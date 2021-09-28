package com.mine.utilities;

import java.beans.PropertyEditorSupport;

import com.mine.component.master.Machine;
import com.mine.service.FuelDistributionService;

public class MachinePropertyEditor extends PropertyEditorSupport{
	FuelDistributionService service;
	public MachinePropertyEditor(FuelDistributionService service) {
		 this.service = service;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException{
		int id = Integer.parseInt(text);
		System.out.println(id);
		Machine machine = service.getMachine(id);
		setValue(machine);
	}
}
