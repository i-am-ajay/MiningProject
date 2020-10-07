package com.mine.controller;

import java.util.Map;

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
import com.mine.component.master.Rate;
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
	
	// --------------------- End Control Fields ------------------------------------------
	
	
	
	// --------------------- Client Controls ---------------------------------------------
	// Creates a new Client
	@RequestMapping("client_creation")
	public String createClient(Model model, @ModelAttribute("status") String status) {
		Client client = new Client();
		client.setCompany(company);
		model.addAttribute("client",client);
		model.addAttribute("lookup",service.getLookupMap("clientType"));
		return "client";
	}
	
	@RequestMapping("save_client")
	public String clientSaved(@ModelAttribute Client client, BindingResult result, @RequestParam(value="client_type", required=false)int id, Model model) {
		if(result.hasErrors()) {
			System.out.println(result.toString());
		}
		else {
			model.addAttribute("status",service.saveClient(client, id, userId));
		}
		return "redirect:client_creation";
	}
	
	// Ajax method to get list of clients to populate in client drop down.
	@RequestMapping("client_list")
	@ResponseBody public String getClientList(@RequestParam("client_id") int id) {
		Map<Integer,String> clientMap = service.getClientList(company, id);
		String dataString = null;
		if(clientMap != null && clientMap.size()>0) {
			JSONObject obj = new JSONObject();
			for(Integer key : clientMap.keySet()) {
				obj.put(key.toString(), clientMap.get(key));
			}
			dataString = obj.toString();
		}
		return dataString;
	}
	
	// ------------------------------------ End Client Controls ------------------------------
	
	
	// ------------------------------------ Company Controls -----------------------------
	@RequestMapping("company_creation")
	public String companyCreation(Model model, @ModelAttribute("status") String status) {
		Company company = new Company();
		model.addAttribute("company",company);
		return "company";
	}
	
	@RequestMapping("company_saved")
	public String saveCompany(Model model,@ModelAttribute Company company, BindingResult companyResult) {
		if(companyResult.hasErrors()) {
			
		}
		else {
			model.addAttribute("status",service.saveCompany(company,userId));
		}
		return "redirect:company_creation";
	}
	// --------------------------------- End Company Controls ----------------------------
	
	
	
	// -------------------------------- Vehicle Controls --------------------------------
	@RequestMapping("create_vehicle")
	public String createVehicle(Model model, @ModelAttribute("status") String status) {
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
	public String saveVehicle(Model model, @ModelAttribute("vehicle") Vehicle vehicle,BindingResult result, @RequestParam("client_name") int clientId) {
		if(result.hasErrors()) {
			System.out.println("Got some errors");
		}
		else {
			model.addAttribute("status",service.saveVehicle(vehicle, clientId, 1, userId));
		}
		return "redirect:create_vehicle";
	}
	
	// Ajax method to check for vehicle duplicay on entering vehicle number.
	@RequestMapping("check_vehicle_duplicacy")
	public @ResponseBody String vehicleDuplicacyCheck(@RequestParam("vehicle_no") String vehicleNo) {
		boolean vehicleExists = service.isVehicleRegistered(vehicleNo);
		JSONObject object = new JSONObject();
		String vehicleStatus = "2";
		if(vehicleExists) {
			vehicleStatus = "1";
		}
		object.put("vehicleStatus", vehicleStatus);
		return object.toString();
	}
	// ----------------------------------- End Vehicle Controls ---------------------------------
	
	
	// ----------------------------------- Rate Controls ----------------------------------------
	@RequestMapping("add_rate")
	public String addRate(Model model, @ModelAttribute("status") String status) {
		Rate rate = new Rate();
		model.addAttribute("rate", rate);
		model.addAttribute("tyre_lookup", service.getLookupMap("tyreType"));
		model.addAttribute("material_lookup", service.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", service.getLookupMap("quantity"));
		model.addAttribute("vehicle_lookup",service.getLookupMap("vehicleType"));
		return "add_rate";
	}
	
	@RequestMapping("save_rate")
	public String saveRate(Model model, @ModelAttribute("rate") Rate rate, BindingResult result) {
		String page = null;
		if(result.hasErrors()) {
			
		}
		else {
			model.addAttribute("status", service.saveRate(rate, 1,userId));
			
		}
		return "redirect:add_rate";
	}
	// ------------------------------------- End Rate Controls ---------------------------------
	// Admin panel control.
	@RequestMapping("admin_panel")
	public String admin() {
		return "admin_panel";
	}
	
	/*@ExceptionHandler(Exception.class)
	public String handleAnyError() {
			String page = "redirect:admin_panel";
		return page;
	}*/
}
