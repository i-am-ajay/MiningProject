package com.mine.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.mine.component.master.User;
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
	public String salesReport(HttpSession session,Model model,@RequestParam(name="vehicle_no",required=false) String vehicleNumber,@RequestParam(name="material",required=false) String material,@RequestParam(name="quantity",required=false) String quantity
			, @RequestParam(name="payment_type", required=false) String paymentType,@RequestParam(name="client", required=false) String client, @RequestParam(name="f_date", required=false) @DateTimeFormat(iso = ISO.DATE)LocalDate fromDate, @RequestParam(name="t_date", required=false) @DateTimeFormat(iso = ISO.DATE)LocalDate toDate) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		List<Object[]> salesList = reportService.getSupplyDetails(vehicleNumber, quantity, material, paymentType, client, fromDate, toDate);
		model.addAttribute("salesList",salesList);
		model.addAttribute("material_lookup", miningService.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", miningService.getLookupMap("quantity"));
		model.addAttribute("client_lookup", miningService.getClientList(companyId,0,false));
		/*if((details.getQuantity().equalsIgnoreCase("foot")) || (details.getQuantity().equalsIgnoreCase("bucket")) ||
				(details.getQuantity().equalsIgnoreCase("ton"))) {
			//System.out.println("In print quantity test");
			double rate = service.getRate(details.getTyreType(), details.getMaterial(), details.getVehicleType(), details.getQuantity(), companyId);
			double quantity = details.getRate() / rate;
			model.addAttribute("qty",Long.toString(Math.round(quantity)).concat(" ").concat(details.getQuantity()));
		}
		else {
			model.addAttribute("qty",details.getQuantity());
		}*/
		//model.addAttribute();
		return "report/sales_report";
	}
	
	@RequestMapping("ledger_summary")
	public String salesSummary(HttpSession session, Model model, @RequestParam(name="param", required=false, defaultValue="0") int param,
			@RequestParam(name="start_date",required=false) @DateTimeFormat(iso= ISO.DATE) LocalDate startDate,
			@RequestParam(name="end_date",required=false) @DateTimeFormat(iso=ISO.DATE) LocalDate endDate
			) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		List<Object[]> obj = null;
		if(param != 0) {
			LocalDate sDate = startDate;
			LocalDate eDate = endDate;
			if(startDate == null) {
				sDate = LocalDate.MIN;
			}
			if(endDate == null) {
				eDate = LocalDate.now();
			}
			obj = reportService.combinedSalesReport(param, sDate, eDate);
		}
			
		model.addAttribute("records", obj);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		String paramValue = "NA";
		switch(param) {
			case 1 : {
				paramValue = "Owner";
				break;
			}
			case 2 : {
				paramValue = "Contractor";
				break;
			}
			case 3 : {
				paramValue = "Sanchalan";
				break;
			}
			case 4 : {
				paramValue = "Office Expense";
				break;
			}
			case 5 : {
				paramValue = "Journal";
				break;
			}
			case 6 : {
				paramValue = "Others";
				break;
			}
			
		}
		model.addAttribute("param_value",paramValue);
		return "report/summary_of_sales";
	}
	
	@RequestMapping("daywise_summary")
	public String daywiseSalesSummaryControl(Model model, HttpSession session,
			@RequestParam(name="s_date", required=false) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam(name="e_date", required=false) @DateTimeFormat(iso=ISO.DATE) LocalDate endDate) {
		
		if(session.getAttribute("user") == null){
			return "login";
		}
		if(startDate == null) {
			startDate = LocalDate.now();
		}
		if(endDate == null) {
			endDate = LocalDate.now();
		}
		model.addAttribute("summary_sales",reportService.daywiseSalesSummary(startDate, endDate));
		model.addAttribute("s_date", startDate);
		model.addAttribute("e_date", endDate);
		return "report/daywise_sales_summary";
	}
	// ------------------------------ End Sales Control ----------------------------------
	
	
	// ------------------------------ Token Control --------------------------------------
	
	// ------------------------------ End Token Control ----------------------------------
	
	
	// ------------------------------ Client Control --------------------------------------
	@RequestMapping("client_report")
	public String clientReport(HttpSession session,Model model, @RequestParam(name="name",required=false) String name,@RequestParam(name="contact",required=false) String contact, 
				 @RequestParam(name="belongs_to",required=false) String clientType ) {
		if(session.getAttribute("user") == null) {
			return "login";
		}	
		List<Client> clientList = reportService.getClientList(name, contact, clientType != null ? Integer.parseInt(clientType) : 0);
			model.addAttribute("clientList",clientList);
			return "report/client_report";
	}
	// ------------------------------ End Client Control ----------------------------------
	
	
	// ------------------------------ Vehicle Control --------------------------------------
	@RequestMapping("vehicle_report")
	public String vehicleReport(HttpSession session,Model model, @RequestParam(name="vehicle_no",required=false) String vehicleNo,@RequestParam(name="client",required=false) String client, @RequestParam(name="vehicle_type",required=false) String vehicleType,
				@RequestParam(name="tyre_type",required=false, defaultValue="0") int tyreType, @RequestParam(name="belongs_to",required=false) String clientType ) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}	
		List<Vehicle> vehicleList = reportService.getVehicleList(vehicleNo, vehicleType, tyreType, client, clientType != null ? Integer.parseInt(clientType) : 0);
			model.addAttribute("vehicleList",vehicleList);
			model.addAttribute("client",miningService.getClientList(companyId, 0,false));
			model.addAttribute("vehicle_lookup",miningService.getLookupMap("vehicleType"));
			model.addAttribute("tyre_lookup", miningService.getLookupMap("tyreType"));
			return "report/vehicle_report";
	}
	// ------------------------------ End Vehicle Control ----------------------------------
	
	
	// ------------------------------ Rate Report ------------------------------------------
	@RequestMapping("rate_report")
	public String vehicleReport(HttpSession session,Model model, @RequestParam(name="material",required=false) String material,@RequestParam(name="quantity",required=false) String quantity, @RequestParam(name="vehicle_type",required=false) String vehicleType,
				@RequestParam(name="tyre_type",required=false, defaultValue="0") int tyreType) {
		User user = (User)session.getAttribute("user");
		if(user == null) {
				return "login";
		}
		
		List<Rate> rateList = reportService.getRateList(vehicleType, tyreType, quantity, material);
		model.addAttribute("vehicle_lookup",miningService.getLookupMap("vehicleType"));
		model.addAttribute("tyre_lookup", miningService.getLookupMap("tyreType"));
		model.addAttribute("material_lookup", miningService.getLookupMap("materialType"));
		model.addAttribute("quantity_lookup", miningService.getLookupMap("quantity"));
		model.addAttribute("rateList",rateList);
		model.addAttribute("role",user.getRole());
		return "report/rate_report";
	}
	
	@RequestMapping("rate_update_report")
	public String updateRateList(HttpSession session, Model model){
		User user = (User)session.getAttribute("user");
		if( user == null || user.getRole().equalsIgnoreCase("user")) {
			return "login";
		}
		List<Rate> rateList = reportService.getRateList(null, 0, null, null);
		model.addAttribute("rateList",rateList);
		return "report/update_rate";
	}
	// ------------------------------ End Rate Report --------------------------------------
	
	
	// ------------------------------ Cashbook Control --------------------------------------
	
	// ------------------------------ End Cashbook Control ----------------------------------
	
	
	// ------------------------------ Ledger Control --------------------------------------
	
	@RequestMapping("ledger_report")
	public String ledgerReport(HttpSession session,Model model,@RequestParam(name="party", required=false)String partyName, 
			@RequestParam(name="f_date", required=false) @DateTimeFormat(iso=ISO.DATE)LocalDate startDate,
			@RequestParam(name="t_date", required=false) @DateTimeFormat(iso=ISO.DATE)LocalDate endDate) {
		if(session.getAttribute("user") == null) {
			return "login";
		}
		if(startDate == null) {
			startDate = LocalDate.now();
		}
		if(endDate == null) {
			endDate = LocalDate.now();
		}
		
		User user = (User)session.getAttribute("user");
		boolean allowCancel = false;
		String role = user.getRole();
		LocalDate todayDate = LocalDate.now();
		List<String[]> stringArray = null;
		
		/** check if user should be allowed to cancel record.
		 *  If role is admin then user can edit the record.
		 *  If role is user then user can edit record only for todays date. 
		 * */
		if(role.equalsIgnoreCase("admin")) {
			allowCancel = true;
		}
		
		else if(role.equalsIgnoreCase("user") && startDate == todayDate && endDate == todayDate) {
			allowCancel = true;
		}
		
		if(partyName != null && partyName.length() > 0) {
			stringArray = getPartyLedgerEntries(partyName,startDate,endDate);
		}
		model.addAttribute("party_list",miningService.getClientList(companyId, 0));
		model.addAttribute("ledger_records",stringArray);
		model.addAttribute("allow_cancel",allowCancel);
		model.addAttribute("party_",partyName);
		return "report/ledger_report";
	}
	// ------------------------------ End Ledger Control ----------------------------------
	
	// ------------------------------ Report Panel ----------------------------------------
	@RequestMapping("report_panel")
	public String reportPanel(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return "login";
		}
		model.addAttribute("role",user.getRole());
		return "report/report_panel";
	}
	// -------------------------------End Report Panel -----------------------------------
	
	
	// ------------------------------- Cancel Entries ------------------------------------
	
	@RequestMapping("cancel_entries")
	public @ResponseBody String cancelEntries(@RequestParam("id")String id) {
		this.miningService.cancelEntries(id);
		return "success";
	}
	
	
	// ------------------------------ End Cancel Entries --------------------------------
	
	
	
	// ------------------------------ Journal Entry Control -----------------------------
	@RequestMapping(value="journal_list")
	public String journalEntries(Model model, 
			@RequestParam(name="start_date", required=false)@DateTimeFormat(iso=ISO.DATE)LocalDate startDate, 
			@RequestParam(name="end_date", required=false)@DateTimeFormat(iso=ISO.DATE)LocalDate endDate,
			HttpSession session) {
		
		User user = (User)session.getAttribute("user");
		if(user == null) {
				return "login";
		}
		if(startDate == null) {
			startDate = LocalDate.now();
		}
		if(endDate == null) {
			endDate = LocalDate.now();
		}
		model.addAttribute("journal_details",reportService.getJournalReportService(startDate, endDate));
		model.addAttribute("start_date",startDate);
		model.addAttribute("end_date", endDate);
		return "report/journal_report";
	}
	
	
	// ------------------------------ End Journal Entry Control -------------------------
	
	
	// ------------------------------- Support Methods -----------------------------------
	
	
	public List<String[]> getPartyLedgerEntries(String partyName, LocalDate startDate, LocalDate endDate) {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0,0));
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23,59,59));
		List<Ledger> ledgerEntries = this.reportService.getLedgerEntries(partyName, startDateTime, endDateTime);
		Double[] balances = reportService.getBalances(partyName, startDateTime, endDateTime);
		double openingBalance = balances[0];
		double closingBalance = balances[1] + openingBalance;
		double closingCredit = balances[3];
		double closingDebit = balances[2];
		
		List<String[]> listOfRecords = new ArrayList<>();
		
		String [] strArray = null;
		BigDecimal bgOpeningBalance = BigDecimal.valueOf(Math.abs(openingBalance));
		if(openingBalance >= 0) {
			strArray = new String[] {startDate.toString(),"Opening Balance",bgOpeningBalance.toPlainString(),"","","f","",""};
			
		}
		else {
			strArray = new String[] {startDate.toString(),"Opening Balance","",bgOpeningBalance.toPlainString(),"","f","",""};
		}
		listOfRecords.add(strArray);
		
		String buttonEnableFlag = "f";
		String parentLink = null;
		String childLink = null;
		String tokenNumber = null;
		String salesLink = null;
		String rowId = null;
		
		for(Ledger ledger : ledgerEntries) {
			buttonEnableFlag = "f";
			parentLink = ledger.getParentEntryLink() != null? Integer.toString(ledger.getParentEntryLink().getId()) : "";
			salesLink = ledger.getSalesLink() != null? Integer.toString(ledger.getSalesLink().getId()) : "";
			tokenNumber = ledger.getSalesLink() != null ? ledger.getSalesLink().getToken() : "";
			if(!salesLink.equals("")) {
				buttonEnableFlag = "f";
			}
			else{
				buttonEnableFlag = "t";
				rowId = "ledger_"+ledger.getId();
			}
			strArray = new String[] {ledger.getEntryDate().toLocalDate().toString(),ledger.getDescription(),
						Double.toString(ledger.getDebitAmount()),
						Double.toString(ledger.getCreditAmount()),
						ledger.getRemarks(),buttonEnableFlag, rowId, tokenNumber};
			listOfRecords.add(strArray);
		}
		
		BigDecimal bgClosingBalance = BigDecimal.valueOf(Math.abs(closingBalance));
		BigDecimal bgClosingDebit = BigDecimal.valueOf(Math.abs(closingDebit));
		BigDecimal bgClosingCredit = BigDecimal.valueOf(Math.abs(closingCredit));
		strArray = new String[] {endDate.toString(),"Total",bgClosingDebit.toPlainString(),bgClosingCredit.toPlainString(),"","f","",""};
		listOfRecords.add(strArray);
		if(closingBalance >= 0) {
			strArray = new String[] {endDate.toString(),"Closing Balance",bgClosingBalance.toPlainString(),"","","f","",""};
			
		}
		else {
			strArray = new String[] {endDate.toString(),"Closing Balance","",bgClosingBalance.toPlainString(),"","f","",""};
		}
		listOfRecords.add(strArray);
		return listOfRecords;
	}
	
	public String getLedgerText(String target, String source, String party) {
		String ledgerHeading = null; 
		if(target.equalsIgnoreCase(party)) {
			ledgerHeading = "To "+target + " From " + source;
		}
		else {
			ledgerHeading = "From "+source+" To " + target;
		}
		return ledgerHeading;
	}
	
	
	// ------------------------------- End Support Methods -------------------------------
}
