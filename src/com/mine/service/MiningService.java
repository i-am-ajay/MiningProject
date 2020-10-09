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
import com.mine.component.master.User;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;
import com.mine.dao.MineDAO;

@Service
public class MiningService {
	@Autowired
	private MineDAO dao;
	
	// -------------------------------- Client Service ----------------------------------------
	
	public Map<Integer,String> getClientList(Company company, int clientTypeId){
		List<Client> clientList = dao.getClientList(company, clientTypeId);
		Map<Integer,String> map = new HashMap<>();
		for(Client client : clientList) {
			map.put(client.getClientId(), client.getName());
		}
		return map;
	}
	
	public Client getClient(String clientName) {
		return dao.clientExists(clientName);
	}
	
	public String saveClient(Client client, int lookupId,int company, int user, String role) {
		return dao.saveClient(client, lookupId,company, user, role);
	}
	
	// ------------------------------- Client Service End ---------------------------------------
	
	
	
	
	// ------------------------------- Company Service -----------------------------------------
	/**
	 *  Update of save company. 
	 * @param company
	 * @param user
	 * @param role - role will decide if user may update company or not
	 * @return String that tells if company created successfully, updated, already exists or failed.
	 */
	public String saveCompany(Company company,int user, String role) {
		return dao.saveCompany(company,user, role);
	}
	
	public Company getCompany(String companyName) {
		return dao.getCompany(companyName);
	}
	// ------------------------------- End Company Serivce --------------------------------------
	
	
	
	// -------------------------- Vehicle Service -----------------------------
	public String saveVehicle(Vehicle vehicle, int client, int company, int user,String role) {
		return dao.saveVehicle(vehicle, client, company, user,role);
	}
	
	public boolean isVehicleRegistered(String vehicleNo) {
		return dao.vehicleExists(vehicleNo);	
	}
	
	public Vehicle getVehicle(String vehicleNo) {
		return dao.getVehicle(vehicleNo);
	}
	
	//--------------------------- End Vehicle Service -------------------------------
	
	// ------------------------------ Rate Service ----------------------------------
	public String saveRate(Rate rate, int companyId, int user) {
		return dao.addRate(rate, companyId, user);
		
	}
	
	public double getRate(String tyreType, String materialType, String truckType, String quantity, int companyId) {
		return dao.getRate(tyreType, truckType, materialType, quantity,companyId);
	}
	
	// ------------------------------ End Rate Service --------------------------------
	
	
	
	// ------------------------------ Supply Service ----------------------------------
	
	public void saveSupplyDetails(SupplyDetails supplyDetails, int user) {
		dao.addSales(supplyDetails, user);
	}
	
	// get data of supply
	
	public List<SupplyDetails> getTop10Records(){
		LocalDateTime startDate = LocalDateTime.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now();
		return dao.getSaleData(8, startDate, endDate);
		
	}
	
	// ----------------------------- End Supply Service ------------------------------------
	
	
	// ----------------------------- Token Service -----------------------------------------
	public Token getToken() {
		return dao.getToken();
	}
	
	public void updateToken(Token token) {
		dao.updateToken(token);
	}
	
	// ---------------------------- End Token Service ------------------------------------
	
	
	// ----------------------------- Misc Service ----------------------------------------
	public Map<Integer,String> getLookupMap(String category){
		List<GeneralData> objList = dao.getLookupMap(category);
		Map<Integer,String> map = new HashMap<>();
		for(GeneralData obj : objList) {
			map.put(obj.getId(), obj.getDescription());
		}
		return map;
	}
	
	public Parameters getParameters() {
		return dao.getParameters();
	}
	
	// ---------------------------- End Misc Service -------------------------------------
}
