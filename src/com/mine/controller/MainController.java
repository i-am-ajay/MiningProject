package com.mine.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;
import com.mine.service.MiningService;

@Controller
public class MainController {
	@Autowired
	MiningService service;
	
	@RequestMapping({"/","home"})
	public String home(Model model) {
		System.out.print("hello");
		return "index";
	}
	
	@RequestMapping("add_sales")
	public String addSales(Model model){
		SupplyDetails details = new SupplyDetails();
		Vehicle vehicle = new Vehicle();
		details.setVehicle(vehicle);
		model.addAttribute("supply", details);
		model.addAttribute("material_lookup", service.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", service.getLookupMap("quantity"));
		return "supply_and_sales";
	}
	
	@RequestMapping("fetch_vehicle")
	public @ResponseBody String fetchVehicleDetails(@RequestParam("vehicle_no") String vehicleNo) {
		Vehicle vehicle = service.getVehicle(vehicleNo);
		String stringObj = null;
		if(vehicle != null) {
			JSONObject object = new JSONObject();
			object.put("vehicle_type", vehicle.getVehicleType());
			object.put("tyre_type", vehicle.getTyreType());
			object.put("discount", vehicle.getDiscount());
			object.put("client_discount", vehicle.getClientId().getDiscount());
			stringObj = object.toString();
		}
		return stringObj;
	}
}
