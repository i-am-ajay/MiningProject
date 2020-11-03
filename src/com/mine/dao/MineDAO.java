package com.mine.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mine.component.master.Client;
import com.mine.component.master.Company;
import com.mine.component.master.GeneralData;
import com.mine.component.master.Parameters;
import com.mine.component.master.Rate;
import com.mine.component.master.Token;
import com.mine.component.master.User;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.CashBookRecord;
import com.mine.component.transaction.CreditRecord;
import com.mine.component.transaction.Ledger;
import com.mine.component.transaction.SupplyDetails;
import com.mine.utilities.DefineTypesAndCategories;
import com.mine.utilities.TypesAndCategories;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

@Repository
public class MineDAO {
	@Autowired
	SessionFactory factory;
	
	// ------------------------- Vehicle Related DAO Methods -------------------------------------
	@Transactional
	public String saveVehicle(Vehicle vehicle, int clientId, int companyId, User createdBy, String role) {
		Session session = null;
		Client client = null;
		Company company = null;
		String status = "fail";
		boolean exists = false;
		
		if(vehicleExists(vehicle.getVehicleNo())) {
			exists = true;
			if(role.equalsIgnoreCase("user")) {
				return "exists";
			}
				status = "updated";
		}
		
		session = factory.getCurrentSession();
		client = session.load(Client.class, clientId);
		company = session.load(Company.class, companyId);
		vehicle.setClientId(client);
		vehicle.setCompanyId(company);
		vehicle.setCreatedById(createdBy);
		if(exists){
			session.merge(vehicle);
		}
		else {
			session.saveOrUpdate(vehicle);
		}
		if(!status.equalsIgnoreCase("updated"))
			status = "success";
		return status;
	}
	
	
	/**
	 * This method will get a vehcile from database based on vehicle id
	 * @param vehicleNo
	 * @return Vehicle
	 */
	@Transactional
	public Vehicle getVehicle(String vehicleNo) {
		Session session = factory.getCurrentSession();
		Vehicle vehicle = session.get(Vehicle.class, vehicleNo);
		return vehicle;
	}
	
	@Transactional
	public boolean vehicleExists(String vehicle_no) {
		boolean vehicleRegistered = false;
		Session session = factory.getCurrentSession();
		Vehicle vehicle = session.get(Vehicle.class, vehicle_no);
		if(vehicle != null) {
			vehicleRegistered = true;
		}
		return vehicleRegistered;
	}
	
	// ------------------------------- End Vehicle DAO Methods ----------------------------------
	
	
	
	// ------------------------------ Company Related DAO Methods -------------------------------
	@Transactional
	public String saveCompany(Company company,User user, String role) {
		/**
		 * Checks if client already exists.
		 * If client exists and role is 'user' then change status to exits and return.
		 * If client exists and role is admin and then change status to updated and set exists flag. This flag
		   marks that company object is already in session.
		 * If company object exists in session then merge the company object. Else save company object.
		 */
		Session session = factory.getCurrentSession();
		String status = "fails";
		boolean exists = false;
		
		Company existingCompany = this.getCompany(company.getName());
		// Check if company already exist
		if(existingCompany != null) {
			exists = true;
			if(role.equalsIgnoreCase("user")) {
				return "exists";
			}	
			existingCompany.setCompanyContact(company.getCompanyContact());
			existingCompany.setCompanyEmail(company.getCompanyEmail());
			existingCompany.setLocation(company.getLocation());
			existingCompany.setCreatedBy(user);
			existingCompany.setName(company.getName());
			company = existingCompany;
			status = "updated";
		}
		company.setCreatedBy(user);
		company.setCreationDate(LocalDate.now());
		session.saveOrUpdate(company);
		if(!status.equalsIgnoreCase("updated"))
			status = "success";
		return status;
	}
	
	public boolean companyExists(Company company) {
		boolean status = false;
		if(getCompany(company.getName()) != null) {
			status = true;
		}
		return status;
	}
	
	@Transactional
	public Company getCompany(String name) {
		Company company = null;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> from = criteria.from(Company.class);
		criteria.where(builder.and(builder.equal(from.get("name"),name),
				builder.isNull(from.get("endDate"))));
		criteria.orderBy(builder.asc(from.get("creationDate")));
		
		TypedQuery<Company> query = session.createQuery(criteria);
		List<Company> companyList = query.getResultList();
		if(companyList != null && companyList.size() > 0) {
			company = companyList.get(0);
		}
		return company;
	}
	
	//------------------------------- End Company DAO Methods -----------------------------------
	
	
	
	// ------------------------------- Client Related DAO Methods
	/**
	 * Save or update client in the system based on user roles.
	 * 
	 * @param client
	 * @param lookupId
	 * @param companyId
	 * @param createdBy
	 * @param role - admin, user - only admins are allowed to make updates in the data.
	 * @return
	 */
	@Transactional
	public String saveClient(Client client, int lookupId,int companyId, User createdBy, String role) {
		/**
		 * Check if client objects already exists. If yes:
		 	a) If role is user then set status to exists and return.
		 	b) If role is admin then we need to update client. Pull the client object from the db, 
		 		update all fields of persistent client from client parameter and then update. change
		 		exists flag to true.
		 		
		 * Save the oject. and if updated flag is not set then update the flag to success.
		 		
		 */
		Session session = factory.getCurrentSession();
		boolean exists = false;
		String status = "fails";
		Client clientDb = clientExists(client.getName());
		if(clientDb != null) {
			exists = true;
			if(role.equalsIgnoreCase("user"))
				return "exists";
			clientDb.setClientAddress(client.getClientAddress());
			clientDb.setClientContact(client.getClientContact());
			clientDb.setDiscount(client.getDiscount());
			clientDb.setName(client.getName());
			clientDb.setComission(client.getComission());
			client = clientDb;
			status = "updated";
		}
		GeneralData data = session.get(GeneralData.class, lookupId);
		Company company = session.get(Company.class, companyId);
		client.setClientType(data);
		client.setCompany(company);
		client.setCreatedBy(createdBy);
		session.saveOrUpdate(client);
		if(!exists)
			status = "success";
		return status;
	}
	
	@Transactional
	public Client clientExists(String clientName){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> from = criteria.from(Client.class);
		Client clientDb = null;
		criteria.where(builder.and(builder.equal(from.get("name"),clientName),
				builder.isNull(from.get("endDate"))));
		
		TypedQuery<Client> query = session.createQuery(criteria);
		List<Client> clientList = query.getResultList();
		if(clientList != null && clientList.size() > 0) {
			clientDb = clientList.get(0);
		}
		return clientDb;
	}
	
	@Transactional
	public List<Client> getClientList(int companyId, int clientTypeId) {
		Session session = factory.getCurrentSession();
		Company company = session.get(Company.class, companyId);
		GeneralData clientType = session.get(GeneralData.class, clientTypeId);
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> from = criteria.from(Client.class);
		
		predicateList.add(builder.equal(from.get("company"),company));
		predicateList.add(builder.isNull(from.get("endDate")));
		if(clientType != null) {
			predicateList.add(builder.equal(from.get("clientType"), clientType));
		}
		Predicate[] predicateArray = predicateList.toArray(new Predicate[predicateList.size()]);
		
		criteria.where(predicateArray);
		
		TypedQuery<Client> clientQuery = session.createQuery(criteria);
		List<Client> client = clientQuery.getResultList();
		return client;
	}
	
	// get sanchalan person name
	@Transactional
	public Client getSanchalanPerson(int companyId) {
		GeneralData data = this.getGeneralDataDescBased("Sanchalan");
		Session session = factory.getCurrentSession();
		Company company = session.get(Company.class, companyId);
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> from = criteria.from(Client.class);
		
		predicateList.add(builder.equal(from.get("clientType"),data));
		predicateList.add(builder.isNull(from.get("endDate")));
		
		criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
		
		TypedQuery<Client> query = session.createQuery(criteria);
		Client client = null;
		try {
			client = query.getSingleResult();
		}
		catch(Exception ex) {
			
		}
		return client;
	}
	
	// ------------------------------ End Client DAO Methods ---------------------------------
	
	
	
	
	
	
	
	// ------------------------------- Rate DAO Methods --------------------------------------
	@Transactional
	public String addRate(Rate rate, int companyId, User user) {
		if(getRate(rate.getTyreType(),rate.getTruckType(), rate.getMaterialType(), rate.getQuantity(), companyId) != 0.0d) {
			System.out.println("System.");
			return "exists";
		}
		String rateSaved = "fails";
		Session session = factory.getCurrentSession();
		Company company = session.get(Company.class, companyId);
		rate.setCompany(company);
		rate.setCreatedById(user);
		session.save(rate);
		rateSaved = "success";
		return rateSaved;
	}
	
	@Transactional
	public double  getRate(String tyreType, String truckType,String materialType, String quantity, int companyId) {
		double rate = 0.0;
		Session session = factory.getCurrentSession();
		Company company = session.load(Company.class, companyId);
		
		System.out.println("rate called");
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rate> criteria = builder.createQuery(Rate.class);
		Root<Rate> from = criteria.from(Rate.class);
		List<Predicate> predicateList = new ArrayList<>();
		if(!quantity.equalsIgnoreCase("bucket") && !quantity.equalsIgnoreCase("ton") && !quantity.equalsIgnoreCase("foot")) {
			predicateList.add(builder.equal(from.get("tyreType"), tyreType));
			predicateList.add(builder.equal(from.get("truckType"), truckType.strip()));
		}
		else {
			predicateList.add(builder.isNull(from.get("tyreType")));
			predicateList.add(builder.isNull(from.get("truckType")));
		}
		predicateList.add(builder.equal(from.get("materialType"), materialType));
		predicateList.add(builder.equal(from.get("quantity"), quantity));
		predicateList.add(builder.isNull(from.get("endDate")));
		predicateList.add(builder.equal(from.get("company"), company));
		criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
		//System.out.println(tyreType+" "+materialType+" "+truckType+" "+quantity+" "+company);
		TypedQuery<Rate> rateQuery = session.createQuery(criteria);
		Rate rateObj = null;
		try {
			rateObj = rateQuery.getSingleResult();
			rate = rateObj.getRate();
		}
		
		catch(NoResultException | NonUniqueResultException | IllegalStateException ex) { 
			System.out.println("tyreType"+tyreType +"MT: "+materialType+"TT: "+truckType+"quantity"+quantity);
			ex.printStackTrace();
		}
		return rate;
	}
	
	@Transactional
	public double updateRate(int id, double rate) {
		double tempRate = rate;
		Session session = factory.getCurrentSession();
		Rate dbRate = session.get(Rate.class, id);
		dbRate.setRate(rate);
		session.update(dbRate);
		tempRate = dbRate.getRate();
		return tempRate;
	}
	
	/*@Transactional
	public double getRate(String tyreType, String truckType, String materialType, String qty) {
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rate> criteria = builder.createQuery(Rate.class);
		Root<Rate> rateRoot = criteria.from(Rate.class);
		
		criteria.where(builder.and(builder.equal(rateRoot.get("tyreType"),tyreType), 
				builder.equal(rateRoot.get("truckType"), truckType)),
				builder.equal(rateRoot.get("materialType"), materialType),
				builder.equal(rateRoot.get("qty"), truckType),
				builder.isNull(rateRoot.get("endDate")));
		
		TypedQuery<Rate> query = session.createQuery(criteria);
		Rate rate = query.getSingleResult();
		return rate.getRate();
		
	}*/
	// ----------------------------- End Rate DAO Methods -----------------------------------------
	
	
	
	// ----------------------------- Sales Related DAO Methods ------------------------------------
	@Transactional
	public void addSales(SupplyDetails details, User user) {
		Session session = factory.getCurrentSession();
		// save sales record in Cash or credit table.
		TypesAndCategories cashSale = DefineTypesAndCategories.cashSale; 
		TypesAndCategories creditSale = DefineTypesAndCategories.creditSale;
		TypesAndCategories bankSale = DefineTypesAndCategories.bankSale;
		
		// get vehicle and set it's linking.
		Vehicle vehicle = session.get(Vehicle.class, details.getVehicle().getVehicleNo());
		details.setVehicle(vehicle);
		details.setUser(user);
		details.setStatus(true);
		
		if(details.getPaymentType().equalsIgnoreCase("cash")) {
			CashBookRecord cashRecord = new CashBookRecord();
			cashRecord.setAmount(details.getFinalRate());
			cashRecord.setParty(vehicle.getClientId());
			cashRecord.setSales(details);
			cashRecord.setType(cashSale.getCashbookType());
			cashRecord.setCategory(cashSale.getCashbookCategory());
			cashRecord.setStatus(true);
			cashRecord.setPaymentType("Cash");
			
			// ledger record
			
			Ledger ledger = new Ledger();
			ledger.setCreditAmount(details.getFinalRate());
			ledger.setSource("Sales");
			ledger.setTarget("Cash");
			ledger.setType(cashSale.getLedgerType());
			ledger.setCreatedBy(user);
			ledger.setStatus(true);
			
			
			cashRecord.setLedger(ledger);
			ledger.setCashbookLinking(cashRecord);
			ledger.setSalesLink(details);
			
			session.save(ledger);
			session.save(cashRecord);
			
		}
		else if(details.getPaymentType().equalsIgnoreCase("bank")) {
			CashBookRecord cashRecord = new CashBookRecord();
			cashRecord.setAmount(details.getFinalRate());
			cashRecord.setParty(vehicle.getClientId());
			cashRecord.setSales(details);
			cashRecord.setType(bankSale.getCashbookType());
			cashRecord.setCategory(bankSale.getCashbookCategory());
			cashRecord.setStatus(true);
			cashRecord.setPaymentType("Bank");
			
			// ledger record
			
			Ledger ledger = new Ledger();
			ledger.setCreditAmount(details.getFinalRate());
			ledger.setSource("Sales");
			ledger.setTarget("Bank");
			ledger.setType(bankSale.getLedgerType());
			ledger.setCreatedBy(user);
			ledger.setStatus(true);
			
			cashRecord.setLedger(ledger);
			ledger.setCashbookLinking(cashRecord);
			ledger.setSalesLink(details);
			
			session.save(ledger);
			session.save(cashRecord);
		}
		else {
			CreditRecord creditRecord = new CreditRecord();
			creditRecord.setAmount(details.getFinalRate());
			creditRecord.setClient(vehicle.getClientId());
			creditRecord.setSales(details);
			creditRecord.setStatus(true);
			creditRecord.setType(creditSale.getCreditType());
			creditRecord.setCategory(creditSale.getCreditCategory());
			
			// ledger record
			Ledger ledger = new Ledger();
			ledger.setDebitAmount(details.getFinalRate());
			ledger.setSource("Sale");
			ledger.setTarget(vehicle.getClientId().getName());
			ledger.setType(creditSale.getLedgerType());
			ledger.setCreditRecordLinking(creditRecord);
			ledger.setSalesLink(details);
			ledger.setCreatedBy(user);
			ledger.setStatus(true);
			
			session.save(creditRecord);
			session.save(ledger);
		}
		session.save(details);
	}
	
	// amount added from cash transaction screen income and expense both will be registered here.
	
	
	// Non Used method.
	/*@Transactional
	public void saveSupplyDetails(SupplyDetails details) {
		Session session = factory.getCurrentSession();
		session.save(details);
		session.flush();
	}*/
	
	@Transactional
	public List<SupplyDetails> getSaleData(int numRecords, LocalDateTime startDate, LocalDateTime endDate){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SupplyDetails> query = builder.createQuery(SupplyDetails.class);
		Root<SupplyDetails> from = query.from(SupplyDetails.class);
		query.where(builder.between(from.get("salesDate"), startDate, endDate)).orderBy(builder.desc(from.get("salesDate")));
		//builder.equal(from.get("status"), true)
		
		TypedQuery<SupplyDetails> details = session.createQuery(query);
		return details.getResultList();
	}
	// ---------------------------- End Sales DAO Methods ----------------------------------------
	
	
	// ---------------------------- Expense and Deposite related DAO -----------------------------
	//natureOfTransaction - Cash/Bank/Credit
	// SupplyDetails - If entry is made through Sale for contractor, sanchalan and driver return. Then pass sales details too.
	@Transactional
	public void addDepositeOrExpense(int clientId, double amount, String type, String cashbookType, String cashbookCategory, String creditType, String creditCategory, String ledgerType, String natureOfTransaction, SupplyDetails details, String remarks, User user) {
		CashBookRecord cashRecord = null;
		Ledger ledger = null;
		CreditRecord creditRecord = null;
		
		Session session = factory.getCurrentSession();
		Client client = session.get(Client.class, clientId);
		
		if(type.equals("deposite")) {
			cashRecord = new CashBookRecord();
			cashRecord.setAmount(amount);
			cashRecord.setCategory(cashbookCategory);
			cashRecord.setType(cashbookType);
			cashRecord.setStatus(true);
			cashRecord.setParty(client);
			cashRecord.setSales(details);
			cashRecord.setPaymentType(natureOfTransaction);
			
			// credit book entry -  if it's a cash deposite an entry will be sent to credit records too.
			creditRecord = new CreditRecord();
			creditRecord.setAmount(amount * -1);
			creditRecord.setCategory(creditCategory);
			creditRecord.setType(creditType);
			creditRecord.setStatus(true);
			creditRecord.setClient(client);
			creditRecord.setCashbookDepositeLink(cashRecord);
			creditRecord.setSales(details);
			
			// ledger entry
			ledger = new Ledger();
			ledger.setCreditAmount(amount);
			ledger.setSource(client.getName());
			ledger.setStatus(true);
			if(natureOfTransaction.equalsIgnoreCase("bank")) {
				ledger.setTarget("Bank");
			}
			else {
				ledger.setTarget("Cash");
			}
			ledger.setType(ledgerType);
			ledger.setCashbookLinking(cashRecord);
			ledger.setSalesLink(details);
			ledger.setRemarks(remarks);
			ledger.setCreatedBy(user);
			
			cashRecord.setLedger(ledger);
			cashRecord.setCreditRecord(creditRecord);
			
			session.save(ledger);
			session.save(creditRecord);
			session.save(cashRecord);
		}
		else {
			// if it's not deposite then it's expense and expense can be cash or credit.
			if(type.equalsIgnoreCase("CashExpense")) {
				cashRecord = new CashBookRecord();
				cashRecord.setAmount(amount * -1);
				cashRecord.setCategory(cashbookCategory);
				cashRecord.setType(cashbookType);
				cashRecord.setParty(client);
				cashRecord.setStatus(true);
				cashRecord.setPaymentType(natureOfTransaction);
				cashRecord.setSales(details);
				
				/*// credit book entry -  if it's a cash deposite an entry will be sent to credit records too.
				creditRecord = new CreditRecord();
				creditRecord.setAmount(amount);
				creditRecord.setCategory(category);
				creditRecord.setType(type);
				creditRecord.setStatus(true);
				creditRecord.setClient(client);
				creditRecord.setCashbookDepsiteLink(cashRecord);*/
				
				// ledger entry
				ledger = new Ledger();
				ledger.setDebitAmount(amount);
				ledger.setStatus(true);
				if(natureOfTransaction.equalsIgnoreCase("bank")) {
					ledger.setSource("Bank");
				}
				else {
					ledger.setSource("Cash");
				}
				ledger.setTarget(client.getName());
				ledger.setType(ledgerType);
				ledger.setCashbookLinking(cashRecord);
				ledger.setSalesLink(details);
				ledger.setRemarks(remarks);
				ledger.setCreatedBy(user);
				
				cashRecord.setLedger(ledger);
				
				session.save(ledger);
				session.save(cashRecord);
			}
			else {
				creditRecord = new CreditRecord();
				creditRecord.setAmount(amount);
				creditRecord.setCategory(creditCategory);
				creditRecord.setType(creditType);
				creditRecord.setStatus(true);
				creditRecord.setClient(client);
				creditRecord.setSales(details);
				
				// ledger entry
				ledger = new Ledger();
				ledger.setCreditAmount(amount);
				ledger.setSource(client.getName());
				ledger.setTarget("Expense");
				ledger.setType(ledgerType);
				ledger.setCreditRecordLinking(creditRecord);
				ledger.setSalesLink(details);
				ledger.setRemarks(remarks);
				ledger.setCreatedBy(user);
				ledger.setStatus(true);
				
				creditRecord.setLedgerLinking(ledger);
				session.save(ledger);
				session.save(creditRecord);
				
			}
		}
	}
	
	
	// ---------------------------- End Expense and Deposite related DAO -------------------------
	
	// ---------------------------- Entry Cancellation DAO ---------------------------------------
	
	@Transactional
	public boolean cancleEntry(String type, int salesId) {
		Session session = factory.getCurrentSession();
		boolean status = false;
		String updateCashbook = null;
		String updateCreditBook = null;
		String updateLedger = null;
		
		String updateSales = "UPDATE supplydetails SET status = 0 WHERE id = :id";
		if(type.equals("sales")) {
			updateCashbook = "UPDATE cashbook_table SET status = 0 WHERE sales_id = :id";
			updateCreditBook = "UPDATE credit_table SET status = 0 WHERE sales_id = :id";
			updateLedger = "UPDATE ledger SET status = 0 WHERE sales_id = :id";
		}
		else if(type.equals("cash")) {
			updateCashbook = "UPDATE cashbook_table SET status = 0 WHERE id = :id";
			updateCreditBook = "UPDATE credit_table SET status = 0 WHERE cashbook_link_for_deposite = :id";
			updateLedger = "UPDATE ledger SET status = 0 WHERE cashbook_link = :id";
		}
		else if(type.equals("credit")) {
			updateCreditBook = "UPDATE credit_table SET status = 0 WHERE id = :id";
			updateLedger = "UPDATE ledger SET status = 0 WHERE creditbook_link = :id";
		}
		
		
		Query query = null;
		// cancel sales;
		if(type.equals("sales")) {
			query = session.createNativeQuery(updateSales);
			query.setParameter("id", salesId);
			query.executeUpdate();
		}
		// cancel cashbook entry
		if(type.equals("sales") || type.equals("cash")) {
			query = session.createNativeQuery(updateCashbook);
			query.setParameter("id", salesId);
			query.executeUpdate();
		}
		
		// cancel credit entry
		query = session.createNativeQuery(updateCreditBook);
		query.setParameter("id", salesId);
		query.executeUpdate();
		
		// cancel ledger entry
		query = session.createNativeQuery(updateLedger);
		query.setParameter("id", salesId);
		query.executeUpdate();
		status = true;
		
		return status;	
	}
	
	// ---------------------------------- End Entry Cancellation DAO ------------------------------

	
	// ---------------------------- Misc DAO Methods ---------------------------------------------
	/* pull parameters from the database. In parameter table there will be only single active record. Method
	 * will pull that record which doesn't have an end date.
	 */
	@Transactional
	public List<GeneralData> getLookupMap(String category){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<GeneralData> criteria = builder.createQuery(GeneralData.class);
		Root<GeneralData> from = criteria.from(GeneralData.class);
		criteria.where(builder.and(builder.equal(from.get("category"),category),builder.isNull(from.get("endDate"))));
		
		TypedQuery<GeneralData> query = session.createQuery(criteria);
		List<GeneralData> objectList = query.getResultList();
		return objectList;
	}
	
	@Transactional
	public GeneralData getGeneralDataDescBased(String desc) {
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<GeneralData> criteria = builder.createQuery(GeneralData.class);
		Root<GeneralData> from = criteria.from(GeneralData.class);
		criteria.where(builder.and(builder.equal(from.get("description"),desc),builder.isNull(from.get("endDate"))));
		
		TypedQuery<GeneralData> query = session.createQuery(criteria);
		List<GeneralData> objectList = query.getResultList();
		GeneralData generalData = null;
		if(objectList != null && objectList.size() > 0) {
			generalData = objectList.get(0);
		}
		return generalData;
	}
	
	// ----------------------------------------- End Misc DAO Methods ------------------------------
	
	
	
	
	// ----------------------------------------- Token DAO Methods ---------------------------------
	@Transactional
	public Token getToken() {
		Session session = factory.getCurrentSession();
		TypedQuery<Token> tokenQuery = session.createQuery("from Token", Token.class);
		Token token = null;
		try {
			token = tokenQuery.getSingleResult();
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return token;
	}
	
	@Transactional
	public void updateToken(Token token) {
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(token);
	}
	
	// ------------------------------------- End Token DAO Methods ------------------------------------
	
	
	// ------------------------------ User authnetication DAO ---------------------------------
	@Transactional()
	// get user details for databse user table.
	public User getUser(String userName) {
		Session session = factory.getCurrentSession();
		User user = session.get(User.class, userName);
		return user;
	}
	
	@Transactional()
	public void saveUser(User user) {
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(user);
		session.flush();
	}
	
	@Transactional()
	public void updatePassword(User user) {
		Session session = factory.getCurrentSession();
		session.update(user);
		session.flush();
	}
	// ------------------------------ End User Authentication DAO -----------------------------
	
	// ------------------------------ Parameters DAO ------------------------------------
	@Transactional
	public boolean updateParameters(Parameters param) {
		Session session = factory.getCurrentSession();
		System.out.println(param.getDriverReturn());
		System.out.println(param.getId());
		boolean status = false;
		try {
				session.update(param);
				status = true;
		}
		catch(Exception ex) {}
		return status;
	}
	
	@Transactional
	public Parameters getParameters() {
		Session session = factory.getCurrentSession();
		TypedQuery<Parameters> parameterQuery = session.createQuery("FROM Parameters p WHERE p.endDate is null", Parameters.class);
		Parameters parameter = null;
		try {
			parameter = parameterQuery.getSingleResult();
		}
		catch(Exception ex) {
			System.out.println(ex);
			// write code to check why their are multiple active parameters and disable parameter except latest one.
		}
		System.out.println("Id" +parameter.getId());
		return parameter;
	}
	// ------------------------------ End Parameter DAO --------------------------------
}
