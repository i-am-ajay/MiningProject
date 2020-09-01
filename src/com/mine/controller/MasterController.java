package com.mine.controller;

import java.util.Map;

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
import com.mine.component.master.Company;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.service.MiningService;

@Controller
public class MasterController {
	@Autowired
	MiningService service;
	Company company = new Company();
	// Creates a new Client
	@RequestMapping("client_creation")
	public String createClient(Model model) {
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
			System.out.println(id);
			service.saveClient(client, id);
		}
		return "client_saved";
	}
	
	@RequestMapping("company_creation")
	public String companyCreation(Model model) {
		Company company = new Company();
		model.addAttribute("company",company);
		return "company";
	}
	
	@RequestMapping("company_saved")
	public String saveCompany(@ModelAttribute Company company, BindingResult companyResult) {
		if(companyResult.hasErrors()) {
			
		}
		else {
			service.saveCompany(company);
		}
		return "company_saved";
	}
	
	@RequestMapping("create_vehicle")
	public String createVehicle(Model model) {
		Vehicle vehicle = new Vehicle();
		vehicle.setCompanyId(company);
		model.addAttribute("vehicle",vehicle);
		model.addAttribute("lookup",service.getLookupMap("clientType"));
		model.addAttribute("vehicle_lookup",service.getLookupMap("vehicleType"));
		model.addAttribute("tyre_lookup", service.getLookupMap("tyreType"));
		return "vehicle";
	}
	
	@RequestMapping("save_vehicle")
	public String saveVehicle(@ModelAttribute("vehicle") Vehicle vehicle,BindingResult result, @RequestParam("client_name") int clientId) {
		if(result.hasErrors()) {
			System.out.println("Got some errors");
		}
		else {
			service.saveVehicle(vehicle, clientId, 1);
		}
		return "end";
	}
	
	// ajax methods
	
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

	public String addRate(Model model) {
		Rate rate = new Rate();
		model.addAttribute("rate", rate);
		model.addAttribute("tyre_lookup", service.getLookupMap("tyreType"));
		model.addAttribute("material_lookup", service.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", service.getLookupMap("quantity"));
		model.addAttribute("vehicle_lookup",service.getLookupMap("vehicleType"));
		return "add_rate";
	}
	
	public String saveRate(Model model) {
		return null;
	}
}
