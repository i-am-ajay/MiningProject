package com.mine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.User;
import com.mine.dao.AuthenticationDAO;

@Service
public class AuthenticationService {
	@Autowired
	AuthenticationDAO authDAO;
	
	public boolean createUser(String username, String password, String role, User createdBy, boolean status) {
		User dbuser = getUser(username);
		boolean isCreated = false;
		if(dbuser == null) {
			isCreated = true;
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setRole(role);
			user.setCreatedBy(createdBy);
			user.setActive(status);
			dbuser = user;
		}
		else {
			if(password != null && password != "") {
				dbuser.setPassword(password);
			}
			dbuser.setCreatedBy(createdBy);
			dbuser.setActive(status);
			dbuser.setRole(role);
		}
		authDAO.saveUser(dbuser);
		return isCreated;
	}
	
	public User getUser(String emp) {
		User user = authDAO.getUser(emp);
		if(user != null) {
			return user;
		}
		else {
			return null;
		}
	}
	
	public void updatePassword(User user) {
		authDAO.updatePassword(user);
	}
}
