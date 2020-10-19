package com.mine.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.component.master.Client;
import com.mine.component.master.Company;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.Ledger;
import com.mine.component.transaction.SupplyDetails;
import com.mine.service.MiningService;
import com.mine.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	ReportService reportService;
	
	@Autowired
	MiningService miningService;
	
	Company company = new Company();
	int companyId = 1;
	// ------------------------------ Sales Control --------------------------------------
	@RequestMapping("sales_report")
	public String salesReport(Model model,@RequestParam(name="vehicle_no",required=false) String vehicleNumber,@RequestParam(name="material",required=false) String material,@RequestParam(name="quantity",required=false) String quantity
			, @RequestParam(name="payment_type", required=false) String paymentType, @RequestParam(name="f_date", required=false) @DateTimeFormat(iso = ISO.DATE)LocalDate fromDate, @RequestParam(name="t_date", required=false) @DateTimeFormat(iso = ISO.DATE)LocalDate toDate) {
		List<SupplyDetails> salesList = reportService.getSupplyDetails(vehicleNumber, quantity, material, paymentType, fromDate, toDate);
		model.addAttribute("salesList",salesList);
		model.addAttribute("material_lookup", miningService.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", miningService.getLookupMap("quantity"));
		//model.addAttribute();
		return "report/sales_report";
	}
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
			model.addAttribute("client",miningService.getClientList(companyId, 0));
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
	// get ledger entries based on party
		/*@RequestMapping("get_party_ledger")
		public @ResponseBody String getPartyLedgerEntries(@RequestParam("name") String partyName) {
			LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0,0));
			LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
			List<Ledger> ledgerEntries = this.reportService.getLedgerEntries(partyName, startDate, endDate);
			System.out.println("Ledger Entries"+ledgerEntries.size());
			Double[] balances = reportService.getBalances(partyName, startDate, endDate);
			double openingBalance = balances[0];
			double closingBalance = balances[1]+balances[0];
			JSONObject obj = null;
			JSONObject[] objArray = new JSONObject[ledgerEntries.size()+1];
			System.out.println(openingBalance);
			if(openingBalance >= 0) {
				obj = new JSONObject();
				obj.put("date",startDate);
				obj.put("cParticular", "Opening Balance");
				obj.put("cRemarks", "");
				obj.put("creditAmount", openingBalance);
				obj.put("dParticular", "");
				obj.put("dRemarks", "");
				obj.put("debitAmount","");
			}
			else {
				obj = new JSONObject();
				obj.put("date",startDate);
				obj.put("cParticular", "");
				obj.put("cRemarks", "");
				obj.put("creditAmount", "");
				obj.put("dParticular", "Opening Balance");
				obj.put("dRemarks", "");
				obj.put("debitAmount",openingBalance);
			}
			objArray[0] = obj;
			int count = 1;
			for(Ledger ledger : ledgerEntries) {
				obj = new JSONObject();
				obj.put("date",ledger.getEntryDate().toLocalDate());
				obj.put("cParticular",ledger.getCreditAmount()!= 0.0 ? ledger.getSource()+" to "+ledger.getTarget() : "");
				obj.put("cRemarks", ledger.getCreditAmount()!= 0.0 ? "" : "");
				obj.put("creditAmount", ledger.getCreditAmount() !=0.0 ? ledger.getCreditAmount() : "");
				obj.put("dParticular", ledger.getDebitAmount()!= 0.0 ? ledger.getSource()+" to "+ledger.getTarget() : "");
				obj.put("dRemarks", ledger.getCreditAmount()!= 0.0 ? "" : "");
				obj.put("debitAmount",ledger.getDebitAmount() !=0.0 ? ledger.getDebitAmount() : "");
				objArray[count] = obj;
				count += 1;
			}
			//System.out.println(Arrays.toString(objArray));
			return Arrays.toString(objArray);
		}*/
	// ------------------------------ End Ledger Control ----------------------------------
	
	// ------------------------------ Report Panel ----------------------------------------
	@RequestMapping("report_panel")
	public String reportPanel() {
		return "report/report_panel";
	}
	
	
	
	// -------------------------------End Report Panel -----------------------------------
	
	// ------------------------------- Support Methods -----------------------------------
	
	public List<String[]> getPartyLedgerEntries(String partyName, LocalDate startDate, LocalDate endDate) {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0,0));
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23,59,59));
		List<Ledger> ledgerEntries = this.reportService.getLedgerEntries(partyName, startDateTime, endDateTime);
		Double[] balances = reportService.getBalances(partyName, startDateTime, endDateTime);
		double openingBalance = balances[0];
		double closingBalance = balances[1]+balances[0];
		List<String[]> listOfRecords = new ArrayList<>();
		
		String [] strArray = null;
		if(openingBalance >= 0) {
			strArray = new String[] {startDate.toString(),"Opening Balance",Double.toString(openingBalance),"",""};
		}
		else {
			strArray = new String[] {startDate.toString(),"Opening Balance","",Double.toString(openingBalance),""};
		}
		listOfRecords.add(strArray);
		int count = 1;
		for(Ledger ledger : ledgerEntries) {
			strArray = new String[] {ledger.getEntryDate().toLocalDate().toString(),ledger.getSource()+" to "+ledger.getTarget(),Double.toString(ledger.getCreditAmount()),Double.toString(ledger.getDebitAmount()),""};
			listOfRecords.add(strArray);
		}
		
		if(closingBalance >= 0) {
			strArray = new String[] {startDate.toString(),"Closing Balance",Double.toString(openingBalance),"",""};
		}
		else {
			strArray = new String[] {startDate.toString(),"Closing Balance","",Double.toString(openingBalance),""};
		}
		listOfRecords.add(strArray);
		return listOfRecords;
	}
	
	
	// ------------------------------- End Support Methods -------------------------------
}
