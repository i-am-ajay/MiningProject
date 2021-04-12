package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name="parameters")
public class Parameters {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false)
	protected int id;
	
	protected double royalty;
	
	@Column(name="driver_return_normal")
	protected double driverReturnNormal;
	
	@Column(name="driver_return_small_vehicle")
	protected double driverReturnSmallVehicle;
	
	// FinalRate <= freeLimit : Sanchalan will be equal to sanchalanOnFree.
	protected double freeLimit;
	
	@Column(name="disable_sanchalan")
	protected boolean disableSanchalan;
	
	protected double sanchalanOnFree;
	
	// when vehicle filled is less than threshhold
	@Column(name="sanchalan_normal")
	protected double sanchalanNormal;
	
	protected double sanchalanTrolly;
	
	@Generated(GenerationTime.ALWAYS)
	@Column(name="creation_date", insertable=false, updatable=false)
	protected LocalDate creationDate;
	
	@Column(name="end_date")
	protected LocalDate endDate;

	public double getRoyalty() {
		return royalty;
	}

	public void setRoyalty(double royalty) {
		this.royalty = royalty;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public double getDriverReturnNormal() {
		return driverReturnNormal;
	}

	public void setDriverReturnNormal(double driverReturn) {
		this.driverReturnNormal = driverReturn;
	}

	public double getDriverReturnSmallVehicle() {
		return driverReturnSmallVehicle;
	}

	public void setDriverReturnSmallVehicle(double driverReturnChanged) {
		this.driverReturnSmallVehicle = driverReturnChanged;
	}

	public double getFreeLimit() {
		return freeLimit;
	}

	public void setFreeLimit(double freeLimit) {
		this.freeLimit = freeLimit;
	}

	public double getSanchalanOnFree() {
		return sanchalanOnFree;
	}

	public void setSanchalanOnFree(double sanchalanOnFree) {
		this.sanchalanOnFree = sanchalanOnFree;
	}

	public double getSanchalanNormal() {
		return sanchalanNormal;
	}

	public void setSanchalanNormal(double sanchalanHigh) {
		this.sanchalanNormal = sanchalanHigh;
	}

	public double getSanchalanTrolly() {
		return sanchalanTrolly;
	}

	public void setSanchalanTrolly(double sanchalanTrolly) {
		this.sanchalanTrolly = sanchalanTrolly;
	}

	public boolean isDisableSanchalan() {
		return disableSanchalan;
	}

	public void setDisableSanchalan(boolean disableSanchalan) {
		this.disableSanchalan = disableSanchalan;
	}	
}
