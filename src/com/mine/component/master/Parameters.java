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

@Entity
@Table(name="parameters")
public class Parameters {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	
	protected double royalty;
	
	@Column(name="driver_return")
	protected double driverReturn;
	
	@Column(name="royalty_changed")
	protected boolean royaltyChanged;
	
	@Column(name="driver_return_changed")
	protected boolean driverReturnChanged;
	
	// FinalRate <= freeLimit : Sanchalan will be equal to sanchalanOnFree.
	protected double freeLimit;
	
	// Number of Vehicle Filled < thresholdLimit : sanchalanHigh : sanchalanLow 
	protected int threshholdLimit;
	
	protected double sanchalanOnFree;
	
	// when vehicle filled is less than threshhold
	protected double sanchalanHigh;
	
	protected double sanchalanLow;
	
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

	public boolean isRoyaltyChanged() {
		return royaltyChanged;
	}

	public void setRoyaltyChanged(boolean royaltyChanged) {
		this.royaltyChanged = royaltyChanged;
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

	public double getDriverReturn() {
		return driverReturn;
	}

	public void setDriverReturn(double driverReturn) {
		this.driverReturn = driverReturn;
	}

	public boolean isDriverReturnChanged() {
		return driverReturnChanged;
	}

	public void setDriverReturnChanged(boolean driverReturnChanged) {
		this.driverReturnChanged = driverReturnChanged;
	}

	public double getFreeLimit() {
		return freeLimit;
	}

	public void setFreeLimit(double freeLimit) {
		this.freeLimit = freeLimit;
	}

	public int getThreshholdLimit() {
		return threshholdLimit;
	}

	public void setThreshholdLimit(int threshholdLimit) {
		this.threshholdLimit = threshholdLimit;
	}

	public double getSanchalanOnFree() {
		return sanchalanOnFree;
	}

	public void setSanchalanOnFree(double sanchalanOnFree) {
		this.sanchalanOnFree = sanchalanOnFree;
	}

	public double getSanchalanHigh() {
		return sanchalanHigh;
	}

	public void setSanchalanHigh(double sanchalanHigh) {
		this.sanchalanHigh = sanchalanHigh;
	}

	public double getSanchalanLow() {
		return sanchalanLow;
	}

	public void setSanchalanLow(double sanchalanLow) {
		this.sanchalanLow = sanchalanLow;
	}	
}
