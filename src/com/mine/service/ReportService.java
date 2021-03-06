package com.mine.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Client;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.Ledger;
import com.mine.component.transaction.SupplyDetails;
import com.mine.dao.ReportDAO;

@Service("reportService")
public class ReportService {
	@Autowired
	ReportDAO reportDAO;
	
	// ----------------------- Sales Report Service ---------------------------
	public List<Object[]> getSupplyDetails(String vehicleNo,String quantity, String material, String paymentType,String client, LocalDate fromDate, LocalDate toDate){
		return reportDAO.getSalesList(vehicleNo, quantity, material, paymentType,client, fromDate, toDate);
	}
	
	public List<Object[]> combinedSalesReport(int code){
		return reportDAO.salesSummary(code);
	}
	
	// ----------------------- End Sales Report Service -----------------------
	
	
	// ----------------------- Vehicle Report Service ---------------------------
	public List<Vehicle> getVehicleList(String vehicleNo, String vehicleType, int tyreType, String client, int clientType){
		return reportDAO.getVehicleList(vehicleNo, vehicleType, tyreType, client, clientType);
	}
	
	// ----------------------- End Vehicle Report Service -----------------------
	
	
	// ----------------------- Client Report Service ----------------------------
	public List<Client> getClientList(String name, String clientContact, int clientType){
		return reportDAO.getClientList(name, clientContact, clientType);
	}

	
	// ----------------------- End Client Report Service ------------------------
	
	
	// ----------------------- Rate Report Service ----------------------------
	public List<Rate> getRateList(String vehicleType, int tyreType, String quantity, String material){
		return reportDAO.getRateList(vehicleType, tyreType, quantity,material);
	}
	
	// ----------------------- End Rate Report Service ------------------------
	
	// ----------------------- Token Report Service ----------------------------
	
	
	// ----------------------- End Token Report Service ------------------------
	
	
	// ----------------------- Ledger Report Service ----------------------------
	public List<Ledger> getLedgerEntries(String name, LocalDateTime startDate, LocalDateTime endDate){
		return reportDAO.ledgerEntries(name, startDate, endDate);
	}
	
	public Double[] getBalances(String name, LocalDateTime startDate, LocalDateTime endDate) {
		return reportDAO.getBalances(name, startDate, endDate);
	}
	
	// ----------------------- End Ledger Report Service ------------------------
	
	
	// ----------------------- Cancel Entry -------------------------------------
	
	
	// ----------------------- End Cancel Entry --------------------------------
	
	
	
}
