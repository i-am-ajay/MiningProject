package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
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
	
	@Column(name="bucket_rate")
	protected double bucketRate;
	
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

	public double getBucketRate() {
		return bucketRate;
	}

	public void setBucketRate(double bucketRate) {
		this.bucketRate = bucketRate;
	}	
}
