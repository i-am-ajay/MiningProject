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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.mine.component.master.User;
import com.mine.component.master.Vehicle;

@Entity
@Audited
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
	
	@Column(name="unit_rate", columnDefinition = "double default=0.0")
	private Double unitRate = 0.0;
	
	
	private Integer unit = 0;
	
	private boolean status;
	
	@Column(name="free_material")
	private Integer freeMaterial;
	
	@Column(name="final_rate")
	private double finalRate;
	
	private double nrl;
	
	@Generated(GenerationTime.INSERT)
	@Column(name="sales_date", insertable=false, updatable=false)
	private LocalDateTime salesDate;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
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
	
	
	public String getQuantityDetail() {
		String qty = quantity;
		if(unit == null) {
			unit = 0;
		}
		if(quantity.equalsIgnoreCase("foot") || quantity.equalsIgnoreCase("bucket") ||
				quantity.equalsIgnoreCase("ton")){
			if(unit > 0) {
				qty = (Integer.toString(unit).concat(" ")).concat(quantity);
			}
		}
		return qty;
	}
	
	/**
	 * In case of bucket, foot, ton we need a numeric value along with quantity.
	 * @param unitRate
	 * @return
	 */
	public String getFormattedQuantity(int unitRate) {
		String qty = this.quantity;
		if(unit == null) {
			unit = 0;
		}
		if(freeMaterial == null) {
			freeMaterial = 0;
		}
		int unit = this.unit;
		if(this.quantity.equalsIgnoreCase("bucket") 
				|| this.quantity.equalsIgnoreCase("foot")
				|| this.quantity.equalsIgnoreCase("ton")) {
			
			if(unit > 0) {
				if(freeMaterial > 0) {
					qty = Integer.toString(unit).concat("+").concat(Integer.toString(freeMaterial)).concat(" ").concat(qty);
				}
				else {
					qty = Integer.toString(unit).concat(" ").concat(qty);
				}
			}
		}
		return qty;	
	}
	
	/**
	 * Test if sales data is correct, if it's correct then return true else return false.
	 * To validate sales data, we'll check rate, vehicle, client etc if any of the field is null data is not
	 * correct.
	 * @return
	 */
	public boolean isDataCorrect() {
		boolean flag = false;
		if((this.clientName != null || this.clientName.length() > 0) && 
			(this.clientName != null || this.clientName.length() > 0) &&
			(this.driverName != null || this.driverName.length() > 0) &&
			(this.driverNumber != null || this.driverNumber.length() >0) &&
			(this.material != null || this.material.length() > 0) &&
			(this.quantity != null || this.material.length() > 0) &&
			(this.rate != 0.0) && (this.unitRate != 0.0)) {
			flag = true;
		}
		return flag;
	}
	
	public Double getUnitRate() {
		return unitRate;
	}
	public void setUnitRate(Double unitRate){
		if(unitRate != null) {	
			this.unitRate = unitRate;
		}
		else {
			this.unitRate = 0.0;
		}
	}
	
	public Integer getUnit(){
		return unit;
	}
	
	@Column
	public void setUnit(Integer unitOf){
		if(unitOf != null) {
			this.unit = unitOf;
		}
		else{
			this.unit = 0;
		}
	}
	public Integer getFreeMaterial() {
		return freeMaterial;
	}
	
	public void setFreeMaterial(Integer freeMaterial) {
		this.freeMaterial = freeMaterial;
	}
	
	
}

