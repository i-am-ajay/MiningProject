package com.mine.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Client;
import com.mine.component.master.Company;
import com.mine.component.master.GeneralData;
import com.mine.component.master.Parameters;
import com.mine.component.master.Rate;
import com.mine.component.master.Token;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;
import com.mine.dao.MineDAO;

@Service
public class MiningService {
	@Autowired
	private MineDAO dao;
	
	public Map<Integer,String> getLookupMap(String category){
		List<GeneralData> objList = dao.getLookupMap(category);
		Map<Integer,String> map = new HashMap<>();
		for(GeneralData obj : objList) {
			map.put(obj.getId(), obj.getDescription());
		}
		return map;
	}
	
	public Map<Integer,String> getClientList(Company company, int clientTypeId){
		List<Client> clientList = dao.getClientList(company, clientTypeId);
		Map<Integer,String> map = new HashMap<>();
		for(Client client : clientList) {
			map.put(client.getClientId(), client.getName());
		}
		return map;
	}
	
	public void saveClient(Client client, int lookupId) {
		dao.saveClient(client, lookupId);
	}
	
	public void saveCompany(Company company) {
		dao.saveCompany(company);
	}
	
	public void saveVehicle(Vehicle vehicle, int client, int company) {
		dao.saveVehicle(vehicle, client, company);
	}
	
	public boolean isVehicleRegistered(String vehicleNo) {
		return dao.vehicleExists(vehicleNo);
		
	}
	public boolean saveRate(Rate rate, int companyId) {
		return dao.addRate(rate, companyId);
		
	}
	
	public Vehicle getVehicle(String vehicleNo) {
		return dao.getVehicle(vehicleNo);
	}
	
	public void saveSupplyDetails(SupplyDetails supplyDetails) {
		dao.addSales(supplyDetails);
	}
	
	// get data of supply
	
	public List<SupplyDetails> getTop10Records(){
		LocalDateTime startDate = LocalDateTime.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now();
		return dao.getSaleData(8, startDate, endDate);
		
	}
	
	public double getRate(String tyreType, String materialType, String truckType, String quantity, int companyId) {
		return dao.getRate(tyreType, truckType, materialType, quantity,companyId);
	}
	
	public Parameters getParameters() {
		return dao.getParameters();
	}
	
	public Token getToken() {
		return dao.getToken();
	}
	
	public void updateToken(Token token) {
		dao.updateToken(token);
	}
}
