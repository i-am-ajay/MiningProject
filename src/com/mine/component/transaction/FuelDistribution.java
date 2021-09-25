package com.mine.component.transaction;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Component;

import com.mine.component.master.Machine;

@Entity
@Component
public class FuelDistribution {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="entry_type")
	private String entryType; // fuel recieved or distributed
	
	@Column(name="fuel_qty")
	private double fuelQty;
	
	@OneToOne()
	@JoinColumn(name="machine_id")
	private Machine machineName;
	
	@Column(name="entry_date")
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate entryDate;
	
	@Column
	private String remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public double getFuelQty() {
		return fuelQty;
	}

	public void setFuelQty(double qty) {
		this.fuelQty = qty;
	}

	public Machine getMachineName() {
		return machineName;
	}

	public void setMachineName(Machine machineName) {
		this.machineName = machineName;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
