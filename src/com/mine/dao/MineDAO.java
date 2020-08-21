package com.mine.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mine.component.master.Client;
import com.mine.component.master.Vehicle;
import com.mine.component.transaction.SupplyDetails;

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
		session.save();
		session.flush();
	}
	
	public void saveInSalesBook(Client client, double amount) {
		Session session = factory.getCurrentSession();
		session.disableFetchProfile(name);;
		session.flush();
	}
}
