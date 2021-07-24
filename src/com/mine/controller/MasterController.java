package com.mine.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.component.master.Client;
import com.mine.component.master.Company;
import com.mine.component.master.Parameters;
import com.mine.component.master.Rate;
import com.mine.component.master.User;
import com.mine.component.master.Vehicle;
import com.mine.service.MiningService;

@Controller
public class MasterController {
	// --------------------- Autowired Fields Section ------------------------------------
	@Autowired
	MiningService service;
	
	// --------------------- End Autowired Fields ----------------------------------------
	
	// --------------------- Control Instance Fields -------------------------------------
	Company company = new Company();
	int userId = 1;
	int companyId = 1;
	
	// --------------------- End Control Fields ------------------------------------------
	
	
	
	// --------------------- Client Controls ---------------------------------------------
	// Creates a new Client
	@RequestMapping("client_creation")
	public String createClient(HttpSession session,Model model, @ModelAttribute("status") String status) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		Client client = new Client();
		client.setCompany(company);
		model.addAttribute("client",client);
		model.addAttribute("lookup",service.getLookupMap("clientType"));
		return "client";
	}
	
	@RequestMapping("save_client")
	public String clientSaved(HttpSession session,@ModelAttribute Client client, BindingResult result, @RequestParam(value="client_type", required=false)int id,
			@RequestParam(defaultValue= "false") boolean status,Model model) {
		if(result.hasErrors()) {
			System.out.println(result.toString());
		}
		else {
			//System.out.println(status);
			User user = (User)session.getAttribute("user");
			if(!status) {
				client.setStatus(true);
			}
			else {
				client.setStatus(false);
			}
			model.addAttribute("status",service.saveClient(client, id,user, companyId,user.getRole()));
		}
		return "redirect:client_creation";
	}
	
	// Ajax method to get client information
	
	@RequestMapping("get_client")
	public @ResponseBody String getClient(@RequestParam(name="name")String clientName) {
		Client client = service.getClient(clientName);
		JSONObject object = null;
		String result = null;
		if(client != null) {
			object = new JSONObject();
			object.put("name",client.getName());
			object.put("contact", client.getClientContact());
			object.put("client_type", client.getClientType().getId());
			object.put("address", client.getClientAddress());
			object.put("company", client.getCompany().getId());
			object.put("comission", client.getComission());
			object.put("discount", client.getDiscount());
			object.put("type_desc", client.getClientType().getDescription());
			result = object.toString();
		}
		return result;
	}
	
	
	// Ajax method to get list of clients to populate in client drop down.
	@RequestMapping("client_list")
	@ResponseBody public String getClientList(@RequestParam("client_id") int id) {
		Map<Integer,String> clientMap = service.getClientList(companyId, id);
		Map<String,String> clientMapString = new LinkedHashMap<>();
		clientMap.forEach((k,v) ->{
			clientMapString.put(k.toString(), v);
		});
		String dataString = null;
		/*if(clientMap != null && clientMap.size()>0) {
			JSONObject obj = new JSONObject();
			for(Entry<Integer,String> e: clientMap.entrySet()) {
				obj.put(e.getKey().toString(), e.getValue());
			}*/
			/*for(Integer key : clientMap.keySet()) {
				obj.put(key.toString(), clientMap.get(key));
			}*/
		JSONObject obj = new JSONObject(clientMapString);
		dataString = obj.toString();
		return dataString;
	}
	
	// ------------------------------------ End Client Controls ------------------------------
	
	
	// ------------------------------------ Company Controls -----------------------------
	/**
	 * View control for company creation page. 
	 * @param model
	 * @param status
	 	If control is acquired 
	 	a) on direct request from browser then there won't be any status model attribute.
	 	b) if after company creation there will be a status model attribute and it will be 
	 	attached in query string.
	 * @return
	 */
	@RequestMapping("company_creation")
	public String companyCreation(HttpSession session,Model model, @ModelAttribute("status") String status) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		Company company = new Company();
		model.addAttribute("company",company);
		return "company";
	}
	
	/**
	 * Based on values from the view either a compnay will be created 
	 	or updated if it exists and user is admin else if company exists and role is user then a msg will be flashed
	 * @param model
	 * @param company
	 * @param companyResult
	 * @return
	 */
	@RequestMapping("company_saved")
	public String saveCompany(HttpSession session,Model model,@ModelAttribute Company company, BindingResult companyResult) {
		/**
		 * Check if passed values has some error.
		 * Call company service with company, user id and user role parameters.
		 * On success request handing :
		  	a) Add the message returned by service to a model as 'status'. 
		  	b) redirect the request to company_creation control.
		 */
		if(companyResult.hasErrors()) {
			
		}
		else {
			User user = (User)session.getAttribute("user");
			model.addAttribute("status",service.saveCompany(company,user,user.getRole()));
		}
		return "redirect:company_creation";
	}
	
	/**
	 * AJAX method call to get company details based on company name, if company already exists in the system then
	 * a JSON object will be created with company object fields and these fields will be populated in company form.
	 * @param companyName
	 * @return
	 */
	@RequestMapping("get_company")
	public @ResponseBody String getCompany(@RequestParam(name="name")String companyName) {
		Company company = service.getCompany(companyName);
		JSONObject object = null;
		String result = null;
		if(company != null) {
			object = new JSONObject();
			object.put("name",company.getName());
			object.put("contact", company.getCompanyContact());
			object.put("email", company.getCompanyEmail());
			object.put("address", company.getLocation());
			result = object.toString();
		}
		return result;
	}
	// --------------------------------- End Company Controls ----------------------------
	
	
	
	// -------------------------------- Vehicle Controls --------------------------------
	@RequestMapping("create_vehicle")
	public String createVehicle(HttpSession session,Model model, @ModelAttribute("status") String status) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		Vehicle vehicle = new Vehicle();
		vehicle.setCompanyId(company);
		vehicle.setDiscount(0.0);
		model.addAttribute("vehicle",vehicle);
		model.addAttribute("lookup",service.getLookupMap("clientType"));
		model.addAttribute("vehicle_lookup",service.getLookupMap("vehicleType"));
		model.addAttribute("tyre_lookup", service.getLookupMap("tyreType"));
		return "vehicle";
	}
	
	@RequestMapping("save_vehicle")
	public String saveVehicle(HttpSession session,Model model, @ModelAttribute("vehicle") Vehicle vehicle,BindingResult result, @RequestParam("client_name") int clientId,
			@RequestParam(name="status",defaultValue="false") boolean disableVehicle) {
		if(result.hasErrors()) {
			System.out.println("Got some errors");
		}
		else {
			User user = (User)session.getAttribute("user");
			//System.out.println(disableVehicle);
			if(!disableVehicle) {
				vehicle.setStatus(true);
			}
			else {
				//System.out.println("Disable vehicle true");
				vehicle.setStatus(false);
			}
			model.addAttribute("status",service.saveVehicle(vehicle, clientId, 1, user,user.getRole()));
		}
		return "redirect:create_vehicle";
	}
	
	// Ajax method to check for vehicle duplicay on entering vehicle number.
	@RequestMapping("check_vehicle_duplicacy")
	public @ResponseBody String vehicleDuplicacyCheck(@RequestParam("vehicle_no") String vehicleNo) {
		//boolean vehicleExists = service.isVehicleRegistered(vehicleNo);
		Vehicle vehicle = service.getVehicle(vehicleNo);
		JSONObject object = null;
		String result = null;
		if(vehicle != null) {
			object = new JSONObject();
			object.put("vehicle_no", vehicle.getVehicleNo());
			object.put("vehicle_type", vehicle.getVehicleType());
			object.put("tyre_type", vehicle.getTyreType());
			object.put("belongs_to", vehicle.getClientId().getClientType().getId());
			object.put("client", vehicle.getClientId().getClientId());
			object.put("discount", vehicle.getDiscount() != 0.0 ? vehicle.getDiscount() : vehicle.getClientId().getDiscount());
			object.put("status",vehicle.isStatus());
			result = object.toString();
		}
		return result;
	}
	// ----------------------------------- End Vehicle Controls ---------------------------------
	
	
	// ----------------------------------- Rate Controls ----------------------------------------
	@RequestMapping("add_rate")
	public String addRate(HttpSession session,Model model, @ModelAttribute("status") String status) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		Rate rate = new Rate();
		model.addAttribute("rate", rate);
		model.addAttribute("tyre_lookup", service.getLookupMap("tyreType"));
		model.addAttribute("material_lookup", service.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", service.getLookupMap("quantity"));
		model.addAttribute("vehicle_lookup",service.getLookupMap("vehicleType"));
		return "add_rate";
	}
	
	@RequestMapping("save_rate")
	public String saveRate(HttpSession session,Model model, @ModelAttribute("rate") Rate rate, BindingResult result) {
		String page = null;
		if(result.hasErrors()) {
			
		}
		else {
			User user = (User)session.getAttribute("user");
			model.addAttribute("status", service.saveRate(rate, 1,user));
			
		}
		return "redirect:add_rate";
	}
	
	@RequestMapping("update_rate")
	public @ResponseBody String updateRate(@RequestParam("rate_id") int id, @RequestParam("rate") double rate) {
		return Double.toString(service.updateRate(id, rate));
	}
	// ------------------------------------- End Rate Controls ---------------------------------
	
	// ------------------------------------ Administrative Controls ----------------------------
	// Admin panel control.
	@RequestMapping("admin_panel")
	public String admin(HttpSession session, Model model) {
		if(session.getAttribute("user") == null){
			return "login";
		}
		model.addAttribute("role",(String)session.getAttribute("role"));
		return "admin_panel";
	}
	
	@RequestMapping("parameter_display")
	public String parametersUpdate(HttpSession session, Model model, @ModelAttribute("status") String status) {
		User user = (User)session.getAttribute("user");
		String page = "admin_panel";
		if(user == null) {
			return "login";
		}
		else if(user.getRole().equalsIgnoreCase("admin")) {
			Parameters param = service.getParameters();
			model.addAttribute("parameters",param);
			page = "parameters";
		}
		else {
			page = "admin_panel";
		}
		return page;
	}
	
	@RequestMapping("update_parameters")
	public String updateParameters(Model model, @ModelAttribute("parameters")Parameters param, BindingResult result) {
		if(result.hasErrors()) {
			System.out.println(result.getObjectName());
		}
		model.addAttribute("status",service.updateParameters(param));
		return "redirect:parameter_display";
	}
	
	/*@ExceptionHandler(Exception.class)
	public String handleAnyError() {
			String page = "redirect:admin_panel";
		return page;
	}*/
	
	//----------------------------------- End Administrative Controls -----------------------------
	
	//---------------------------------- Support Code ---------------------------------------------
	//Add a row for Self in the database if there isn't a row already.
	
	//---------------------------------- End Support Code -----------------------------------------
}
