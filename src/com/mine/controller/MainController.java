package com.mine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;

@Controller
public class MainController {
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
		return "supply_and_sales";
	}
}
