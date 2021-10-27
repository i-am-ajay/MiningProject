package com.mine.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotationPredicates;
import org.springframework.stereotype.Repository;

import com.mine.component.master.Fuel;
import com.mine.component.master.Machine;
import com.mine.component.transaction.FuelDistribution;
import com.mine.component.transaction.Machine24HrsUnits;
import com.mine.component.transaction.UnitContainer;

@Repository
public class FuelDistributionDao {
	/**
	 * If Fule added or fuel given to some machine entries will be inserted in database by this method.
	 * @param distribution
	 */
	@Autowired
	SessionFactory factory;
	// --------------------------------- Fuel Related Methods ---------------------------------------------
	@Transactional
	public void insertFuelRecord(FuelDistribution distribution) {
		Session session = factory.getCurrentSession();
		Fuel fuel = getTotalFuel();
		fuel.setQty(fuel.getQty()+distribution.getFuelQty());
		session.update(fuel);
		Machine machine = distribution.getMachineName();
		/*if(machine != null) {
			FuelDistribution lastDistribution = this.lastUnitOfMachine(machine, distribution.getEntryDate());
			LocalDate lastEntryDate = lastEntryDateMachine(machine);
			if((machine.getEntryDate().equals(lastEntryDate) ||
					machine.getEntryDate().isAfter(lastEntryDate)
					&& distribution.getCurrentUnits()>0)) {
				machine.setLastUnitForFuel(distribution.getCurrentUnits());
				session.update(machine);
			}
		}*/
		session.save(distribution);
	}
	
	@Transactional
	public FuelDistribution lastUnitOfMachine(Machine machine, LocalDate entryDate) {
		Session session = factory.getCurrentSession();
		List<FuelDistribution> lastUnit = null;
		
		TypedQuery<FuelDistribution> lastUnitQuery = session.createQuery("FROM FuelDistribution fd "
				+ "WHERE fd.entryDate <= :entryDate AND fd.machineName = :machine AND fd.currentUnits <> 0"
				+ "ORDER BY fd.id DESC",FuelDistribution.class).setMaxResults(1);
		lastUnitQuery.setParameter("entryDate", entryDate);
		lastUnitQuery.setParameter("machine", machine);
		FuelDistribution lastUnitDistribution = null;
		try {
			lastUnit = lastUnitQuery.getResultList();
			if(lastUnit != null && lastUnit.size() > 0) {
				lastUnitDistribution = lastUnit.get(0);
			}
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		return lastUnitDistribution;
	}
	
	@Transactional
	public Fuel getTotalFuel() {
		String query = "FROM Fuel";
		Fuel fuel = null;
		Session session = factory.getCurrentSession();
		TypedQuery<Fuel> fuelQuery = session.createQuery(query,Fuel.class);
		try {
			fuel = fuelQuery.getSingleResult();
		}
		catch(Exception ex) {
			fuel = new Fuel();
			fuel.setEntryDate(LocalDate.now());
			fuel.setQty(0);
			session.save(fuel);
		}
		return fuel;
	}
	
	@Transactional
	public List<FuelDistribution> getFuleDistributionReport(int machineId, LocalDate fromDate,LocalDate toDate){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<FuelDistribution> criteriaQuery = builder.createQuery(FuelDistribution.class);
		Root<FuelDistribution> from = criteriaQuery.from(FuelDistribution.class);
		List<Predicate> predicateList = new ArrayList<>();
		if(machineId >0){
			predicateList.add(builder.equal(from.get("machineName").get("id"),machineId));
		}
		else if(machineId == 0) {
			predicateList.add(builder.isNull(from.get("machineName")));
		}
		
		if(fromDate != null && toDate != null) {
			predicateList.add(builder.between(from.get("entryDate"), fromDate, toDate));
		}
		else if(fromDate != null){
			predicateList.add(builder.greaterThanOrEqualTo(from.get("entryDate"), fromDate));
		}
		else if(toDate != null) {
			predicateList.add(builder.lessThanOrEqualTo(from.get("entryDate"), toDate));
		}
		criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
		
		TypedQuery<FuelDistribution> typedQuery = session.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	//--------------------------------------- End Fuel Related Methods -------------------------------------------------
	
	//--------------------------------------- Machine Methods ------------------------------------------------------
	@Transactional
	public Machine getMachine(int machineId) {
		Session session = factory.getCurrentSession();
		return session.get(Machine.class, machineId); 
	}
	
	@Transactional
	public Machine getMachine(String name) {
		Machine machine = null;
		Session session = factory.getCurrentSession();
		TypedQuery<Machine> machineQuery = session.createQuery("from Machine m WHERE m.name= :name",Machine.class);
		machineQuery.setParameter("name", name);
		try {
			machine = machineQuery.getSingleResult();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return machine;
	}
	
	
	@Transactional
	public List<Machine> machineList(LocalDate date){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Machine> machineCriteria = builder.createQuery(Machine.class);
		Root<Machine> fromMachine = machineCriteria.from(Machine.class);
		machineCriteria.where(builder.and(builder.lessThanOrEqualTo(fromMachine.get("entryDate"), date),builder.isNull(fromMachine.get("endDate"))));
		TypedQuery<Machine> machineQuery = session.createQuery(machineCriteria);
		return machineQuery.getResultList();
	}
	
	@Transactional
	public void saveMachine(Machine machine) {
		Session session = factory.getCurrentSession();
		System.out.println(machine.toString());
		session.saveOrUpdate(machine);
	}
	
	// ----------------------------------------- End Machine Methods ---------------------------------------------------------
	
	// ----------------------------------------- Enter 24 hrs List -----------------------------------------------------------
	
	@Transactional
	public void save24HrsList(UnitContainer container,LocalDate date) {
		List<Machine24HrsUnits> unitList = container.getMachineList();
		
		Session session = factory.getCurrentSession();
		for(Machine24HrsUnits machineUnit : unitList){
			machineUnit.setUnitDate(date);
			Machine machine = this.getMachine(machineUnit.getMachineId().getId());
			/*if(date.equals(LocalDate.now()) && machineUnit.getCurrentUnit() != 0) {
				machine.setLast24HrsUnit(machineUnit.getCurrentUnit());
				session.update(machine);
			}*/
			//machine.setLast24HrsUnit(machineUnit.getCurrentUnit());
			//session.update(machine);
			machineUnit.setMachineId(machine);
			session.saveOrUpdate(machineUnit);
		}
	}
	
	@Transactional
	public Machine24HrsUnits getLast24HrsUnitForMachine(Machine machine, LocalDate lastUnitDate) {
		Session session = factory.getCurrentSession();
		List<Machine24HrsUnits> lastUnit = null;
		
		TypedQuery<Machine24HrsUnits> lastUnitQuery = session.createQuery("FROM Machine24HrsUnits mu "
				+ "WHERE mu.unitDate <= :lastUnitDate AND mu.machineId = :machine AND mu.currentUnit <> 0"
				+ "ORDER BY mu.id DESC",Machine24HrsUnits.class).setMaxResults(1);
		lastUnitQuery.setParameter("lastUnitDate", lastUnitDate);
		lastUnitQuery.setParameter("machine", machine);
		Machine24HrsUnits last24HrsUnitDistribution = null;
		try {
			lastUnit = lastUnitQuery.getResultList();
			if(lastUnit != null && lastUnit.size() > 0) {
				last24HrsUnitDistribution = lastUnit.get(0);
			}
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		return last24HrsUnitDistribution;
	}
	
	@Transactional
	public List<Machine24HrsUnits> get24HrsList(LocalDate date) {
		Session session = factory.getCurrentSession();
		TypedQuery<Machine24HrsUnits> query = session.createQuery("FROM Machine24HrsUnits WHERE unitDate =: unitDate",Machine24HrsUnits.class);
		query.setParameter("unitDate",date);
		return query.getResultList();
	}
	
	@Transactional
	public LocalDate lastEnrtyDate24HrsUnit() {
		Session session = factory.getCurrentSession();
		TypedQuery<LocalDate> dateQuery = session.createQuery("SELECT max(mu.unitDate) FROM Machine24HrsUnits mu WHERE mu.machineId IS NOT NULL",LocalDate.class);
		LocalDate date = null; 
		try{
			date = dateQuery.getSingleResult();
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		return date;
	}
	
	// ------------------------------------------ End 24 hrs unit list -------------------------------------------------------
}
