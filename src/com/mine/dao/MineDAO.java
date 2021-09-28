package com.mine.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


import javax.persistence.Query;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
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
		vehicle.setCreationTime(LocalDate.now());
		if(exists){
			session.merge(vehicle);
		}
		else {
			session.saveOrUpdate(vehicle);
		}
		if(!status.equalsIgnoreCase("updated"))
			status = "success";
		System.out.println("Creation Time :"+vehicle.getCreationTime());
		session.flush();
		return status;
	}
	
	
	/**
	 * This method will get a vehcile from database based on vehicle id
	 * @param vehicleNo
	 * @return Vehicle
	 */
	@Transactional
	public Vehicle getVehicle(String vehicleNo) {
		Vehicle vehicleReturn = null;
		Session session = factory.getCurrentSession();
		Vehicle vehicle = session.get(Vehicle.class, vehicleNo);
		if(vehicle !=null) {
			vehicleReturn = vehicle;
		}
		return vehicleReturn;
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
			clientDb.setStatus(client.isStatus());
			client = clientDb;
			status = "updated";
		}
		GeneralData data = session.get(GeneralData.class, lookupId);
		Company company = session.get(Company.class, companyId);
		client.setClientType(data);
		client.setCompany(company);
		client.setCreatedBy(createdBy);
		//client.setCreationDate(LocalDate.now());
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
	public Client getClient(int clientId) {
		Session session = factory.getCurrentSession();
		return session.get(Client.class, clientId);
	}
	
	@Transactional
	public List<Client> getClietList(int companyId, int clientTypeId){
		return getClientList(companyId, clientTypeId, true);
	}
	
	@Transactional
	public List<Client> getClientList(int companyId, int clientTypeId, boolean allClients) {
		Session session = factory.getCurrentSession();
		Company company = session.get(Company.class, companyId);
		GeneralData clientType = session.get(GeneralData.class, clientTypeId);
		CriteriaBuilder builder = session.getCriteriaBuilder();
		Subquery<GeneralData> generalDataSubQuery = null;
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> from = criteria.from(Client.class);
		
		predicateList.add(builder.equal(from.get("company"),company));
		predicateList.add(builder.isNull(from.get("endDate")));
		if(clientType != null) {
			predicateList.add(builder.equal(from.get("clientType"), clientType));
			Predicate[] predicateArray = predicateList.toArray(new Predicate[predicateList.size()]);
			criteria.where(predicateArray);
		}
		else if(clientTypeId == 46) {
			criteria.where(builder.equal(from.get("clientType"), clientTypeId));
		}
		else if(allClients == false) {
			generalDataSubQuery = criteria.subquery(GeneralData.class);
			Root<GeneralData> generalDataRoot = generalDataSubQuery.from(GeneralData.class);
			generalDataSubQuery.select(generalDataRoot.get("id")).where(builder.or(
					builder.equal(generalDataRoot.get("description"), "Self"),
					builder.equal(generalDataRoot.get("description"), "Owner"),
					builder.equal(generalDataRoot.get("description"), "Contractor")));
			criteria.where(from.get("clientType").in(generalDataSubQuery));
		}
		criteria.orderBy(builder.asc(from.get("name")));
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
			return "exists";
		}
		String rateSaved = "fails";
		Session session = factory.getCurrentSession();
		Company company = session.get(Company.class, companyId);
		rate.setCompany(company);
		rate.setCreatedById(user);
		rate.setCreatedDate(LocalDate.now());
		session.save(rate);
		rateSaved = "success";
		return rateSaved;
	}
	
	@Transactional
	public double  getRate(String tyreType, String truckType,String materialType, String quantity, int companyId) {
		double rate = 0.0;
		Session session = factory.getCurrentSession();
		Company company = session.load(Company.class, companyId);
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rate> criteria = builder.createQuery(Rate.class);
		Root<Rate> from = criteria.from(Rate.class);
		List<Predicate> predicateList = new ArrayList<>();
		if(!quantity.equalsIgnoreCase("bucket") && !quantity.equalsIgnoreCase("ton") && !quantity.equalsIgnoreCase("foot")) {
			predicateList.add(builder.equal(from.get("tyreType"), tyreType));
			predicateList.add(builder.equal(from.get("truckType"), truckType));
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
		dbRate.setCreatedDate(LocalDate.now());
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
	/**
	 * For each transaction ledger will have two entries. 
	 * Services or Money that's being given is recorded in credit.
	 * Services or Money recieved in recorded in debit.
	 * 
	 * If we are giving service then that will have entry in credit and there will be a corresponding
	 * entry for party paying for the service and it will be in debit head. both service and payment will 
	 * have equal amount entry so entries will always be balanced. 
	 * 
	 * Similarly if we are making a payment it will be recorded in credit as money going out
	 * and there will be a corresponding entry of either service or good we have recieved in 
	 * debit head.
	 * @param details
	 * @param user
	 */
	@Transactional
	public void addSalesToCashAndLedger(SupplyDetails details, User user){
		Session session = factory.getCurrentSession();
		// save sales record in Cash or credit table.
		/*TypesAndCategories cashSale = DefineTypesAndCategories.cashSale; 
		TypesAndCategories creditSale = DefineTypesAndCategories.creditSale;
		TypesAndCategories bankSale = DefineTypesAndCategories.bankSale;*/
		LocalDateTime currentDateTime = LocalDateTime.now();
		// get vehicle and set it's linking.
		Vehicle vehicle = session.get(Vehicle.class, details.getVehicle().getVehicleNo());
		details.setVehicle(vehicle);
		details.setUser(user);
		details.setStatus(true);
		// For Cash or Bank payment modes
		
		
		// Ledger of sale
		Ledger ledgerCredit = new Ledger();
		ledgerCredit.setCreditAmount(details.getFinalRate());
		ledgerCredit.setCreatedBy(user);
		ledgerCredit.setStatus(true);
		ledgerCredit.setEntryDate(currentDateTime);
		ledgerCredit.setSalesLink(details);
		ledgerCredit.setAccount("Sales");
		
		// Ledger record for payment
		Ledger ledgerDebit = new Ledger();
		ledgerDebit.setDebitAmount(details.getFinalRate());
		ledgerDebit.setCreatedBy(user);
		ledgerDebit.setStatus(true);
		ledgerDebit.setEntryDate(currentDateTime);
		ledgerDebit.setSalesLink(details);
		
				
		if(details.getPaymentType().equalsIgnoreCase("cash") || details.getPaymentType().equalsIgnoreCase("bank")){
			if(details.getPaymentType().equalsIgnoreCase("cash")) {
				// ledger record for cash
				ledgerDebit.setAccount("Cash");
				ledgerDebit.setDescription("Cash To Sales");
				ledgerCredit.setDescription("Sales In Cash");
			}
			else {
				ledgerDebit.setAccount("Bank");
				ledgerDebit.setDescription("Bank To Sales");
				ledgerCredit.setDescription("Sales in Bank");
			}
		}
		else {
			// ledger record
			ledgerDebit.setAccount(details.getClientName());
			ledgerDebit.setDescription("Credit Sale");
			ledgerCredit.setDescription(details.getClientName().concat(" To Sales"));
		}
		//session.save(details);
		session.save(ledgerCredit);
		session.save(ledgerDebit);
		session.save(details);
		session.setHibernateFlushMode(FlushMode.COMMIT);
	}
	
	@Transactional
	public Long getSaleCount() {
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		Root<SupplyDetails> rootSupply = cQuery.from(SupplyDetails.class);
		LocalDateTime fromDate = LocalDateTime.of(LocalDate.now(),LocalTime.of(0, 0,0));
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
		cQuery.multiselect(builder.count(rootSupply.get("id"))).where(builder.and(builder.between(rootSupply.get("salesDate"), fromDate, endDate),builder.equal(rootSupply.get("status"), true)));
		// Execute Query
		TypedQuery<Long> query = session.createQuery(cQuery);
		Long count = 0L;
		try {
			count = query.getSingleResult();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
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
	
	/** combined sale data.
	 * 1) Categorize Owner-Contractor/Sanchalan/Other Office Expense
	 * 2) Write a query on Party to fetch all parties from given category.
	 * 3) For each party get a sum of total amount from ledger.
	 *  */
	

	// ---------------------------- End Sales DAO Methods ----------------------------------------
	
	
	// ---------------------------- Expense and Deposite related DAO -----------------------------
	//natureOfTransaction - Cash/Bank/Credit
	// SupplyDetails - If entry is made through Sale for contractor, sanchalan and driver return. Then pass sales details too.
	@Transactional
	public void addDepositeOrExpense(int clientId, double amount, String type, String creditDescription, String debitDescription, String natureOfTransaction, SupplyDetails details, String remarks, User user, LocalDateTime date) {
		Session session = factory.getCurrentSession();
		Client client = session.get(Client.class, clientId);
		
		//Ledger of deposite
		Ledger ledgerCredit = new Ledger();
		ledgerCredit.setCreditAmount(amount);
		ledgerCredit.setCreatedBy(user);
		ledgerCredit.setStatus(true);
		ledgerCredit.setEntryDate(date);
		ledgerCredit.setSalesLink(details);
		ledgerCredit.setDescription(creditDescription);
		ledgerCredit.setRemarks(remarks);
		
		//Ledger record for expense
		Ledger ledgerDebit = new Ledger();
		ledgerDebit.setDebitAmount(amount);
		ledgerDebit.setCreatedBy(user);
		ledgerDebit.setStatus(true);
		ledgerDebit.setEntryDate(date);
		ledgerDebit.setDescription(debitDescription);
		ledgerDebit.setRemarks(remarks);
		ledgerDebit.setSalesLink(details);
		
		
		if(type.equals("deposite")) {
			ledgerCredit.setParentEntryLink(ledgerDebit);
			ledgerDebit.setParentEntryLink(ledgerCredit);
			/* debit and credit entry of ledger i.e amount recieved is debited
			 * either in cash or bank and party account is credited with equivalent amount. 
			*/
			ledgerCredit.setAccount(client.getName());
			
			if(natureOfTransaction.equalsIgnoreCase("bank")) {
				ledgerDebit.setAccount("Bank");
				//ledgerCredit.setDescription("Bank /Payment From ".concat(client.getName()));
				ledgerDebit.setDescription("Bank To ".concat(client.getName()));
				ledgerCredit.setDescription(client.getName().concat("Bank Payment"));
			}
			else {
				ledgerDebit.setAccount("Cash");
				ledgerDebit.setDescription("Cash To ".concat(client.getName()));
				ledgerCredit.setDescription("Cash Payment");
			}
		}
		else {
			// if it's not deposite then it's expense and expense can be cash or credit.
			if(type.equalsIgnoreCase("CashExpense")) {
				ledgerDebit.setAccount(client.getName());
				
				if(natureOfTransaction.equalsIgnoreCase("bank")) {
					ledgerCredit.setAccount("Bank");
					ledgerDebit.setDescription("Payment From Bank");
					ledgerCredit.setDescription(client.getName().concat(" To Bank"));
				}
				else {
					ledgerCredit.setAccount("Cash");
					ledgerCredit.setDescription(client.getName().concat(" To Cash"));
					ledgerDebit.setDescription("Payment In Cash");
					//"Payment In Cash ".concat(client.getName())
					
				}
				ledgerDebit.setParentEntryLink(ledgerCredit);
				ledgerCredit.setParentEntryLink(ledgerDebit);
			}
			else {
				ledgerDebit.setAccount("Expense");
				ledgerCredit.setAccount(client.getName());
				ledgerDebit.setDescription(debitDescription.concat(" Amount Payble To".concat(client.getName())));
				ledgerCredit.setDescription(creditDescription.concat(" Amount To be Recieved"));
				ledgerCredit.setParentEntryLink(ledgerDebit);
				ledgerDebit.setParentEntryLink(ledgerCredit);
			}
		}
		session.save(ledgerCredit);
		session.save(ledgerDebit);
	}
	
	@Transactional
	public void journalEntry(String debtor, String creditor, double amount, String remarks, LocalDateTime dateTime, User user) {
		
		// Ledger of creditor
		Ledger ledgerCredit = new Ledger();
		ledgerCredit.setCreditAmount(amount);
		ledgerCredit.setCreatedBy(user);
		ledgerCredit.setStatus(true);
		ledgerCredit.setEntryDate(dateTime);
		ledgerCredit.setAccount(creditor);
		ledgerCredit.setDescription("Journal Entry: "+ debtor);
		ledgerCredit.setRemarks(remarks);
		
		// Ledger record for payment
		Ledger ledgerDebit = new Ledger();
		ledgerDebit.setDebitAmount(amount);
		ledgerDebit.setCreatedBy(user);
		ledgerDebit.setStatus(true);
		ledgerDebit.setEntryDate(dateTime);
		ledgerDebit.setDescription("Journal Entry: "+debtor+" To "+creditor);
		ledgerDebit.setRemarks(remarks);
		ledgerDebit.setAccount(debtor);
		
		ledgerDebit.setParentEntryLink(ledgerCredit);
		ledgerCredit.setParentEntryLink(ledgerDebit);
		Session session = factory.getCurrentSession();
		session.save(ledgerDebit);
		session.save(ledgerCredit);
		session.flush();
	}
	
	// ---------------------------- End Expense and Deposite related DAO -------------------------
	
	// ---------------------------- Entry Cancellation DAO ---------------------------------------
	
	@Transactional
	public boolean cancleEntry(String type, int salesId) {
		Session session = factory.getCurrentSession();
		boolean status = false;
		
		String updateLedger = null;
		
		String updateSales = "UPDATE supplydetails SET status = 0 WHERE id = :id";
		if(type.equals("sales")) {
			updateLedger = "UPDATE ledger SET status = 0 WHERE sales_id = :id";
		}
		else{
			updateLedger = "UPDATE ledger SET status = 0 WHERE id = :id OR parent_link = :id";
		}
		
		Query query = null;
		// cancel sales;
		if(type.equals("sales")) {
			query = session.createNativeQuery(updateSales);
			query.setParameter("id", salesId);
			query.executeUpdate();
		}
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
		criteria.orderBy(builder.asc(from.get("description")));
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
		boolean status = false;
		param.setCreationDate(LocalDate.now());
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
		//System.out.println("Id" +parameter.getId());
		return parameter;
	}
	// ------------------------------ End Parameter DAO --------------------------------
	
	@Transactional
	public void saveLedger() {
		Ledger ledger = new Ledger();
		ledger.setDebitAmount(100);
		ledger.setStatus(true);
		Session session = factory.getCurrentSession();
		session.save(ledger);
	}
}
