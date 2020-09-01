package com.mine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Client;
import com.mine.component.master.Company;
import com.mine.component.master.GeneralData;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
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
	public void saveRate(Rate rate, int companyId) {
		dao.addRate(rate, companyId);
	}
}
