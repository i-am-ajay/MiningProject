package com.mine.service;

import java.time.LocalDate;
import java.time.Period;
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
		this.startNewCycle(machine, machine.getEntryDate());
		
		//machine.setCycleEndDate();
		fuelDao.saveMachine(machine);
	}
	/*-------------------- End Machine Service -------------------------*/
	
	/*-------------------- Fuel Service --------------------------------*/
	public void insertFuelRecord(FuelDistribution dist) {
		fuelDao.insertFuelRecord(dist);
	}
	
	public FuelDistribution getLastMachineDistribution(Machine machine, LocalDate entryDate) {
		return fuelDao.lastUnitOfMachine(machine, entryDate);
	}
	
	public Map<Integer,Machine> getMachineMap(LocalDate date, boolean machineDes){
		Map<Integer,Machine> machineMap = new HashMap<>();
		List<Machine> list = fuelDao.machineList(date);
		if(list != null && list.size() > 0) {
			list.forEach(machine ->{
				machineMap.put(machine.getId(), machine);
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
	
	public LocalDate getLastEntryDate24HrsUnit() {
		return fuelDao.lastEnrtyDate24HrsUnit();
	}
	
	public Machine24HrsUnits getLast24HrsUnitMachine(Machine machine, LocalDate lastUnitDate) {
		return fuelDao.getLast24HrsUnitForMachine(machine, lastUnitDate);
	}
	/*-------------------------- End Capture 24 hrs unit ---------------------*/
	
	/*-------------------------- Machine Cycle Methods -----------------------*/
	public void startNewCycle(Machine machine, LocalDate cycleStartDate) {
		machine.setCycleBeginDate(cycleStartDate);
		machine.setCycleEndDate(cycleStartDate.plusDays(30));
		machine.setCycle(machine.getCycle()+1);
	}
	/*------------------------- End Machine Cycle Methods --------------------*/
}
