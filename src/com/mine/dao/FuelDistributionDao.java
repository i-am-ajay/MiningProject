package com.mine.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mine.component.master.Fuel;
import com.mine.component.master.Machine;
import com.mine.component.transaction.FuelDistribution;
import com.mine.component.transaction.Machine24HrsUnits;

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
		machine.setLastUnitForFuel(distribution.getCurrentUnits());
		session.update(machine);
		session.save(distribution);
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
	
	public List<Machine24HrsUnits> save24HrsList() {
		return null;
	}
	
	// ------------------------------------------ End 24 hrs unit list -------------------------------------------------------
}
