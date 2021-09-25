package com.mine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ajay.customeditor.MachinePropertyEditor;
import com.mine.component.master.Machine;
import com.mine.component.transaction.FuelDistribution;
import com.mine.service.FuelDistributionService;
import com.mine.service.MiningService;

@Controller
public class FuleDistributionController{
	@Autowired
	FuelDistributionService service;

	@Autowired
	MiningService forLookupService;
	
	@Autowired
	FuelDistribution dist; 
	
	@RequestMapping(value="fuel_distribution")
	public String showFuelDistributionPage(Model model) {
		model.addAttribute("fuel_dist_obj",dist);
		model.addAttribute("machine_map",service.getMachineMap());
		model.addAttribute("entry_type",forLookupService.getLookupMap("Entry Type"));
		model.addAttribute("total_qty",service.getTotalFuel());
		return "add_fuel";
	}
	
	@InitBinder
	public void propertyEditor(WebDataBinder binder) {
		binder.registerCustomEditor(Machine.class, new MachinePropertyEditor(service));
	}
	
	@RequestMapping(value="save_fuel_record")
	public String saveFuelRecord(@ModelAttribute FuelDistribution dist, BindingResult result) {
		String page;
		if(result.hasErrors()) {
			page = "fuel_distribution";
		}
		else {
			service.insertFuelRecord(dist);
			page = "redirect:fuel_distribution";
		}
		return null;
	}
}
