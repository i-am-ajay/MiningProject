package com.mine.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.Double;
import java.lang.Long;
import java.sql.Date;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
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
			//System.out.println("VehicleNo"+ vehicleNo);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("vehicleNo"),vehicleNo));
		}
		if(vehicleType != null && vehicleType.length() > 0) {
			//System.out.println("VehicleType"+ vehicleType);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("vehicleType"),vehicleType));
		}
		if(tyreType != 0) {
			//System.out.println("tyreType"+ tyreType);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("tyreType"),tyreType));
		}
		if(clientName != null && clientName.length() > 0) {
			//System.out.println("client"+ clientName.length());
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientId").get("name"), clientName));
		}
		if(clientType != 0) {
			//System.out.println("clientType"+ clientType);
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientId").get("clientType").get("id"), clientType));
		}
		if(initiateSearch) {
			criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
			TypedQuery<Vehicle> vehicleQuery = session.createQuery(criteria);
			vehicleList = vehicleQuery.getResultList();
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
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientType").get("id"), clientType));
		}
		if(initiateSearch) {
			criteria.where(builder.and(predicateList.toArray(new Predicate[predicateList.size()])));
			criteria.orderBy(builder.desc(from.get("name")));
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
		if(client != null && client.length() > 0) {
			initiateSearch = true;
			predicateList.add(builder.equal(from.get("clientName"), client));
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
	public List<Object[]> salesSummary(int selectionCode,LocalDateTime sDate, LocalDateTime eDate){
		Session session = factory.getCurrentSession();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Client> clientRoot = query.from(Client.class);
		// create SubQuery
		
		Subquery<Double> subQuery = query.subquery(Double.class); 
		Root<Ledger>subQueryRoot = subQuery.from(Ledger.class);
		
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(builder.equal(subQueryRoot.get("account"), clientRoot.get("name")));
		predicateList.add(builder.equal(subQueryRoot.get("status"), 1));
		if(sDate != null && eDate != null) {
			predicateList.add(builder.between(subQueryRoot.get("entryDate"), sDate, eDate));
		}
		Predicate [] predArray = predicateList.toArray(new Predicate[predicateList.size()]);
		
		subQuery.select(builder.diff(builder.sum(subQueryRoot.get("creditAmount")),
		builder.sum(subQueryRoot.get("debitAmount")))).where(builder.and(predArray));
		 
		/*Subquery<Double> subQueryDebit = query.subquery(Double.class); 
		Subquery<Double> subQueryCredit = query.subquery(Double.class);
		Root<Ledger> subQueryRootDebit = subQueryDebit.from(Ledger.class);
		Root<Ledger> subQueryRootCredit = subQueryCredit.from(Ledger.class);*/
		
		// create seperate subqueryf or credita and debit amount.
		// Get predicate for general data.
		Subquery<GeneralData> generalSubQuery = query.subquery(GeneralData.class);
		Root<GeneralData> generalDataRoot = generalSubQuery.from(GeneralData.class);
		// client predicate
		if (selectionCode == 1){
			generalSubQuery.select(generalDataRoot).where(builder.equal(generalDataRoot.get("description"),"Owner"));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery));
		}
		else if(selectionCode == 2){
			generalSubQuery.select(generalDataRoot).where(builder.equal(generalDataRoot.get("description"), "Contractor"));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery));
		}
		else if(selectionCode == 3) {
			generalSubQuery.select(generalDataRoot).where(builder.equal(generalDataRoot.get("description"),"Sanchalan"));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery));
		}
		else if(selectionCode == 4) {
			generalSubQuery.select(generalDataRoot).where(builder.equal(generalDataRoot.get("description"), "Office"));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery));
		}
		else if(selectionCode == 5) {
			/*generalSubQuery.select(generalDataRoot).where(builder.equal(generalDataRoot.get("description"),"Journal"));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery));*/
		}
		else {
			generalSubQuery.select(generalDataRoot).where(builder.or(builder.equal(generalDataRoot.get("description"),"Owner"),
					builder.equal(generalDataRoot.get("description"),"Contractor"),
					builder.equal(generalDataRoot.get("description"),"Sanchalan"),
					builder.equal(generalDataRoot.get("description"), "Office"),
					builder.equal(generalDataRoot.get("description"),"Journal")));
			query.multiselect(clientRoot.get("name"),subQuery).where(clientRoot.get("clientType").in(generalSubQuery).not());
		}
		query.orderBy(builder.desc(clientRoot.get("name")));
		
		TypedQuery<Object[]> summaryObj = session.createQuery(query);
		return summaryObj.getResultList();
	}
	
	
	/**
	 * Return daywise summary of sales for a given time period.
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	
	@Transactional
	public List<Object[]> getSummaryOfSale(LocalDate startDate, LocalDate endDate) {
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<SupplyDetails> from = query.from(SupplyDetails.class);
		
		// subquery for bank details
		Subquery<Double> bankSubquery = query.subquery(Double.class);
		Root<SupplyDetails> subQueryFrom = bankSubquery.from(SupplyDetails.class);
		bankSubquery.select(builder.sum(subQueryFrom.get("finalRate"))).where(
			builder.and(
				builder.equal(subQueryFrom.get("paymentType"), builder.literal("bank")),
				builder.equal(subQueryFrom.get("status"), 1),
				builder.equal(
					builder.function("DATE",Date.class,subQueryFrom.get("salesDate")),
					builder.function("DATE",Date.class, from.get("salesDate")
					)
				)
			)
		);
		
		// subquery for credit details;
		Subquery<Double> creditSubquery = query.subquery(Double.class);
		Root<SupplyDetails> creditSubQueryFrom = creditSubquery.from(SupplyDetails.class);
		creditSubquery.select(builder.sum(creditSubQueryFrom.get("finalRate"))).where(
			builder.and(
				builder.equal(creditSubQueryFrom.get("paymentType"), builder.literal("credit")),
				builder.equal(creditSubQueryFrom.get("status"), 1),
				builder.equal(
					builder.function("DATE",Date.class,creditSubQueryFrom.get("salesDate")),
					builder.function("DATE",Date.class, from.get("salesDate")
					)
				)
			)
		);
		
		// subquery to get number of trucks
		Subquery<Long> truckCounts = query.subquery(Long.class);
		Root<SupplyDetails> truckCountSubQueryFrom = truckCounts.from(SupplyDetails.class);
		truckCounts.select(builder.count(truckCountSubQueryFrom.get("vehicle"))).where(
			builder.and(
				builder.notEqual(truckCountSubQueryFrom.get("vehicle").get("vehicleType"), "Tralla"),
				builder.notEqual(truckCountSubQueryFrom.get("vehicle").get("vehicleType"), "Trolly"),
				builder.equal(truckCountSubQueryFrom.get("status"), 1),
				builder.equal(
					builder.function("DATE",Date.class,truckCountSubQueryFrom.get("salesDate")),
					builder.function("DATE",Date.class, from.get("salesDate")
					)
				)
			)
		);
		// subquery to get number of trallies
		Subquery<Long> trallCounts = query.subquery(Long.class);
		Root<SupplyDetails> trallCountSubQueryFrom = trallCounts.from(SupplyDetails.class);
		trallCounts.select(builder.count(trallCountSubQueryFrom.get("vehicle"))).where(
			builder.and(
				builder.or(
						builder.equal(trallCountSubQueryFrom.get("vehicle").get("vehicleType"), "Tralla"),
						builder.equal(trallCountSubQueryFrom.get("vehicle").get("vehicleType"), "Trolly")
				),
				builder.equal(trallCountSubQueryFrom.get("status"), 1),
				builder.equal(
					builder.function("DATE",Date.class,trallCountSubQueryFrom.get("salesDate")),
					builder.function("DATE",Date.class, from.get("salesDate")
					)
				)
			)
		);
		
		query.multiselect(builder.function("DATE", Date.class, from.get("salesDate")),truckCounts,trallCounts,builder.sum(from.get("finalRate")),creditSubquery,bankSubquery)
				.where(
					builder.and(
							builder.equal(from.get("paymentType"), builder.literal("cash")),
							builder.equal(from.get("status"), 1),
							builder.between(builder.function("DATE", Date.class, from.get("salesDate")), 
							Date.valueOf(startDate), Date.valueOf(endDate))
					)	
				).groupBy(builder.function("DATE", Date.class, from.get("salesDate")));
		
		TypedQuery<Object[]> objQuery = session.createQuery(query);
		List<Object[]> objList = objQuery.getResultList();
		return objList;
	}
	
	//-------------------------------- End Sales Report --------------------------------------------
	
	
	// ------------------------------ Ledger DAO -------------------------------------------------
	@Transactional
	public List<Ledger> ledgerEntries(String name, LocalDateTime startDate, LocalDateTime endDate){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Ledger> query = builder.createQuery(Ledger.class);
		Root<Ledger> from = query.from(Ledger.class);
		query.where(builder.and(builder.equal(from.get("account"), name),builder.between(from.get("entryDate"), startDate, endDate),builder.equal(from.get("status"), true)));
		query.orderBy(builder.asc(from.get("entryDate")));
		TypedQuery<Ledger> ledgerQuery = session.createQuery(query);
		return ledgerQuery.getResultList();
	}
	
	@Transactional
	public Double[] getBalances(String name, LocalDateTime startDate, LocalDateTime endDate) {
		Session session = factory.getCurrentSession();
		String openingBalance = " SELECT SUM(l.debitAmount) - SUM(l.creditAmount) FROM Ledger l WHERE l.account = :name"
				+ " AND l.entryDate < :sDate and status = 1 ";
		
		String rangeBalanceDebitQuery = "SELECT SUM(l.debitAmount), SUM(l.creditAmount),SUM(l.debitAmount) - SUM(l.creditAmount) FROM Ledger l WHERE "
				+ "(l.account = :name) AND (l.entryDate BETWEEN :sDate AND :eDate) AND status = 1";
		TypedQuery<Double> openingBalanceQuery = session.createQuery(openingBalance,Double.class);
		openingBalanceQuery.setParameter("name", name);
		openingBalanceQuery.setParameter("sDate", startDate);
		double openingBalanceAmount = 0.0;
		try {
			Double openingBalanceDouble = openingBalanceQuery.getSingleResult();
			openingBalanceAmount = openingBalanceDouble != null ? openingBalanceDouble : 0.0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// Get balance of selected records
		TypedQuery<Object[]> creditDebitAmount = session.createQuery(openingBalance,Object[].class);
		creditDebitAmount = session.createQuery(rangeBalanceDebitQuery, Object[].class);
		creditDebitAmount.setParameter("name", name);
		creditDebitAmount.setParameter("sDate", startDate);
		creditDebitAmount.setParameter("eDate", endDate);
		double selectedRecordsBalance = 0.0;
		double selectedRangeCredit = 0.0;
		double selectedRangeDebit = 0.0;
		try {
			Object [] openingBalanceArray = creditDebitAmount.getSingleResult();
			selectedRecordsBalance = (Double)openingBalanceArray[2] != null ? (Double)openingBalanceArray[2] : 0.0 ;
			selectedRangeCredit = (Double)openingBalanceArray[1] != null ? (Double)openingBalanceArray[1] : 0.0; 
			selectedRangeDebit = (Double)openingBalanceArray[0] != null ? (Double)openingBalanceArray[0] : 0.0; 
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		/** For total of debit and credit.
		 * If There is a transaction on debit side only then total will display else total is 0.0.
		 * If opening balance is in debit side then total will be opening balance + today's transaction debit else today's tranaction debit.
		 * If opening balance is credit side then total will be opening balance + today's transaction credit else today's tranaction debit.
		 */
		double debitTotal = 0.0;
		double creditTotal = 0.0;
		// set debit total
		if(selectedRangeDebit > 0.0) {
			if(openingBalanceAmount > 0.0) {
				debitTotal = openingBalanceAmount + selectedRangeDebit;
			}
			else {
				debitTotal = selectedRangeDebit;
			}
		}
		if(selectedRangeCredit > 0.0) {
			if(openingBalanceAmount < 0.0) {
				creditTotal = Math.abs(openingBalanceAmount) + selectedRangeCredit; 
			}
			else {
				creditTotal = selectedRangeCredit;
			}
		}
		return new Double[]{openingBalanceAmount , selectedRecordsBalance, debitTotal, selectedRangeCredit};
	}
	// ------------------------------ End Ledger DAO ---------------------------------------------
	
	// ----------------------------- Journal Report ----------------------------------------------
	@Transactional
	public List<Ledger> getJournalReport(LocalDateTime startDate, LocalDateTime endDate) {
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Ledger> query = builder.createQuery(Ledger.class); 
		Root<Ledger> rootFrom = query.from(Ledger.class);
		
		query.where(builder.and(builder.like(rootFrom.get("description"), "%journal%"),
				builder.equal(rootFrom.get("status"),true),
				builder.between(rootFrom.get("entryDate"), startDate, endDate)));
		query.orderBy(builder.asc(rootFrom.get("entryDate")));
		TypedQuery<Ledger> journalQuery = session.createQuery(query);
		List<Ledger> ledgerQuery = journalQuery.getResultList();
		return ledgerQuery;
	}
	
	// ---------------------------- End Journal Report -----------------------------------------
	
	
	
	// ------------------------------ Utility DAO ----------------------------------------
	@Transactional
	public void getTypeId() {
		Session session = factory.getCurrentSession();
		List<Long> idList = session.createQuery("SELECT id FROM GeneralData WHERE description in ('Owner','Contractor','Sanchalan')",Long.class).getResultList();
	}
	
	
	// ------------------------------ End Utility DAO ----------------------------------------
}
