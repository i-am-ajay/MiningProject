package com.mine.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ajay.customeditor.MachinePropertyEditor;
import com.ajay.customeditor.VendorIdPropertyEditor;
import com.mine.component.master.Client;
import com.mine.component.master.Machine;
import com.mine.component.master.User;
import com.mine.component.transaction.FuelDistribution;
import com.mine.component.transaction.Machine24HrsUnits;
import com.mine.component.transaction.UnitContainer;
import com.mine.service.FuelDistributionService;
import com.mine.service.MiningService;

@Controller
public class FuleDistributionController {
	@Autowired
	private FuelDistributionService service;

	@Autowired
	private MiningService miningService;

	@Autowired
	private MiningService forLookupService;

	@Autowired
	private FuelDistribution dist;

	@Autowired
	private Machine machine;

	@Autowired
	private UnitContainer container;

	private String page;

	private String machineCreationStatus;
	private String addFuelStatus;

	// --------------------------------- Machine Controls
	// --------------------------------------------

	@RequestMapping("add_machine")
	public String addMachine(Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "login";
		}
		User user = (User) session.getAttribute("user");
		model.addAttribute("machine", machine);
		model.addAttribute("status", machineCreationStatus);
		model.addAttribute("vendor_list", miningService.getClientList(1, 46));
		machineCreationStatus = null;
		return "add_machine";
	}

	@RequestMapping("save_machine")
	public String saveMachine(@ModelAttribute Machine machine, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result.toString());
		}
		this.machineCreationStatus = "fail";
		service.saveMachine(machine);
		this.machineCreationStatus = "success";
		return "redirect:add_machine";
	}

	// machine report
	@RequestMapping("machine_report")
	public String getMachines(Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "login";
		}
		User user = (User) session.getAttribute("user");
		List<Machine> machineList = service.getMachines(LocalDate.now());
		model.addAttribute("machineList", machineList);
		return "report/machine_report";
	}

	@RequestMapping("get_machine")
	public @ResponseBody String getMachine(@RequestParam("name") String machineName) {
		Machine machine = service.getMachine(machineName);
		String jsonString = null;
		JSONObject obj = null;
		if (machine != null) {
			obj = new JSONObject();
			obj.put("name", machine.getName());
			obj.put("entry_date", machine.getEntryDate());
			obj.put("end_date", machine.getEndDate());
			obj.put("machine_id", machine.getId());
			obj.put("rate", machine.getMachineRate());
			obj.put("fixed_hours", machine.getFixedHours());
			obj.put("cycle", machine.getCycle());
			obj.put("vendorId", machine.getVendorId());
			obj.put("24hrs_unit", machine.getLast24HrsUnit());
			obj.put("fuel_unit", machine.getLastUnitForFuel());
			jsonString = obj.toString();
		}
		return jsonString;
	}

	// ------------------------------- End Machine DAO
	// -----------------------------------------------
	// ------------------------------- Machine Ajax Control
	// -----------------------------------------
	@RequestMapping("last_unit")
	public @ResponseBody String lastUnit(@RequestParam("machine_id") int id, 
			@RequestParam("entry_date")String entryDate) {
		LocalDate date = LocalDate.parse(entryDate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println("Hello From last unit");
		Machine machine = service.getMachine(id);
		System.out.println(entryDate);
		FuelDistribution distribution = service.getLastMachineDistribution(machine, date);
		System.out.println(distribution.getCurrentUnits());
		JSONObject obj = null;
		if (machine != null) {
			obj = new JSONObject();
			obj.put("lastUnit", distribution.getCurrentUnits());
		}
		return obj.toString();
	}
	// ------------------------------- End Machine Ajax Control
	// ----------------------------------------

	// -------------------------------- End Machine Controls
	// -------------------------------------------
	/*
	 * @RequestMapping("distribute_fuel") public String distribute_fuel(Model model)
	 * { showFuelDistributionPage(model); return "distribute_fuel_to_machines"; }
	 */
	// --------------------------------- Fuel Controls
	// ---------------------------------------------------
	@RequestMapping(value = "add_fuel")
	public String showFuelDistributionPage(Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "login";
		}
		User user = (User) session.getAttribute("user");
		model.addAttribute("status", addFuelStatus);
		model.addAttribute("fuel_dist_obj", dist);
		model.addAttribute("machine_map", service.getMachineMap(LocalDate.now(), true));
		model.addAttribute("entry_type", forLookupService.getLookupMap("FuelEntryType"));
		model.addAttribute("total_qty", service.getTotalFuel());
		addFuelStatus = null;
		return "add_fuel";
	}

	@RequestMapping("distribute_fuel")
	public String distribute_fuel(Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "login";
		}
		User user = (User) session.getAttribute("user");
		model.addAttribute("status", addFuelStatus);
		model.addAttribute("fuel_dist_obj", dist);
		model.addAttribute("machine_map", service.getMachineMap(LocalDate.now(), true));
		model.addAttribute("entry_type", forLookupService.getLookupMap("FuelEntryType"));
		model.addAttribute("total_qty", service.getTotalFuel());
		addFuelStatus = null;
		return "distribute_fuel_to_machines";
	}

	@RequestMapping(value = "save_fuel_record")
	public String saveFuelRecord(@ModelAttribute FuelDistribution dist, @RequestParam String page,
			BindingResult result) {
		addFuelStatus = "fails";
		if (result.hasErrors()) {
			page = "add_fuel";
		} else {
			addFuelStatus = "success";
			if (dist.getEntryType().equals("Fuel Given")) {
				dist.setFuelQty(dist.getFuelQty() * -1);
			}
			service.insertFuelRecord(dist);
			page = "redirect:add_fuel";
		}
		return page;
	}

	@RequestMapping(value = "save_distributed_fuel")
	public String saveFuleDistributionRecord(@ModelAttribute FuelDistribution dis, BindingResult result) {
		addFuelStatus = "fails";
		if (result.hasErrors()) {
			page = "distribute_fuel";
		} else {
			if (dis.getEntryType().equals("Fuel Gven")) {
				dis.setFuelQty(dis.getFuelQty() * -1);
			}
			addFuelStatus = "success";
			service.insertFuelRecord(dis);
			page = "redirect:distribute_fuel";
		}
		return page;
	}

	@RequestMapping("fuel_distribution_report")
	public String fuleDistributionReport(Model model,
			@RequestParam(name = "machine", required = false,defaultValue="-1") int machineId,
			@RequestParam(name = "f_date", required = false) String fromDate,
			@RequestParam(name = "t_date", required = false) String toDate,
			@RequestParam(name = "submitted", required = false) boolean submitted,
			HttpSession session) {
		LocalDate fDate = null;
		LocalDate tDate = null;
		
		if (session.getAttribute("user") == null) {
			return "login";
		}
		User user = (User) session.getAttribute("user");
		
		if(fromDate != null && fromDate.length()>0) {
			fDate = LocalDate.parse(fromDate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		if(toDate != null && toDate.length()>0) {
			tDate = LocalDate.parse(toDate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		
		if (submitted) { model.addAttribute("fuel_distribution_list",
				service.getFuelDistributionReport(machineId, fDate, tDate)); }
		model.addAttribute("machineList", service.getMachines(LocalDate.now()));
		return "report/fuel_distribution_report";
	}
	
	@RequestMapping("fuel_report_panel")
	public String fuelReportPanel() {
		return "report/fuel_report_panel";
	}

	// ---------------------------- End Fuel Controls
	// -----------------------------------------------

	// ---------------------------- Property Editors Control
	// ----------------------------------------
	@InitBinder
	public void propertyEditor(WebDataBinder binder) {
		binder.registerCustomEditor(Machine.class, new MachinePropertyEditor(service));
		binder.registerCustomEditor(Client.class, new VendorIdPropertyEditor(miningService));
	}

	// --------------------------- End Property Editor Control
	// ---------------------------------------

	// --------------------------- Fuel Managment Screen
	// ---------------------------------------------

	@RequestMapping("fuel_panel")
	public String fuelManagment(Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "login";
		}
		User user = (User) session.getAttribute("user");
		return "fuel_panel";
	}

	// --------------------------- End Fuel Managment Scree
	// ------------------------------------------

	// --------------------------- 24 hrs unit managment
	// ---------------------------------------------
	@RequestMapping("units_24_hrs")
	public String unit24hrs(Model model, @RequestParam(name = "unit_date", required = false) String strDate) {
		container.getMachineList().clear();
		LocalDate date = service.getLastEntryDate24HrsUnit();
		LocalDate projectBeginDate = LocalDate.of(2021, 10, 1);
		if (date == null) {
			date = projectBeginDate;
		} 
		else {
			date = date.plusDays(1);
		}
		Map<Integer, Object> activeMachine = service.getMachineMap(date, false);
		Map<Integer, Machine24HrsUnits> existingUnitMap = service.get24hrsUnitMap(date);
		Machine24HrsUnits machineUnits = null;
		for (int key : activeMachine.keySet()) {
			if (existingUnitMap != null && existingUnitMap.get(key) != null) {
				machineUnits = existingUnitMap.get(key);
				System.out.println(machineUnits.getMachineId().getId());
			} else {
				machineUnits = new Machine24HrsUnits();
				Machine machine = (Machine) activeMachine.get(key);
				machineUnits.setLastUnit(machine.getLast24HrsUnit());
				machineUnits.setMachineId(machine);
			}
			container.getMachineList().add(machineUnits);
		}
		model.addAttribute("candidate_machines", container);
		model.addAttribute("unit_date", date);
		return "units_24_hrs";
	}

	@RequestMapping("save_units_24_hrs")
	public String save24hrsUnit(@ModelAttribute("candidate_machines") UnitContainer container,
			@RequestParam(value = "u_date", required = false) String strDate, BindingResult bindingResult) {
		System.out.println("date at save time" + strDate);
		LocalDate date = null;
		String page = null;
		if (strDate == null || strDate.length() == 0) {
			date = LocalDate.now();
		} else {
			date = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		service.capture24hrsUnit(container, date);
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.toString());
		} else {
			page = "redirect:units_24_hrs";

		}
		return page;
	}

	// -------------------------- End 24 hrs unit managment
	// -------------------------------------------

}
