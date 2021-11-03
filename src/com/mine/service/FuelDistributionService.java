package com.mine.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Machine;
import com.mine.component.master.Parameters;
import com.mine.component.master.User;
import com.mine.component.transaction.FuelDistribution;
import com.mine.component.transaction.Machine24HrsUnits;
import com.mine.component.transaction.UnitContainer;
import com.mine.dao.FuelDistributionDao;
import com.mine.dao.MineDAO;

@Service("fuel-dist")
public class FuelDistributionService {
	@Autowired
	FuelDistributionDao fuelDao;
	
	@Autowired
	MineDAO mineDao;
	
	/*------------------- Machine Service ------------------------------*/
	public Machine getMachine(int id) {
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
	/*-------------------- End Machine Service -------------------------*/
	
	/*-------------------- Fuel Service --------------------------------*/
	public void insertFuelRecord(FuelDistribution dist) {
		fuelDao.insertFuelRecord(dist);
	}
	
	public void postFuelAmountInLedger(FuelDistribution details, User user) {
		mineDao.addFuelToCashAndLedger(details, user);
	}
	
	public double getLastMachineDistribution(Machine machine, LocalDateTime entryDate) {
		return fuelDao.lastUnitOfMachine(machine, entryDate);
	}
	
	
	public List<FuelDistribution> getFuelDistributionReport(int machineId, LocalDateTime fromDate, LocalDateTime toDate){
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
	
	public double getLast24HrsUnitMachine(Machine machine, LocalDate lastUnitDate) {
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
	
	/* ------------------------ Update Unit service --------------------------*/
	public List<FuelDistribution> getFuelUnitsService(Machine machine, LocalDate date) {
		return fuelDao.getFuelUnits(machine, date);
	}
	
	public List<Machine24HrsUnits> getMachine24HrsUnitsService(Machine machine, LocalDate date){
		return fuelDao.getMachine24HrsUnits(machine, date);
	}
	
	public boolean updateUnitService(int machineId, Double unitValue) {
		return fuelDao.updateUnit(machineId, unitValue);
	}
	
	public boolean update24HrsUnitService(int machineId, Double unitValue) {
		return fuelDao.update24HrsUnit(machineId, unitValue);
	}
	/*------------------------- End Update Unit Service ---------------------- */
	
	/* ------------------------ Get Parameter Details ------------------------ */
	public Parameters getParameters() {
		return mineDao.getParameters();
	}
	/*------------------------- End Parameter Details ------------------------ */
}
