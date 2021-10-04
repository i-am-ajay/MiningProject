package com.mine.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Machine;
import com.mine.component.transaction.FuelDistribution;
import com.mine.component.transaction.Machine24HrsUnits;
import com.mine.dao.FuelDistributionDao;

@Service("fuel-dist")
public class FuelDistributionService {
	@Autowired
	FuelDistributionDao fuelDao;
	
	/*------------------- Machine Service ------------------------------*/
	public Machine getMachine(int id) {
		System.out.println("Machine Id "+id);
		return fuelDao.getMachine(id);
	}
	
	public Machine getMachine(String name) {
		return fuelDao.getMachine(name);
	}
	
	public void saveMachine(Machine machine) {
		fuelDao.saveMachine(machine);
	}
	/*-------------------- End Machine Service -------------------------*/
	
	/*-------------------- Fuel Service --------------------------------*/
	public void insertFuelRecord(FuelDistribution dist) {
		fuelDao.insertFuelRecord(dist);
	}
	
	public Map<Integer,Object> getMachineMap(LocalDate date, boolean machineDes){
		Map<Integer,Object> machineMap = new HashMap<>();
		List<Machine> list = fuelDao.machineList(date);
		if(list != null && list.size() > 0) {
			list.forEach(machine ->{
				machineMap.put(machine.getId(), machineDes== true? machine.getName(): machine);
			});
		}
		return machineMap;
	}
	
	
	public double getTotalFuel() {
		return fuelDao.getTotalFuel().getQty();
	}
	/*-------------------------- End Fuel Service --------------------------- */
	
	/*-------------------------- Capture 24 hrs unit ------------------------*/
	public List<Machine24HrsUnits> capture24hrsUnit() {
		return null;
	}
	/*-------------------------- End Capture 24 hrs unit ---------------------*/
}
