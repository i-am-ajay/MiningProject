package com.mine.component.transaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mine.component.master.Vehicle;

@Entity
public class SupplyDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Vehicle vehicle;
	private String driverName;
	private String driverNumber;
	private String qunatity;
	private String material;
	private String paymentType;
	private boolean driverReturn;
	private double discount;
	private double rate;
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverNumber() {
		return driverNumber;
	}
	public void setDriverNumber(String driverNumber) {
		this.driverNumber = driverNumber;
	}
	public String getQunatity() {
		return qunatity;
	}
	public void setQunatity(String qunatity) {
		this.qunatity = qunatity;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public boolean isDriverReturn() {
		return driverReturn;
	}
	public void setDriverReturn(boolean driverReturn) {
		this.driverReturn = driverReturn;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getId() {
		return id;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
