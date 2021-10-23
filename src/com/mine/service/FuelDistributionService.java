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
import com.mine.component.transaction.UnitContainer;
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
	
	public List<Machine> getMachines(LocalDate date){
		return fuelDao.machineList(date);
	}
	
	public void saveMachine(Machine machine) {
		machine.setCycleBeginDate(machine.getEntryDate());
		//machine.setCycleEndDate();
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
		System.out.println("Service Map Size "+list.size());
		if(list != null && list.size() > 0) {
			list.forEach(machine ->{
				machineMap.put(machine.getId(), machineDes== true? machine.getName(): machine);
			});
		}
		return machineMap;
	}
	
	public List<FuelDistribution> getFuelDistributionReport(int machineId, LocalDate fromDate, LocalDate toDate){
		return fuelDao.getFuleDistributionReport(machineId, fromDate, toDate);
	}
	
	
	public double getTotalFuel() {
		return fuelDao.getTotalFuel().getQty();
	}
	/*-------------------------- End Fuel Service --------------------------- */
	
	/*-------------------------- Capture 24 hrs unit ------------------------*/
	public void capture24hrsUnit(UnitContainer container, LocalDate date) {
		fuelDao.save24HrsList(container, date);
	}
	
	public Map<Integer,Machine24HrsUnits> get24hrsUnitMap(LocalDate date) {
		List<Machine24HrsUnits> m24HrsUnitList = fuelDao.get24HrsList(date);
		Map<Integer,Machine24HrsUnits> map24HrsUnits = new HashMap<>();
		for(Machine24HrsUnits machineUnits : m24HrsUnitList){
			int machineId = machineUnits.getMachineId().getId();
			map24HrsUnits.put(machineId, machineUnits);
		}
		return map24HrsUnits;
	}
	/*-------------------------- End Capture 24 hrs unit ---------------------*/
}
