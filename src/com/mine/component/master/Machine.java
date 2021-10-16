package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Machine {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="machine_type")
	private String machineType;
	
	@Column(name="machine_rate")
	private double machineRate=0.0;
	
	@Column(name="fixed_hours")
	private double fixedHours =0.0;
	
	@Column(name="cycle")
	private double cycle=0;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate entryDate;
	
	@Column(name="last_unit_for_fuel")
	private double lastUnitForFuel=0.0;
	
	@Column(name="last_24hrs_unit")
	private double last24HrsUnit=0.0;
	
	@ManyToOne()
	@JoinColumn(name="vendor_id")
	private Client vendorId;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate endDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public double getMachineRate() {
		return machineRate;
	}
	public void setMachineRate(double machineRate) {
		this.machineRate = machineRate;
	}
	public double getFixedHours() {
		return fixedHours;
	}
	public void setFixedRate(double fixedHours) {
		this.fixedHours = fixedHours;
	}
	public double getCycle() {
		return cycle;
	}
	public void setCycle(double cycle) {
		this.cycle = cycle;
	}
	public void setFixedHours(double fixedHours) {
		this.fixedHours = fixedHours;
	}
	public double getLastUnitForFuel() {
		return lastUnitForFuel;
	}
	public void setLastUnitForFuel(double lastUnitForFuel) {
		this.lastUnitForFuel = lastUnitForFuel;
	}
	public double getLast24HrsUnit() {
		return last24HrsUnit;
	}
	public void setLast24HrsUnit(double last24HrsUnit) {
		this.last24HrsUnit = last24HrsUnit;
	}
	public Client getVendorId() {
		return vendorId;
	}
	public void setVendorId(Client vendorId) {
		this.vendorId = vendorId;
	}
	
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String toString() {
		return "id"+id+" machineRate:"+machineRate+"fixedHours:"+fixedHours+"entryDate:"+entryDate+"lastUnitForFuel:"+lastUnitForFuel+"last24HrsUnit"+last24HrsUnit;
	}
	
}
