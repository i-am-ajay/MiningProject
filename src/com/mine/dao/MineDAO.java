package com.mine.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mine.component.master.Client;
import com.mine.component.master.Company;
import com.mine.component.master.GeneralData;
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
	public Vehicle getVehicle(String vehicleNo) {
		return null;
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
		client.setClientType(data);
		session.save(data);
	}
	
	@Transactional
	public void saveCompany(Company company) {
		Session session = factory.getCurrentSession();
		session.save(company);
	}
	
	public void saveVehicle(Vehicle vehicle, int clientId, int companyId) {
		Session session = factory.getCurrentSession();
		
		Client client = session.get(Client.class, clientId);
		
		vehicle.setClientId(client);
		
		session.save(vehicle);
	}
	
	public void getClientList(Company company) {
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> from = criteria.from(Client.class);
		criteria.where(builder.and(builder.equal(from.get("company"),company),builder.isNull(from.get("endDate"))));
	}
}
