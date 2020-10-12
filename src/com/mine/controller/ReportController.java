package com.mine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mine.component.master.Client;
import com.mine.component.master.Company;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.service.MiningService;
import com.mine.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	ReportService reportService;
	
	@Autowired
	MiningService miningService;
	
	Company company = new Company();
	// ------------------------------ Sales Control --------------------------------------
	
	// ------------------------------ End Sales Control ----------------------------------
	
	
	// ------------------------------ Token Control --------------------------------------
	
	// ------------------------------ End Token Control ----------------------------------
	
	
	// ------------------------------ Client Control --------------------------------------
	@RequestMapping("client_report")
	public String clientReport(Model model, @RequestParam(name="name",required=false) String name,@RequestParam(name="contact",required=false) String contact, 
				 @RequestParam(name="belongs_to",required=false) String clientType ) {
			List<Client> clientList = reportService.getClientList(name, contact, clientType != null ? Integer.parseInt(clientType) : 0);
			model.addAttribute("clientList",clientList);
			return "report/client_report";
	}
	// ------------------------------ End Client Control ----------------------------------
	
	
	// ------------------------------ Vehicle Control --------------------------------------
	@RequestMapping("vehicle_report")
	public String vehicleReport(Model model, @RequestParam(name="vehicle_no",required=false) String vehicleNo,@RequestParam(name="client",required=false) String client, @RequestParam(name="vehicle_type",required=false) String vehicleType,
				@RequestParam(name="tyre_type",required=false, defaultValue="0") int tyreType, @RequestParam(name="belongs_to",required=false) String clientType ) {
			List<Vehicle> vehicleList = reportService.getVehicleList(vehicleNo, vehicleType, tyreType, client, clientType != null ? Integer.parseInt(clientType) : 0);
			model.addAttribute("vehicleList",vehicleList);
			model.addAttribute("client",miningService.getClientList(company, 0));
			model.addAttribute("vehicle_lookup",miningService.getLookupMap("vehicleType"));
			model.addAttribute("tyre_lookup", miningService.getLookupMap("tyreType"));
			return "report/vehicle_report";
	}
	// ------------------------------ End Vehicle Control ----------------------------------
	
	
	// ------------------------------ Rate Report ------------------------------------------
	@RequestMapping("rate_report")
	public String vehicleReport(Model model, @RequestParam(name="material",required=false) String material,@RequestParam(name="quantity",required=false) String quantity, @RequestParam(name="vehicle_type",required=false) String vehicleType,
				@RequestParam(name="tyre_type",required=false, defaultValue="0") int tyreType) {
			List<Rate> rateList = reportService.getRateList(vehicleType, tyreType, quantity, material);
			model.addAttribute("vehicle_lookup",miningService.getLookupMap("vehicleType"));
			model.addAttribute("tyre_lookup", miningService.getLookupMap("tyreType"));
			model.addAttribute("material_lookup", miningService.getLookupMap("materialType"));
			model.addAttribute("quantity_lookup", miningService.getLookupMap("quantity"));
			model.addAttribute("rateList",rateList);
			return "report/rate_report";
	}
	
	// ------------------------------ End Rate Report --------------------------------------
	
	
	// ------------------------------ Cashbook Control --------------------------------------
	
	// ------------------------------ End Cashbook Control ----------------------------------
	
	
	// ------------------------------ Ledger Control --------------------------------------
	
	// ------------------------------ End Ledger Control ----------------------------------
}
