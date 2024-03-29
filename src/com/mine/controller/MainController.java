package com.mine.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		model.addAttribute("role",(String)session.getAttribute("role"));
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
		service.saveSupplyDetails(details, user, companyId);
		model.addAttribute("supply",details);
		/*if((details.getQuantity().equalsIgnoreCase("foot")) || (details.getQuantity().equalsIgnoreCase("bucket")) ||
				(details.getQuantity().equalsIgnoreCase("ton"))) {
			//System.out.println("In print quantity test");
			//double rate = service.getRate(details.getTyreType(), details.getMaterial(), details.getVehicleType(), details.getQuantity(), companyId);
			//double quantity = details.getRate() / rate;
			double quantity = details.getUnit();
			model.addAttribute("qty",Long.toString(Math.round(quantity)).concat(" ").concat(details.getQuantity()));
		}
		else {
			model.addAttribute("qty",details.getQuantity());
		}*/
		model.addAttribute("qty",details.getFormattedQuantity(0));
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
	@RequestMapping(value="ledger_entries_screen")
	public String ledgerEntries(HttpSession session,Model model,@RequestParam(name="party", required=false) String partyName, @RequestParam(name="amount",required=false, defaultValue="0.0") double amount, 
			@RequestParam(name="type", required=false) String type, @RequestParam(name="expense_type", required=false) String expenseType, 
			@RequestParam(name="remarks", required=false) String remarks, 
			@RequestParam(name="entry_date", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
		if(session.getAttribute("user") == null){
			return "login";
		}
		User user = (User)session.getAttribute("user");
		LocalDateTime dateTime = null;
		if(date != null) {
			dateTime = LocalDateTime.of(date, LocalTime.now());
		}
		else {
		 dateTime = LocalDateTime.of(LocalDate.now(),LocalTime.now());
		}
		if (partyName != null && amount != 0.0) {
			service.ledgerEntries(partyName, amount, type, expenseType, remarks,user, dateTime);
		}
		
		Map<Integer, String> clientMap = service.getClientList(companyId, 0);
		model.addAttribute("party_list",service.getClientList(companyId, 0));
		model.addAttribute("subtype_list",service.getLookupMap("SubType"));
		model.addAttribute("minDate", this.setMinDate(user.getRole()));
		model.addAttribute("maxDate", LocalDate.now());
		model.addAttribute("party_",partyName);
		
		model.addAttribute("enable_date", user.getRole().equalsIgnoreCase("user") ? false : true);

		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		List<String[]> stringList = reportController.getPartyLedgerEntries("Cash", startDate, endDate);
		model.addAttribute("ledger_records",stringList);
		//System.out.println(stringList.size());
		return "ledger_entries";
	}
	
	@RequestMapping("journal_entries")
	public String journalEntries(HttpSession session,Model model,@RequestParam(name="debtor", required=false) String debtorName,
			@RequestParam(name="creditor", required=false) String creditorName,
			@RequestParam(name="amount",required=false, defaultValue="0.0") double amount, 
			@RequestParam(name="remarks", required=false) String remarks, 
			@RequestParam(name="entry_date", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
		
		if(session.getAttribute("user") == null){
			return "login";
		}
		User user = (User)session.getAttribute("user");
		LocalDateTime dateTime = null;
		if(date != null) {
			dateTime = LocalDateTime.of(date, LocalTime.now());
		}
		else {
		 dateTime = LocalDateTime.of(LocalDate.now(),LocalTime.now());
		}
		if (debtorName != null && creditorName != null && amount != 0.0) {
			service.journalEntries(debtorName, creditorName, amount, remarks, dateTime, user);
		}
		
		model.addAttribute("party_list",service.getClientList(companyId, 0));
		model.addAttribute("minDate", this.setMinDate(user.getRole()));
		model.addAttribute("maxDate", LocalDate.now());
		model.addAttribute("debtor_",debtorName);
		model.addAttribute("enable_date", user.getRole().equalsIgnoreCase("user") ? false : true);

		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		List<String[]> stringList = reportController.getPartyLedgerEntries(debtorName, startDate, endDate);
		model.addAttribute("ledger_records",stringList);
		return "journal_entries";
	}
	
	//-------------------------------- End Expense and Deposite Control -----------------------
	
	
	// -------------------------------- JSON Control ------------------------------------------ 
	//get Vehicle and Associated Discount
	@RequestMapping("vehicle_fetch")
	public @ResponseBody String fetchVehicleDetails(@RequestParam("vehicle_num") String vehicleNo) {
		//System.out.println(vehicleNo);
		Vehicle vehicle = service.getVehicle(vehicleNo);
		String stringObj = null;
		if(vehicle != null) {
			JSONObject object = new JSONObject();
			object.put("vehicle_type", vehicle.getVehicleType());
			object.put("tyre_type", vehicle.getTyreType());
			object.put("vehicle_of", vehicle.getClientId().getName());
			object.put("status", vehicle.isStatus());
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
		//System.out.println(stringObj);
		return stringObj;
	}	
	//---------------------------------- End Of Control -----------------------------------
	
	// --------------------------------- User authentication Control ----------------------
	
	
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
	
	//---------------------------------- Utility Methods -------------------------
	public LocalDate setMinDate(String role) {
		LocalDate date = null;
		if(role.equalsIgnoreCase("admin")) {
			date = LocalDate.ofEpochDay(0);
		}
		else if(role.equalsIgnoreCase("manager")) {
			date = LocalDate.now().minusDays(2);
		}
		return date;
	}
	
	//---------------------------------- End Of Utility Methods ------------------
	
	//---------------------------------- Test methods -----------------------------
	
	
	//---------------------------------- End Of Test ------------------------------
}
