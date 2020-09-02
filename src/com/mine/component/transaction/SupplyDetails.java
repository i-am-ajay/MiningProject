package com.mine.component.transaction;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.mine.component.master.Vehicle;

@Entity
public class SupplyDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="vehicle_id")
	private Vehicle vehicle;
	private String driverName;
	private String driverNumber;
	private String quantity;
	private String material;
	@Transient
	private String vehicleType;
	@Transient
	private String tyreType;
	
	private String paymentType;
	private boolean driverReturn;
	private double discount;
	private double rate;
	
	private int soldBy;
	
	private LocalDate salesDate;
	
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
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String qunatity) {
		this.quantity = qunatity;
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
	public int getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(int soldBy) {
		this.soldBy = soldBy;
	}
	public LocalDate getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(LocalDate salesDate) {
		this.salesDate = salesDate;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getTyreType() {
		return tyreType;
	}
	public void setTyreType(String tyreType) {
		this.tyreType = tyreType;
	}	
}
