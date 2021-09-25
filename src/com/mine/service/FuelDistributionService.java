package com.mine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.component.master.Machine;
import com.mine.component.transaction.FuelDistribution;
import com.mine.dao.FuelDistributionDao;

@Service("fuel-dist")
public class FuelDistributionService {
	@Autowired
	FuelDistributionDao fuelDao;
	
	public Machine getMachine(int id) {
		return fuelDao.getMachine(id);
	}
	
	public void insertFuelRecord(FuelDistribution dist) {
		fuelDao.insertFuelRecord(dist);
	}
	
	public Map<Integer,String> getMachineMap(){
		Map<Integer,String> machineMap = new HashMap<>();
		List<Machine> list = fuelDao.machineList();
		if(list != null && list.size() > 0) {
			list.forEach(machine ->{
				machineMap.put(machine.getId(), machine.getName());
			});
		}
		return machineMap;
	}
	
	public double getTotalFuel() {
		return fuelDao.getTotalFuel().getQty();
	}
}
