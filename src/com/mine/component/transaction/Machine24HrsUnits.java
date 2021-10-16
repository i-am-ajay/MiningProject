package com.mine.component.transaction;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.mine.component.master.Machine;
@Entity
@Table(name="machine_24hrs_unit")
public class Machine24HrsUnits {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate unitDate;
	
	@Column(name="last_unit")
	private double lastUnit;
	
	@Column(name="current_unit")
	private double currentUnit;
	
	@Column(name="hours")
	private double hours;
	
	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machineId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getUnitDate() {
		return unitDate;
	}

	public void setUnitDate(LocalDate unitDate) {
		this.unitDate = unitDate;
	}

	public double getLastUnit() {
		return lastUnit;
	}

	public void setLastUnit(double lastUnit) {
		this.lastUnit = lastUnit;
	}

	public double getCurrentUnit() {
		return currentUnit;
	}

	public void setCurrentUnit(double currentUnit) {
		this.currentUnit = currentUnit;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public Machine getMachineId() {
		return machineId;
	}

	public void setMachineId(Machine machineId) {
		this.machineId = machineId;
	}
}
