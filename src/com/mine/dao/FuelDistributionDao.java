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

@Repository
public class FuelDistributionDao {
	/**
	 * If Fule added or fuel given to some machine entries will be inserted in database by this method.
	 * @param distribution
	 */
	@Autowired
	SessionFactory factory;
	
	@Transactional
	public void insertFuelRecord(FuelDistribution distribution) {
		Session session = factory.getCurrentSession();
		Fuel fuel = getTotalFuel();
		fuel.setQty(fuel.getQty()+distribution.getFuelQty());
		session.update(fuel);
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
		}
		return fuel;
	}
	
	@Transactional
	public Machine getMachine(int machineId) {
		Session session = factory.getCurrentSession();
		return session.get(Machine.class, machineId); 
	}
	
	
	@Transactional
	public List<Machine> machineList(){
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Machine> machineCriteria = builder.createQuery(Machine.class);
		Root<Machine> fromMachine = machineCriteria.from(Machine.class);
		machineCriteria.where(builder.isNull(fromMachine.get("endDate")));
		
		TypedQuery<Machine> machineQuery = session.createQuery(machineCriteria);
		return machineQuery.getResultList();
	}
	
}
