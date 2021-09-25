package com.ajay.customeditor;

import java.beans.PropertyEditorSupport;

import com.mine.component.master.Machine;
import com.mine.service.FuelDistributionService;

public class MachinePropertyEditor extends PropertyEditorSupport{
	
	private FuelDistributionService fuelService;
	public MachinePropertyEditor(FuelDistributionService service){
		this.fuelService = service;
	}
	@Override
	public void setAsText(String idValue) {
		int id = Integer.parseInt(idValue);
		Machine machine = fuelService.getMachine(id);
		if(machine == null) {
			setValue(null);
		}
		else {
			setValue(machine);
		}
	}
}
