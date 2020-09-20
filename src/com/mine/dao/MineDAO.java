package com.mine.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;

@Repository
public class MineDAO {
	@Autowired
	SessionFactory factory;
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
	public List<SupplyDetails> getSaleData(int numRecords, LocalDateTime startDate, LocalDateTime endDate){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SupplyDetails> query = builder.createQuery(SupplyDetails.class);
		Root<SupplyDetails> from = query.from(SupplyDetails.class);
		query.where(builder.between(from.get("salesDate"), startDate, endDate)).orderBy(builder.desc(from.get("salesDate")));
		
		TypedQuery<SupplyDetails> details = session.createQuery(query);
		details.setMaxResults(10);
		return details.getResultList();
	}
	
	/**
	 * To save details in to the database.
	 * @param details
	 */
	@Transactional
	public void saveSupplyDetails(SupplyDetails details) {
		Session session = factory.getCurrentSession();
		session.save(details);
		session.flush();
	}
	
	@Transactional
	public void saveInCashBook(Client client, double amount) {
		Session session = factory.getCurrentSession();
		//session.save();
		session.flush();
	}
	
	public void saveInSalesBook(Client client, double amount) {
		Session session = factory.getCurrentSession();
		//session.disableFetchProfile(name);;
		session.flush();
	}
	
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
	public void saveClient(Client client, int lookupId) {
		Session session = factory.getCurrentSession();
		GeneralData data = session.get(GeneralData.class, lookupId);
		Company company = session.get(Company.class, 1);
		client.setClientType(data);
		client.setCompany(company);
		client.setCreatedBy(1);
		session.save(client);
		System.out.println("client data saved");
	}
	
	@Transactional
	public void saveCompany(Company company) {
		Session session = factory.getCurrentSession();
		company.setCreatedBy(1);
		session.save(company);
	}
	
	@Transactional
	public void saveVehicle(Vehicle vehicle, int clientId, int companyId) {
		Session session = factory.getCurrentSession();
		
		Client client = session.load(Client.class, clientId);
		Company company = session.load(Company.class, companyId);
		
		vehicle.setClientId(client);
		vehicle.setCompanyId(company);
		vehicle.setCreatedById(1);
		
		session.save(vehicle);
	}
	
	@Transactional
	public List<Client> getClientList(Company company, int clientTypeId) {
		Session session = factory.getCurrentSession();
		company = session.get(Company.class, 1);
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
	
	@Transactional
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
		
	}
	
	@Transactional
	public boolean addRate(Rate rate, int companyId) {
		boolean rateSaved = false;
		if(rate.getRate() != getRate(rate.getTyreType(), rate.getMaterialType(), rate.getTruckType(),rate.getQuantity(), companyId)) {
			Session session = factory.getCurrentSession();
			Company company = session.get(Company.class, companyId);
			rate.setCompany(company);
			rate.setCreatedById(1);
			session.save(rate);
			rateSaved = true;
		}
		else {
			rateSaved = false;
		}
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
		criteria.where(builder.and(
				builder.equal(from.get("tyreType"), tyreType),
				builder.equal(from.get("materialType"), materialType),
				builder.equal(from.get("truckType"), truckType),
				builder.equal(from.get("quantity"), quantity),
				builder.isNull(from.get("endDate")),
				builder.equal(from.get("company"), company)
		));
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
	
	// DAO for saving sale details in db.
	@Transactional
	public void addSales(SupplyDetails details) {
		Session session = factory.getCurrentSession();
		Vehicle vehicle = session.get(Vehicle.class, details.getVehicle().getVehicleNo());
		details.setVehicle(vehicle);
		session.save(details);
	}
	
	/* pull parameters from the database. In parameter table there will be only single active record. Method
	 * will pull that record which doesn't have an end date.
	 */
	@Transactional
	public Parameters getParameters() {
		Session session = factory.getCurrentSession();
		TypedQuery<Parameters> parameterQuery = session.createQuery("FROM Parameters p WHERE p.endDate is null", Parameters.class);
		Parameters parameter = null;
		try {
			parameter = parameterQuery.getSingleResult();
			System.out.println(parameter.getRoyalty());
		}
		catch(Exception ex) {
			System.out.println(ex);
			// write code to check why their are multiple active parameters and disable parameter except latest one.
		}
		return parameter;
	}
	
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
		session.update(token);
	}
	
	
}
