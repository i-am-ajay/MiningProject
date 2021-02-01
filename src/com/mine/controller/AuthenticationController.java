package com.mine.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.component.master.User;
import com.mine.service.AuthenticationService;

@Controller
public class AuthenticationController {
	
	@Autowired
	AuthenticationService eFS;
	
	@RequestMapping("signup")
	public String signup(Model model,HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		if(!session.getAttribute("role").toString().equalsIgnoreCase("Admin")) {
			return "admin_panel";
		}
		return "registration";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@RequestMapping("create_user")
	public String createUser(Model model, @RequestParam("username") String username, @RequestParam("password") String password, 
			@RequestParam("role") String role, @RequestParam(name="inactive", required=false) boolean activeStatus, HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		if(!session.getAttribute("role").toString().equalsIgnoreCase("Admin")) {
			return "admin_panel";
		}
		String requestStatus = null;
		boolean active = true;
		if(activeStatus == true) {
			active = false;
		}
		boolean status = eFS.createUser(username, password, role, (User)session.getAttribute("user"), active);
		if(status == true) {
			requestStatus = "success";
		}
		else {
			requestStatus = "exists";
		}
		model.addAttribute("status",requestStatus);
		return "registration";
	}
	
	@RequestMapping("authenticate_user")
	public String authenticateUser(Model model,HttpSession session, @RequestParam(name="username") String userName, @RequestParam(name="password") String password ) {
		String page = "login";
		userName = userName.toLowerCase();
		User user = eFS.getUser(userName);
		if( user != null && user.isActive()) {
			if(user.getPassword().equals(password.trim())) {
				String role = user.getRole();
				session.setAttribute("username", user.getUsername());
				session.setAttribute("user", user);
				session.setAttribute("role", role);
				
				role = role.toLowerCase();
				if(role.equals("admin")) {
					page="redirect:admin_panel";
				}
				else if(role.equals("user")) {
					page = "redirect:admin_panel";
				}
				else {
					page = "login";
				}
			}
		}
		return page;
	}
	
	// fetch user details.
	@RequestMapping("user_load")
	public @ResponseBody String userSearch(@RequestParam("user")String username) {
		User user = eFS.getUser(username);
		JSONObject obj = null;
		if(user != null) {
		obj = new JSONObject();
		obj.put("user", user.getUsername());
		obj.put("password", user.getPassword());
		obj.put("role", user.getRole());
		obj.put("deactive", !user.isActive());
		}
		return obj.toString();
	}
	
	@RequestMapping("change_password")
	public String changePassword(HttpSession session, Model model,  @RequestParam(name="password", required=false)String password,@RequestParam(name="rpassword", required=false)String rpassword) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		model.addAttribute("username",session.getAttribute("username"));
		User user = eFS.getUser(session.getAttribute("username").toString());
		if(password != null && password.length() > 0 ) {
			user.setPassword(password);
			eFS.updatePassword(user);
			model.addAttribute("status", "updated");
		}
		
		return "password_change";
	}
}
