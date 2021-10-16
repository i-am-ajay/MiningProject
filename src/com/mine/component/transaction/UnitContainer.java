package com.mine.component.transaction;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UnitContainer {
	ArrayList<Machine24HrsUnits> machineList = new ArrayList<>();
	private String sampleText;
	private int currentIndex = 0;
	private String test = "test";
	private Machine24HrsUnits currentMachine;

	public ArrayList<Machine24HrsUnits> getMachineList() {
		return machineList;
	}

	public void setMachineList(ArrayList<Machine24HrsUnits> machineList) {
		this.machineList = machineList;
	}
	
	public void next() {
		currentMachine = machineList.get(currentIndex);
		System.out.println(currentMachine.getMachineId().getName());
		if(currentIndex < machineList.size())
			currentIndex++;
	}
	
	public void reset() {
		currentMachine = null;
		currentIndex = 0;
	}

	public Machine24HrsUnits getCurrentMachine() {
		return currentMachine;
	}

	public void setCurrentMachine(Machine24HrsUnits currentMachine) {
		this.currentMachine = currentMachine;
	}

	public String getSampleText() {
		return sampleText;
	}

	public void setSampleText(String sampleText) {
		this.sampleText = sampleText;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
