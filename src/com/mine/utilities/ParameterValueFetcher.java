package com.mine.utilities;

import org.springframework.beans.factory.annotation.Autowired;

import com.mine.component.master.Parameters;
import com.mine.service.MiningService;

public class ParameterValueFetcher {
	
	public static double getSanchalanAmount(Parameters params,String vehicleType) {
		double sanchalanAmount = 0.0;
		if(vehicleType.equalsIgnoreCase("Tralla") || vehicleType.equalsIgnoreCase("Trolly")) {
			sanchalanAmount = params.getSanchalanTrolly();
		}
		else if(vehicleType.equalsIgnoreCase("bugda")) {
			sanchalanAmount = 0.0;
		}
		else {
			sanchalanAmount = params.getSanchalanNormal();
		}
		return sanchalanAmount;
	}
	
	public static double getDriverReturnAmount(Parameters params, String vehicleType) {
		double driverReturnAmount = 0.0;
		if(vehicleType.equalsIgnoreCase("Tralla") || vehicleType.equalsIgnoreCase("Trolly")) {
			driverReturnAmount = params.getDriverReturnSmallVehicle();
		}
		else {
			driverReturnAmount = params.getDriverReturnNormal();
		}
		return driverReturnAmount;
	}
}
