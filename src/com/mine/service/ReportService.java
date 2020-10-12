package com.mine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Client;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.dao.ReportDAO;

@Service("reportService")
public class ReportService {
	@Autowired
	ReportDAO reportDAO;
	
	// ----------------------- Sales Report Service ---------------------------
	
	
	// ----------------------- End Sales Report Service -----------------------
	
	
	// ----------------------- Vehicle Report Service ---------------------------
	public List<Vehicle> getVehicleList(String vehicleNo, String vehicleType, int tyreType, String client, int clientType){
		/*System.out.println("Vehicle No"+vehicleNo);
		System.out.println("Vehicle Type"+vehicleType);
		System.out.println("Tyre Type"+tyreType);
		System.out.println("Client"+client);
		System.out.println("Client Type"+clientType);*/
		return reportDAO.getVehicleList(vehicleNo, vehicleType, tyreType, client, clientType);
	}
	
	// ----------------------- End Vehicle Report Service -----------------------
	
	
	// ----------------------- Client Report Service ----------------------------
	public List<Client> getClientList(String name, String clientContact, int clientType){
		/*System.out.println("Vehicle No"+vehicleNo);
		System.out.println("Vehicle Type"+vehicleType);
		System.out.println("Tyre Type"+tyreType);
		System.out.println("Client"+client);
		System.out.println("Client Type"+clientType);*/
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
	
	
	// ----------------------- End Ledger Report Service ------------------------
	
	

	
	
	
}
