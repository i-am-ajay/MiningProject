package com.mine.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mine.component.master.Client;
import com.mine.component.master.GeneralData;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;

@Repository
public class ReportDAO {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	MineDAO mineDAO;
	
	//-------------------------------- Vehicle Reports DAO --------------------------------------
	@Transactional
	public List<Vehicle> getVehicleList(String vehicleNo, String vehicleType, int tyreType, String clientName, int clientType){
		boolean initiateSearch = false;
		List<Vehicle> vehicleList = null;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Vehicle> criteria = builder.createQuery(Vehicle.class);
		Root<Vehicle> from = criteria.from(Vehicle.class);
		List<Predicate> predicateList = new ArrayList<>();
		if(vehicleNo != null && vehicleNo.length() > 0) {
			System.out.println("VehicleNo"+ vehicleNo);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("vehicleNo"),vehicleNo));
		}
		if(vehicleType != null && vehicleType.length() > 0) {
			System.out.println("VehicleType"+ vehicleType);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("vehicleType"),vehicleType));
		}
		if(tyreType != 0) {
			System.out.println("tyreType"+ tyreType);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("tyreType"),tyreType));
		}
		if(clientName != null && clientName.length() > 0) {
			System.out.println("client"+ clientName.length());
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientId").get("name"), clientName));
		}
		if(clientType != 0) {
			System.out.println("clientType"+ clientType);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientId").get("clientType").get("id"), clientType));
		}
		if(initiateSearch) {
			criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
			TypedQuery<Vehicle> vehicleQuery = session.createQuery(criteria);
			vehicleList = vehicleQuery.getResultList();
		}
		if(vehicleList != null) {
			vehicleList.forEach(e ->{
				System.out.println(e.getVehicleNo());
			});
		}
		return vehicleList;
	}
	
	//-------------------------------- Vehicle Reports DAO --------------------------------------

	
	//-------------------------------- Client Reports DAO --------------------------------------
	@Transactional
	public List<Client> getClientList(String name, String clientContact, int clientType){
		boolean initiateSearch = false;
		List<Client> clientList = null;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Client> criteria = builder.createQuery(Client.class);
		Root<Client> from = criteria.from(Client.class);
		List<Predicate> predicateList = new ArrayList<>();
		if(name != null && name.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.like(from.get("name"),"%"+name+"%"));
		}
		if(clientContact != null && clientContact.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientContact"),clientContact));
		}
		if(clientType != 0) {
			System.out.println("clientType"+ clientType);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientType").get("id"), clientType));
		}
		if(initiateSearch) {
			criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
			TypedQuery<Client> clientQuery = session.createQuery(criteria);
			clientList = clientQuery.getResultList();
		}
		
		return clientList;
	}
	
	//-------------------------------- End Client Reports DAO --------------------------------------
	
	//-------------------------------- Vehicle Rate Report DAO -------------------------------------
	@Transactional
	public List<Rate> getRateList(String vehicleType, int tyreType, String quantity, String material){
		List<Rate> rateList = null;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rate> criteria = builder.createQuery(Rate.class);
		Root<Rate> from = criteria.from(Rate.class);
		List<Predicate> predicateList = new ArrayList<>();
		if(vehicleType != null && vehicleType.length() > 0) {
			predicateList.add(builder.equal(from.get("truckType"),vehicleType));
		}
		if(tyreType != 0) {
			predicateList.add(builder.equal(from.get("tyreType"),tyreType));
		}
		if(quantity != null && quantity.length() > 0) {
			predicateList.add(builder.equal(from.get("quantity"), quantity));
		}
		if(material != null && material.length() > 0) {
			predicateList.add(builder.equal(from.get("materialType"), material));
		}
		criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
		TypedQuery<Rate> rateQuery = session.createQuery(criteria);
		rateList = rateQuery.getResultList();
		return rateList;
	}
	
	//-------------------------------- End Vehicle Rate Report DAO ---------------------------------
	
	
	//-------------------------------- Sales Report ------------------------------------------------
	@Transactional
	public List<SupplyDetails> getSalesList(String vehicleNo,String quantity, String material, String paymentType, LocalDate fromDate, LocalDate toDate){
		boolean initiateSearch = false;
		List<SupplyDetails> salesList = null;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SupplyDetails> criteria = builder.createQuery(SupplyDetails.class);
		Root<SupplyDetails> from = criteria.from(SupplyDetails.class);
		List<Predicate> predicateList = new ArrayList<>();
		if(vehicleNo != null && vehicleNo.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("vehicle").get("vehicleNo"),vehicleNo));
		}
		if(quantity != null && quantity.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("quantity"), quantity));
		}
		if(material != null && material.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("material"), material));
		}
		if(paymentType != null && paymentType.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("paymentType"), paymentType));
		}
		if(fromDate != null) {
			initiateSearch = true;
			LocalDateTime fromDateTime = LocalDateTime.of(fromDate, LocalTime.of(0, 0));
			predicateList.add(builder.greaterThanOrEqualTo(from.get("salesDate"), fromDateTime));
		}
		if(toDate != null) {
			initiateSearch = true;
			LocalDateTime toDateTime = LocalDateTime.of(toDate, LocalTime.of(11, 59));
			predicateList.add(builder.lessThanOrEqualTo(from.get("salesDate"), toDateTime));
		}
		if(initiateSearch) {
			criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
			TypedQuery<SupplyDetails> salesQuery = session.createQuery(criteria);
			salesList = salesQuery.getResultList();
		}
		return salesList;
	}
	//-------------------------------- End Sales Report --------------------------------------------
}
