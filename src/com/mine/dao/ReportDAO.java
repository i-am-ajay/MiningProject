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
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mine.component.master.Client;
import com.mine.component.master.GeneralData;
import com.mine.component.master.Rate;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.Ledger;
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
	
	//-------------------------------- End Vehicle Reports DAO --------------------------------------

	
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
	
	//-------------------------------- Rate Report DAO -------------------------------------
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
	
	//-------------------------------- End Rate Report DAO ---------------------------------
	
	
	//-------------------------------- Sales Report ------------------------------------------------
	@Transactional
	public List<Object[]> getSalesList(String vehicleNo,String quantity, String material, String paymentType, String client, LocalDate fromDate, LocalDate toDate){
		boolean initiateSearch = false;
		List<Object[]> salesList = null;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
		Root<SupplyDetails> from = criteria.from(SupplyDetails.class);
		// subquery to get rate
		// create subquery result type.
		Subquery<Double> rateQuery = criteria.subquery(Double.class);
		// create subquery root.
		Root<Rate> rateRoot = rateQuery.from(Rate.class);
		List<Predicate> ratePredicate = new ArrayList<>();
		rateQuery.select(rateRoot.get("rate")).where(
			builder.and(builder.equal(rateRoot.get("materialType"), from.get("material")),
					builder.equal(rateRoot.get("quantity"), from.get("quantity"))),
					builder.isNull(rateRoot.get("truckType")),
					builder.isNull(rateRoot.get("tyreType"))
		);
		
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
		if(paymentType != null && paymentType.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("client"), paymentType));
		}
		if(fromDate != null) {
			initiateSearch = true;
			LocalDateTime fromDateTime = LocalDateTime.of(fromDate, LocalTime.of(0, 0,0));
			predicateList.add(builder.greaterThanOrEqualTo(from.get("salesDate"), fromDateTime));
		}
		if(toDate != null) {
			initiateSearch = true;
			LocalDateTime toDateTime = LocalDateTime.of(toDate, LocalTime.of(23, 59, 59));
			predicateList.add(builder.lessThanOrEqualTo(from.get("salesDate"), toDateTime));
		}
		//predicateList.add(builder.equal(from.get("status"), true));
		if(initiateSearch) {
			criteria.multiselect(from,rateQuery).where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
			TypedQuery<Object[]> salesQuery = session.createQuery(criteria);
			salesList = salesQuery.getResultList();
		}
		return salesList;
	}
	
	@Transactional
	public List<Object[]> salesSummary(int selectionCode){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Client> clientRoot = query.from(Client.class);
		// create SubQuery
		Subquery<Double> subQuery = query.subquery(Double.class);
		Root<Ledger> subQueryRoot = subQuery.from(Ledger.class);
		
		subQuery.select(builder.diff(builder.sum(subQueryRoot.get("creditAmount")),
				builder.sum(subQueryRoot.get("debitAmount")))).where(builder.and(builder.or(builder.equal(subQueryRoot.get("target"), clientRoot.get("name")),
						builder.equal(subQueryRoot.get("source"), clientRoot.get("name")))),builder.equal(subQueryRoot.get("status"), 1));
		
		// Get predicate for general data.
		Subquery<GeneralData> generalSubQuery = query.subquery(GeneralData.class);
		Root<GeneralData> generalDataRoot = generalSubQuery.from(GeneralData.class);
		// client predicate
		if (selectionCode == 1){
			generalSubQuery.select(generalDataRoot).where(builder.or(builder.equal(generalDataRoot.get("description"),"Owner"),
					builder.equal(generalDataRoot.get("description"),"Contractor")));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery));
		}
		else if(selectionCode == 2) {
			generalSubQuery.select(generalDataRoot).where(builder.equal(generalDataRoot.get("description"),"Sanchalan"));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery));
		}
		else {
			generalSubQuery.select(generalDataRoot).where(builder.or(builder.equal(generalDataRoot.get("description"),"Owner"),
					builder.equal(generalDataRoot.get("description"),"Contractor"),
					builder.equal(generalDataRoot.get("description"),"Sanchalan")));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery).not());
		}
		
		TypedQuery<Object[]> summaryObj = session.createQuery(query);
		return summaryObj.getResultList();
	}
	
	//-------------------------------- End Sales Report --------------------------------------------
	
	
	// ------------------------------ Ledger DAO -------------------------------------------------
	@Transactional
	public List<Ledger> ledgerEntries(String name, LocalDateTime startDate, LocalDateTime endDate){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Ledger> query = builder.createQuery(Ledger.class);
		Root<Ledger> from = query.from(Ledger.class);
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(builder.equal(from.get("source"), name));
		predicateList.add(builder.equal(from.get("target"), name));
		predicateList.add(builder.between(from.get("entryDate"), startDate, endDate));
		predicateList.add(builder.equal(from.get("status"),true));
		//query.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()]))).orderBy(builder.desc(from.get("entryDate")));
		query.where(builder.and(builder.or(builder.equal(from.get("source"), name),builder.equal(from.get("target"), name)),builder.between(from.get("entryDate"), startDate, endDate),builder.equal(from.get("status"), true)));
		TypedQuery<Ledger> ledgerQuery = session.createQuery(query);
		return ledgerQuery.getResultList();
	}
	
	@Transactional
	public Double[] getBalances(String name, LocalDateTime startDate, LocalDateTime endDate) {
		Session session = factory.getCurrentSession();
		String openingBalanceQuery = "SELECT SUM(l.creditAmount)-SUM(l.debitAmount) FROM Ledger l WHERE "
				+ "(l.target = :name OR l.source= :name) AND l.entryDate < :sDate and status = 1";
		
		String rangeBalanceQuery = "SELECT SUM(l.creditAmount)-SUM(l.debitAmount) FROM Ledger l WHERE "
				+ "(l.target = :name OR l.source= :name) AND (l.entryDate BETWEEN :sDate AND :eDate) AND status = 1";
		TypedQuery<Double> creditDebitAmount = session.createQuery(openingBalanceQuery,Double.class);
		creditDebitAmount.setParameter("name", name);
		creditDebitAmount.setParameter("sDate", startDate);
		Double openingBalance = null;
		try {
			openingBalance = creditDebitAmount.getSingleResult();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// Get balance of selected records
		creditDebitAmount = session.createQuery(rangeBalanceQuery,Double.class);
		creditDebitAmount.setParameter("name", name);
		creditDebitAmount.setParameter("sDate", startDate);
		creditDebitAmount.setParameter("eDate", endDate);
		Double selectedRecordsBalance = null;
		try {
			selectedRecordsBalance = creditDebitAmount.getSingleResult();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return new Double[]{openingBalance != null ? openingBalance : 0.0, selectedRecordsBalance != null ? selectedRecordsBalance : 0.0};
	}
	
	
	// ------------------------------ End Ledger DAO ---------------------------------------------
	
	// ------------------------------ Utility DAO ----------------------------------------
	@Transactional
	public void getTypeId() {
		Session session = factory.getCurrentSession();
		List<Long> idList = session.createQuery("SELECT id FROM GeneralData WHERE description in ('Owner','Contractor','Sanchalan')",Long.class).getResultList();
	}
	
	
	// ------------------------------ End Utility DAO ----------------------------------------
}
