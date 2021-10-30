package com.mine.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		//distribution.setHrs(distribution.getCurrentUnits() - distribution.getLastUnits());
		session.save(distribution);
	}
	
	@Transactional
	public double lastUnitOfMachine(Machine machine, LocalDateTime entryDate) {
		Session session = factory.getCurrentSession();
		List<FuelDistribution> lastUnit = null;
		
		TypedQuery<FuelDistribution> lastUnitQuery = session.createQuery("FROM FuelDistribution fd "
				+ "WHERE fd.entryDate <= :entryDate AND fd.machineName = :machine AND fd.currentUnits <> 0"
				+ "ORDER BY fd.id DESC",FuelDistribution.class).setMaxResults(1);
		lastUnitQuery.setParameter("entryDate", entryDate);
		lastUnitQuery.setParameter("machine", machine);
		double lastUnitValue = 0.0;
		try {
			lastUnit = lastUnitQuery.getResultList();
			if(lastUnit != null && lastUnit.size() > 0) {
				lastUnitValue = lastUnit.get(0).getCurrentUnits();
			}
			else {
				Machine machineObj = this.getMachine(machine.getId());
				lastUnitValue = machineObj.getOpeningUnit();
			}
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		return lastUnitValue;
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
	public double getLast24HrsUnitForMachine(Machine machine, LocalDate lastUnitDate) {
		Session session = factory.getCurrentSession();
		List<Machine24HrsUnits> lastUnit = null;
		
		TypedQuery<Machine24HrsUnits> lastUnitQuery = session.createQuery("FROM Machine24HrsUnits mu "
				+ "WHERE mu.unitDate <= :lastUnitDate AND mu.machineId = :machine AND mu.currentUnit <> 0"
				+ "ORDER BY mu.id DESC",Machine24HrsUnits.class).setMaxResults(1);
		lastUnitQuery.setParameter("lastUnitDate", lastUnitDate);
		lastUnitQuery.setParameter("machine", machine);
		double last24HrsUnitValue = machine.getOpeningUnit();
		try {
			lastUnit = lastUnitQuery.getResultList();
			if(lastUnit != null && lastUnit.size() > 0) {
				last24HrsUnitValue = lastUnit.get(0).getCurrentUnit();
			}
			
		}
		catch(HibernateException ex) {
			ex.printStackTrace();
		}
		return last24HrsUnitValue;
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
	
	// ------------------------------------------ Unit update logic ----------------------------------------------------------
	@Transactional
	public List<FuelDistribution> getFuelUnits(Machine machine, LocalDate date) {
		LocalDate updateDate = LocalDate.now();
		LocalDateTime startDate = LocalDateTime.of(date, LocalTime.of(0, 0));
		LocalDateTime endDate = LocalDateTime.of(date, LocalTime.of(23, 59));
		Session session = factory.getCurrentSession();
/*		TypedQuery<FuelDistribution> unitQuery = session.createQuery("FROM FuelDistribution fd WHERE fd.machineName = :machine AND (fd.entryDate BETWEEN :startDate AND :endDate) AND (fd.machineName.cycleBeginDate >= :updateDate AND fd.machineName.cycleEndDate <= :updateDate)"
				+ " ORDER BY fd.entryDate ASC",FuelDistribution.class);*/
		System.out.println("FuelDao Machine Id"+machine.getId());
		TypedQuery<FuelDistribution> unitQuery = session.createQuery("FROM FuelDistribution fd WHERE fd.machineName = :machine AND (fd.entryDate BETWEEN :startDate AND :endDate)"
				+ " ORDER BY fd.entryDate ASC",FuelDistribution.class);
		unitQuery.setParameter("machine", machine);
		unitQuery.setParameter("startDate", startDate);
		unitQuery.setParameter("endDate", endDate);
		//unitQuery.setParameter("updateDate", updateDate);
		List<FuelDistribution> fuelDistributionList = unitQuery.getResultList();
		return fuelDistributionList;
	}
	
	// update fule entry.
	@Transactional
	public boolean updateUnit(int id, double unitValue) {
		boolean status = false;
		Session session = factory.getCurrentSession();
		FuelDistribution dis = session.get(FuelDistribution.class, id);
		Machine machine = dis.getMachineName();
		dis.setCurrentUnits(unitValue);
		dis.setHrs(unitValue - dis.getLastUnits());
		FuelDistribution nextEntry = getNextFuelRecord(id, dis.getEntryDate(),machine);
		session.update(dis);
		if(nextEntry != null) {
			nextEntry.setLastUnits(unitValue);
			session.update(nextEntry);
		}
		status = true;
		return status;
	}
	
	@Transactional
	public FuelDistribution getNextFuelRecord(int id, LocalDateTime date, Machine machine) {
		Session session = factory.getCurrentSession();
		FuelDistribution distribution = null;
		TypedQuery<FuelDistribution> typedQuery = session.createQuery("From FuelDistribution fd WHERE fd.entryDate > :date AND fd.id <> :id"
				+ " AND fd.machineName = :machine AND fd.currentUnits > 0 ORDER BY fd.entryDate ASC",FuelDistribution.class);
		typedQuery.setParameter("date", date);
		typedQuery.setParameter("id", id);
		typedQuery.setParameter("machine", machine);
		typedQuery.setMaxResults(1);
		List<FuelDistribution> fuelUnitsList = typedQuery.getResultList();
		if(fuelUnitsList != null && fuelUnitsList.size() > 0) {
			distribution = fuelUnitsList.get(0);
		}
		return distribution;
	}
	
	// update 24 hrs fuel entry
	@Transactional
	public boolean update24HrsUnit(int id, double unitValue) {
		boolean status = false;
		Session session = factory.getCurrentSession();
		Machine24HrsUnits _24HrsUnit = session.get(Machine24HrsUnits.class, id);
		Machine machine = _24HrsUnit.getMachineId();
		_24HrsUnit.setCurrentUnit(unitValue);
		_24HrsUnit.setHours(unitValue - _24HrsUnit.getLastUnit());
		Machine24HrsUnits nextEntry = getNext24hrsRecord(id, _24HrsUnit.getUnitDate(), machine);
		session.update(_24HrsUnit);
		if(nextEntry != null) {
			nextEntry.setLastUnit(unitValue);
			nextEntry.setHours(nextEntry.getCurrentUnit() - unitValue);
			session.update(nextEntry);
		}
		status = true;
		return status;
	}
	
	@Transactional
	public Machine24HrsUnits getNext24hrsRecord(int id, LocalDate date, Machine machine) {
		Session session = factory.getCurrentSession();
		Machine24HrsUnits m24HrsUnits = null;
		TypedQuery<Machine24HrsUnits> typedQuery = session.createQuery("From Machine24HrsUnits mu WHERE mu.unitDate > :date "
				+ " AND mu.machineId= :machine AND mu.currentUnit > 0 ORDER BY mu.unitDate ASC",Machine24HrsUnits.class);
		typedQuery.setParameter("date", date);
		typedQuery.setParameter("machine", machine);
		typedQuery.setMaxResults(1);
		List<Machine24HrsUnits> m24HrsUnitsList = typedQuery.getResultList();
		if(m24HrsUnitsList != null && m24HrsUnitsList.size() > 0) {
			m24HrsUnits = m24HrsUnitsList.get(0);
		}
		return m24HrsUnits;
	}
	
	@Transactional
	public List<Machine24HrsUnits> getMachine24HrsUnits(Machine machine, LocalDate date){
		LocalDate nextUnitdate = date.plusDays(1);
		LocalDate updateDate = LocalDate.now();
		Session session = factory.getCurrentSession();
		TypedQuery<Machine24HrsUnits> unitQuery = session.createQuery("FROM Machine24HrsUnits mu "
				+ "WHERE mu.machineId = :machine AND mu.unitDate = :date"
				+ " ORDER BY mu.unitDate ASC",Machine24HrsUnits.class);
		unitQuery.setParameter("machine", machine);
		unitQuery.setParameter("date", date);
		List<Machine24HrsUnits> machine24HrsUnitsList = unitQuery.getResultList();
		return machine24HrsUnitsList;
	}
	// ----------------------------------------- End of Unit update Logic -----------------------------------------------------
}
