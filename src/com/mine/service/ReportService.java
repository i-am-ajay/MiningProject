package com.mine.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Client;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.Ledger;
import com.mine.component.transaction.SupplyDetails;
import com.mine.dao.ReportDAO;
import com.mine.utilities.MonthName;

@Service("reportService")
public class ReportService {
	@Autowired
	ReportDAO reportDAO;
	
	// ----------------------- Sales Report Service ---------------------------
	public List<Object[]> getSupplyDetails(String vehicleNo,String quantity, String material, String paymentType,String client, LocalDate fromDate, LocalDate toDate){
		return reportDAO.getSalesList(vehicleNo, quantity, material, paymentType,client, fromDate, toDate);
	}
	
	/**
	 * 
	 * @param code - specify which type of client should be picked.
	 * @param period - month-year combination
	 * @return
	 */
	//public List<Object[]> combinedSalesReport(int code, String period){
	public List<Object[]> combinedSalesReport(int code, LocalDate startDate, LocalDate endDate){
		LocalDateTime sTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime eTime = LocalDateTime.of(endDate, LocalTime.MAX);
		return reportDAO.salesSummary(code,sTime, eTime);
	}
	
	public List<Object[]> daywiseSalesSummary(LocalDate startDate, LocalDate endDate){
		List<Object[]> salesArray = reportDAO.getSummaryOfSale(startDate, endDate);
		salesArray = this.processDayswiseArrayForTotalSale(salesArray);
		return salesArray;
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
	
	
	// ----------------------- Support Methods ---------------------------------
	public List<Object[]> processDayswiseArrayForTotalSale(List<Object[]> list){
		long sumTrucks = 0;
		long sumTrallas = 0;
		double sumCash = 0.0;
		double sumCredit = 0.0;
		double sumBank = 0.0;
		for(Object[] obj : list) {
			if(obj[1] != null)
				sumTrucks += (long)obj[1];
			if(obj[2] != null)
				sumTrallas += (long)obj[2];
			if(obj[3] != null)
				sumCash += (double)obj[3];
			if(obj[4] != null)
				sumCredit += (double)obj[4];
			if(obj[5] != null)
				sumBank += (double)obj[5];
		}
		String strSumCash =  BigDecimal.valueOf(sumCash).toPlainString() ;
		String strSumCredit =  new BigDecimal(sumCredit).toPlainString();
		String strSumBank =  new BigDecimal(sumBank).toPlainString();
		Object [] totalArray = new Object[] {"Total",sumTrucks,sumTrallas,strSumCash,strSumCredit,strSumBank};
		list.add(totalArray);
		return list;
	}
	
	// ----------------------- End Support Methods ---------------------------------------------
	
	// ---------------------- Journal Service --------------------------------------------------
	public List<Ledger> getJournalReportService(LocalDate startDate, LocalDate endDate){
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		
		if(startDate == null) {
			startDateTime = LocalDateTime.now();
		}
		else {
			startDateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0));
		}
		
		if(endDate == null) {
			endDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 59));
		}
		else {
			endDateTime = LocalDateTime.of(endDate, LocalTime.of(11, 59));
		}
		
		return reportDAO.getJournalReport(startDateTime, endDateTime);
	}
	
	// ---------------------- End Journal Service ----------------------------------------------
	
}
