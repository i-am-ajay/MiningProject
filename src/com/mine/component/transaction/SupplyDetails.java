package com.mine.component.transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.mine.component.master.User;
import com.mine.component.master.Vehicle;

@Entity
public class SupplyDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String token;
	
	@ManyToOne
	@JoinColumn(name="vehicle_id")
	private Vehicle vehicle;
	private String driverName;
	private String driverNumber;
	private String clientName;
	private String quantity;
	private String material;
	@Transient
	private String vehicleType;
	@Transient
	private String tyreType;
	
	private String paymentType;
	private double driverReturn;
	private double discount;
	private double rate;
	
	private boolean status;
	
	@Column(name="final_rate")
	private double finalRate;
	
	private double nrl;
	
	@Generated(GenerationTime.INSERT)
	@Column(name="sales_date", insertable=false, updatable=false)
	private LocalDateTime salesDate;
	
	@JoinColumn(name="sold_by")
	@ManyToOne
	private User user;
	
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
	public double getDriverReturn() {
		return driverReturn;
	}
	public void setDriverReturn(double driverReturn) {
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
	
	public LocalDateTime getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(LocalDateTime salesDate) {
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
	public double getFinalRate() {
		return finalRate;
	}
	public void setFinalRate(double finalRate) {
		this.finalRate = finalRate;
	}
	public double getNrl() {
		return nrl;
	}
	public void setNrl(double nrl) {
		this.nrl = nrl;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}	
	
	
	public String getQuantityDetail(double unitRate) {
		String qty = quantity;
		if(quantity.equalsIgnoreCase("foot") || quantity.equalsIgnoreCase("bucket") ||
				quantity.equalsIgnoreCase("ton")){
			int q = (int)(rate / unitRate);
			qty = (Integer.toString(q).concat(" ")).concat(quantity);
		}
		return qty;
	}
	
	public String getFormattedQuantity(double unitRate) {
		String qty = this.quantity;
		if(this.quantity.equalsIgnoreCase("bucket") 
				|| this.quantity.equalsIgnoreCase("foot")
				|| this.quantity.equalsIgnoreCase("ton")) {
			long r = Math.round(this.rate / unitRate);
			qty = Long.toString(r).concat(" ").concat(this.quantity);
		}
		return qty;	
	}
}
