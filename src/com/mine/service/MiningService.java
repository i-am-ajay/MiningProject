package com.mine.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
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
import com.mine.utilities.DefineTypesAndCategories;
import com.mine.utilities.ParameterValueFetcher;

@Service
public class MiningService {
	@Autowired
	private MineDAO dao;
	
	// -------------------------------- Client Service ----------------------------------------
	
	public Map<Integer,String> getClientList(int companyId, int clientTypeId){
		return getClientList(companyId, clientTypeId, true);
	}
	
	public Map<Integer,String> getClientList(int companyId, int clientTypeId, boolean type){
		List<Client> clientList = dao.getClientList(companyId, clientTypeId, type);
		Map<Integer,String> map = new HashMap<>();
		for(Client client : clientList) {
			map.put(client.getClientId(), client.getName());
		}
		return map;
	}
	
	/*public Map<Integer,String> getPartyList(){
		List<Client> clientList;
	}*/
	
	public Client getClient(String clientName) {
		return dao.clientExists(clientName);
	}
	
	public String saveClient(Client client, int lookupId,User user, int company, String role) {
		return dao.saveClient(client, lookupId,company, user, role);
	}
	
	// ------------------------------- Client Service End ---------------------------------------
	
	
	
	
	// ------------------------------- Company Service -----------------------------------------
	/**
	 *  Update or save company. 
	 * @param company
	 * @param user
	 * @param role - role will decide if user may update company or not
	 * @return String that tells if company created successfully, updated, already exists or failed.
	 */
	public String saveCompany(Company company,User user, String role) {
		return dao.saveCompany(company,user, role);
	}
	
	public Company getCompany(String companyName) {
		return dao.getCompany(companyName);
	}
	// ------------------------------- End Company Serivce --------------------------------------
	
	
	
	// -------------------------- Vehicle Service -----------------------------
	public String saveVehicle(Vehicle vehicle, int client, int company, User user,String role) {
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
	public String saveRate(Rate rate, int companyId, User user) {
		return dao.addRate(rate, companyId, user);
		
	}
	
	public double updateRate(int id, double rate) {
		return dao.updateRate(id, rate);
	}
	
	public double getRate(String tyreType, String materialType, String truckType, String quantity, int companyId) {
		return dao.getRate(tyreType, truckType, materialType, quantity,companyId);
	}
	
	// ------------------------------ End Rate Service --------------------------------
	
	
	
	// ------------------------------ Supply Service ----------------------------------
	
	public void saveSupplyDetails(SupplyDetails supplyDetails, User user, int companyId) {
		Parameters params = this.getParameters();
		dao.addSalesToCashAndLedger(supplyDetails, user);
		
		// both expenses will be added in credit table as we are not paying them in cash. 
		/* add an entry of sanchalan expense. 
		 * 1) If final rate is 5000 or less then Sanchalan will be 1000 or any decided rate.
		 * 2) If vehicle sold is less than a threshhold value then sanchalan expense is 2700, if threshhold is crossed rate is reduced.
		 * 3) We'll add same cost for all vehicle i.e cost after threshold is reached, 
		 * if threshold is not crossed then an entry will be passed to add addition price in expense.
		 * */
		Client client = supplyDetails.getVehicle().getClientId();
		LocalDateTime dateTime = LocalDateTime.now();
		double sancalanAmount = 0.0;
		if(!supplyDetails.getMaterial().equalsIgnoreCase("bugada")) {
			sancalanAmount = supplyDetails.getFinalRate() <= params.getFreeLimit()  ? params.getSanchalanOnFree() : ParameterValueFetcher.getSanchalanAmount(params,supplyDetails.getVehicle().getVehicleType());
		}
		Client sanchalanPerson = dao.getSanchalanPerson(companyId);
		dao.addDepositeOrExpense(sanchalanPerson.getClientId(), sancalanAmount, 
				"Expense", "Sanchalan","Sanchalan", 
				"Credit", supplyDetails, null, user, dateTime);
		
		
		// If it is comission agent vehicle add comission expense too. if vehicle is not a free vehicle.
		if(client.getClientType().getDescription().equalsIgnoreCase("Contractor")) {
			if(supplyDetails.getFinalRate() != 0.0) {
				dao.addDepositeOrExpense(client.getClientId(), supplyDetails.getVehicle().getClientId().getComission(), 
						"Expense", "Sale Commission","Sale Commission", 
						"Credit",supplyDetails,null, user, dateTime);
			}
		}
		// if driver wapsi is there then it will be a cash expense. 
		/*
		 * double driverReturnAmount = supplyDetails.getDriverReturn();
		 * if(driverReturnAmount > 0.0) { Client driverReturn =
		 * dao.clientExists("Driver Return");
		 * dao.addDepositeOrExpense(driverReturn.getClientId(), driverReturnAmount,
		 * "CashExpense",
		 * DefineTypesAndCategories.cashExpenseDriverReturn.getCashbookType(),
		 * DefineTypesAndCategories.cashExpenseDriverReturn.getCashbookCategory(),
		 * DefineTypesAndCategories.cashExpenseDriverReturn.getCreditType(),
		 * DefineTypesAndCategories.cashExpenseDriverReturn.getCreditCategory(),
		 * DefineTypesAndCategories.cashExpenseDriverReturn.getLedgerType(),"Cash",
		 * supplyDetails, "Driver Return Paid",user); }
		 */
	}
	
	// get data of supply
	
	public List<SupplyDetails> getTop10Records(){
		LocalDateTime endDate = LocalDateTime.now();
		LocalDateTime startDate = endDate.minusDays(1);
		
		return dao.getSaleData(0, startDate, endDate);
		
	}
	
	public boolean cancleSales(int salesId) {
		System.out.println(salesId);
		return dao.cancleEntry("sales", salesId);
	}
	
	// One Day Sale Count
	public long getTodaySaleCount() {
		long count = dao.getSaleCount();
		System.out.println(count);
		return count;
	}
	// ----------------------------- End Supply Service ------------------------------------
	
	
	// ----------------------------- Ledger Entries ----------------------------------------
	public void ledgerEntries(String partyName, double amount, String type, 
			String expenseCategory, String remarks, User user, LocalDateTime date) {
			System.out.println(partyName);
			Client party = dao.clientExists(partyName);
			String partyType = party.getClientType().getDescription();
			if(type.equalsIgnoreCase("income")) {
				if(partyType.equalsIgnoreCase("owner")) {
				dao.addDepositeOrExpense(party.getClientId(), amount, "deposite", 
						null,null,expenseCategory, null,remarks,user, date);
				}
				else {
					dao.addDepositeOrExpense(party.getClientId(), amount, "deposite", 
							null,null,expenseCategory,null,remarks, user,date);
				}
		}
		else {
			if(expenseCategory.equalsIgnoreCase("cash_expense") || expenseCategory.equalsIgnoreCase("bank")) {
				if(partyType.equalsIgnoreCase("office")) {
				dao.addDepositeOrExpense(
						party.getClientId(), amount, "CashExpense", 
						null,null,expenseCategory,null,remarks, user,date);
				}
				else if(partyType.equalsIgnoreCase("contractor")) {
					dao.addDepositeOrExpense(
							party.getClientId(), amount, "CashExpense", 
							null, null ,expenseCategory,null,remarks, user,date);
				}
				else if(partyType.equalsIgnoreCase("sanchalan")) {
					dao.addDepositeOrExpense(
							party.getClientId(), amount, "CashExpense", 
							null, null,expenseCategory,null,remarks, user,date);
				}
				else {
					dao.addDepositeOrExpense(
							party.getClientId(), amount, "CashExpense", 
							null, null,expenseCategory,null,remarks, user,date);
				}
			}
			else {
				if(partyType.equalsIgnoreCase("sanchalan")) {
					dao.addDepositeOrExpense(
							party.getClientId(), amount, "CreditExpense", 
							null, null,expenseCategory,null,remarks, user,date);
				}
				else {
					dao.addDepositeOrExpense(
							party.getClientId(), amount, "CreditExpense", 
							null, null,expenseCategory,null,remarks, user, date);
				}
			}
		}
	}
	
	public void journalEntries(String debtor, String creditor, double amount, String remarks, LocalDateTime dateTime, User user) {
		//dao.journalEntry(debtor, creditor, amount, remarks, dateTime, user);
	}
	
	public boolean cancelEntries(String id) {
		String [] idParts = id.split("_"); 
		return dao.cancleEntry(idParts[0], Integer.parseInt(idParts[1]));
	}
	
	// ----------------------------- End Ledger Entries ------------------------------------
	
	// ----------------------------- Token Service -----------------------------------------
	public Token getToken() {
		return dao.getToken();
	}
	
	public void updateToken(Token token) {
		dao.updateToken(token);
	}
	
	// ---------------------------- End Token Service ------------------------------------
	
	
	// ---------------------------- User authentication Service --------------------------
	public User getUser(String emp) {
		User user = dao.getUser(emp);
		if(user != null) {
			return user;
		}
		else {
			return null;
		}
	}
	
	public boolean createUser(String username, String password, String role, User createdBy, boolean status) {
		User dbuser = getUser(username);
		boolean isCreated = false;
		if(dbuser == null) {
			isCreated = true;
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setRole(role);
			user.setCreatedBy(createdBy);
			user.setActive(status);
			dbuser = user;
		}
		else {
			if(password != null && password != "") {
				dbuser.setPassword(password);
			}
			dbuser.setCreatedBy(createdBy);
			dbuser.setActive(status);
			dbuser.setRole(role);
		}
		dao.saveUser(dbuser);
		return isCreated;
	}
	
	public void updatePassword(User user) {
		dao.updatePassword(user);
	}
	
	
	
	// ----------------------------End User authntication Service ---------------------------
	
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
	
	// ---------------------------- Parameters Service -----------------------------------
	public String updateParameters(Parameters parameters) {
		boolean flag =  dao.updateParameters(parameters);
		String status = "fails";
		if(flag) {
			status = "updated";
		}
		return status;
	}
	
	// ----------------------------- End Parameters Service ------------------------------
	
	// ----------------------------- test methods ---------------------------------------
	public void saveLedger() {
		dao.saveLedger();
	}
	
	//------------------------------ end test methods -----------------------------------
}
