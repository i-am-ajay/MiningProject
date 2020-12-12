package com.mine.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.component.master.User;
import com.mine.component.master.Client;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;
import com.mine.service.MiningService;
import com.mine.service.ReportService;
import com.mine.utilities.TokenManager;

@Controller
public class MainController {
	@Autowired
	MiningService service;
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	ReportController reportController;
	
	int companyId = 1;

	//------------------------------ Home Page Control --------------------------------
	@RequestMapping({"/","home"})
	public String home(Model model, HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		return "admin_panel";
	}
	
	//------------------------------ End of Home Page Control ---------------------------
	
	
	//-------------------------------- Sales Control ------------------------------------
	@RequestMapping("display_sales_page")
	public String renderSales(Model model, HttpSession session){
		if(session.getAttribute("user") == null){
			return "login";
		}
		User user = (User)session.getAttribute("user");
		SupplyDetails details = new SupplyDetails();
		details.setNrl(0.0);
		details.setDriverReturn(0.0);
		Vehicle vehicle = new Vehicle();
		details.setVehicle(vehicle);
		model.addAttribute("supply", details);
		model.addAttribute("material_lookup", service.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", service.getLookupMap("quantity"));
		model.addAttribute("data_list",service.getTop10Records());
		model.addAttribute("parameter",service.getParameters());
		model.addAttribute("role",user.getRole());
		model.addAttribute("sale_count",service.getTodaySaleCount());
		return "supply_and_sales";
	}
	
	@RequestMapping("save_supply")
	public String saveSales(HttpSession session,Model model,@ModelAttribute("supply") SupplyDetails details, 
			BindingResult result) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		String page = null;
		if(result.hasErrors()) {
			System.out.println(result.toString());
		}
		if(!details.isDataCorrect()) {
			return "display_sales_page";
		}
		String token = TokenManager.giveToken(service);
		details.setToken(token);
		User user = (User)session.getAttribute("user");
		service.saveSupplyDetails(details, user);
		model.addAttribute("supply",details);
		if((details.getQuantity().equalsIgnoreCase("foot")) || (details.getQuantity().equalsIgnoreCase("bucket")) ||
				(details.getQuantity().equalsIgnoreCase("ton"))) {
			//System.out.println("In print quantity test");
			double rate = service.getRate(details.getTyreType(), details.getMaterial(), details.getVehicleType(), details.getQuantity(), companyId);
			double quantity = details.getRate() / rate;
			model.addAttribute("qty",Long.toString(Math.round(quantity)).concat(" ").concat(details.getQuantity()));
		}
		else {
			model.addAttribute("qty",details.getQuantity());
		}
		page = "print_token";
		return page;
	}
	
	// JSON Request to get rate based on selected  parameter
	@RequestMapping("get_rate")
	public @ResponseBody String getVehicleRate(@RequestParam("tyre_type")String tyreType, 
												@RequestParam("material_type")String materialType, 
												@RequestParam("vehicle_type")String truckType, 
												@RequestParam("quantity")String quantity) {
		//System.out.println("tyreType"+tyreType +"MT: "+materialType+"TT: "+truckType+"quantity"+quantity);
		//System.out.println();
		double rate = service.getRate(tyreType, materialType, truckType, quantity, companyId);
		JSONObject obj = new JSONObject();
		obj.put("rate", rate);
		return obj.toString();
	}
	
	@RequestMapping("cancel_sales")
	public @ResponseBody String cancleSales(@RequestParam("sales_id")int id) {
		String status = "";
		boolean flag = service.cancleSales(id);
		if(flag) {
			status = "table-danger";
		}
		return status;
	}
	// -------------------------------- End Of Sales Page Control -----------------------------
	
	
	//-------------------------------- Expense and Deposite Control ---------------------------
	@RequestMapping("ledger_entries_screen")
	public String ledgerEntries(HttpSession session,Model model,@RequestParam(name="party", required=false) String partyName, @RequestParam(name="amount",required=false, defaultValue="0.0") double amount, 
			@RequestParam(name="type", required=false) String type, @RequestParam(name="expense_type", required=false) String expenseType, 
			@RequestParam(name="remarks", required=false) String remarks) {
		if(session.getAttribute("user") == null){
			return "login";
		}
		User user = (User)session.getAttribute("user");
		if (partyName != null && amount != 0.0) {
			service.ledgerEntries(partyName, amount, type, expenseType, remarks,user);
		}
		
		model.addAttribute("party_list",service.getClientList(companyId, 0));
		model.addAttribute("subtype_list",service.getLookupMap("SubType"));

		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		List<String[]> stringList = reportController.getPartyLedgerEntries("Cash", startDate, endDate);
		model.addAttribute("ledger_records",stringList);
		//System.out.println(stringList.size());
		return "ledger_entries";
	}
	
	//-------------------------------- End Expense and Deposite Control -----------------------
	
	
	// -------------------------------- JSON Control ------------------------------------------ 
	//get Vehicle and Associated Discount
	@RequestMapping("fetch_vehicle")
	public @ResponseBody String fetchVehicleDetails(@RequestParam("vehicle_no") String vehicleNo) {
		Vehicle vehicle = service.getVehicle(vehicleNo);
		String stringObj = null;
		if(vehicle != null) {
			JSONObject object = new JSONObject();
			object.put("vehicle_type", vehicle.getVehicleType());
			object.put("tyre_type", vehicle.getTyreType());
			object.put("vehicle_of", vehicle.getClientId().getName());
			// Discount logic 
			Client client = vehicle.getClientId();
			//String clientDescription = client.getClientType().getDescription();
			
			if(vehicle.getDiscount() != 0) {
				object.put("discount", vehicle.getDiscount());
			}
			else {
				object.put("discount", client.getDiscount());
			}
			// Add client information
			stringObj = object.toString();
		}
		return stringObj;
	}	
	//---------------------------------- End Of Control -----------------------------------
	
	// --------------------------------- User authentication Control ----------------------
	
	@RequestMapping("authenticate_user")
	public String authenticateUser(Model model,HttpSession session, @RequestParam(name="username") String userName, @RequestParam(name="password") String password ) {
		String page = "login";
		userName = userName.toLowerCase();
		User user = service.getUser(userName);
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
	
	@RequestMapping("change_password")
	public String changePassword(HttpSession session, Model model,  @RequestParam(name="password", required=false)String password,@RequestParam(name="rpassword", required=false)String rpassword) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		model.addAttribute("username",session.getAttribute("username"));
		User user = service.getUser(session.getAttribute("username").toString());
		if(password != null && password.length() > 0 ) {
			user.setPassword(password);
			service.updatePassword(user);
			model.addAttribute("status", "updated");
		}
		
		return "password_change";
	}
	
	// Create User
	@RequestMapping("create_user")
	public String createUser(Model model, @RequestParam("username") String username, @RequestParam("password") String password, 
			@RequestParam("role") String role, @RequestParam(name="inactive", required=false) boolean activeStatus, HttpSession session) {
		if(session.getAttribute("user") == null){
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
		boolean status = service.createUser(username, password, role, (User)session.getAttribute("user"), active);
		if(status == true) {
			requestStatus = "success";
		}
		else {
			requestStatus = "exists";
		}
		model.addAttribute("status",requestStatus);
		return "registration";
	}
	
	// fetch user details.
	@RequestMapping("user_load")
	public @ResponseBody String userSearch(@RequestParam("user")String username) {
		User user = service.getUser(username);
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
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	// ---------------------------------End User authentication control -------------------
	
	// --------------------------------- Print token -------------------------------
	
	@RequestMapping("print_token")
	public String printToken(HttpSession session, Model model, @ModelAttribute("supply") SupplyDetails details) {
		if(session.getAttribute("user") == null){
			return "login";
		}
		model.addAttribute("supply",details);
		return "print_token";
	}
	
	//---------------------------------- End Print Token ---------------------------
}
