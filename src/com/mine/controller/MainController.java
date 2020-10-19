package com.mine.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	int companyId = 1;
	
	int userId = 1;
	//------------------------------ Home Page Control --------------------------------
	@RequestMapping({"/","home"})
	public String home(Model model) {
		System.out.print("hello");
		return "index";
	}
	
	//------------------------------ End of Home Page Control ---------------------------
	
	
	//-------------------------------- Sales Control ------------------------------------
	@RequestMapping("display_sales_page")
	public String renderSales(Model model){
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
		return "supply_and_sales";
	}
	
	@RequestMapping("save_supply")
	public String saveSales(Model model,@ModelAttribute("supply") SupplyDetails details, 
			BindingResult result) {
		String page = null;
		if(result.hasErrors()) {
			System.out.println(result.toString());
		}
		String token = TokenManager.giveToken(service);
		System.out.println(token);
		details.setToken(token);
		System.out.println("Driver Return"+ details.getDriverReturn());
		System.out.println("NRL"+details.getNrl());
		service.saveSupplyDetails(details, userId);
		page = "redirect:display_sales_page";
		return page;
	}
	
	// JSON Request to get rate based on selected  parameter
	@RequestMapping("get_rate")
	public @ResponseBody String getVehicleRate(@RequestParam("tyre_type")String tyreType, 
												@RequestParam("material_type")String materialType, 
												@RequestParam("vehicle_type")String truckType, 
												@RequestParam("quantity")String quantity) {
		double rate = service.getRate(tyreType, materialType, truckType, quantity, companyId);
		JSONObject obj = new JSONObject();
		obj.put("rate", rate);
		return obj.toString();
	}
	// -------------------------------- End Of Sales Page Control -----------------------------
	
	
	//-------------------------------- Expense and Deposite Control ---------------------------
	@RequestMapping("ledger_entries_screen")
	public String ledgerEntries(Model model,@RequestParam(name="party", required=false) String partyName, @RequestParam(name="amount",required=false, defaultValue="0.0") double amount, 
			@RequestParam(name="type", required=false) String type, @RequestParam(name="expense_type", required=false) String expenseType, 
			@RequestParam(name="remarks", required=false) String remarks) {
		if (partyName != null && amount != 0.0) {
			service.ledgerEntries(partyName, amount, type, expenseType, remarks);
		}
		
		model.addAttribute("party_list",service.getClientList(companyId, 0));
		model.addAttribute("subtype_list",service.getLookupMap("SubType"));
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
		List<String[]> stringList = reportService.getLedgerEntries("Cash", LocalDateTime.of(LocalDate.now(), time), )
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
}
