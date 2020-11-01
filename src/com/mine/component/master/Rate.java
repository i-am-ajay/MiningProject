package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Rate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false)
	private int rateId;
	
	private String tyreType;
	
	private String quantity;
	
	private String materialType;
	
	private String truckType;
	
	private Double rate;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	private LocalDate createdDate;
	
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdById;
	
	@ManyToOne
	private User endedById;

	public String getTyreType() {
		return tyreType;
	}

	public void setTyreType(String tyreType) {
		this.tyreType = tyreType;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdBy) {
		this.createdDate = createdBy;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public User getCreatedById() {
		return createdById;
	}

	public void setCreatedById(User createdById) {
		this.createdById = createdById;
	}

	public User getEndedById() {
		return endedById;
	}

	public void setEndedById(User endedById) {
		this.endedById = endedById;
	}

	public int getRateId() {
		return rateId;
	}
	
	public void setRateId(int rateId){
		this.rateId = rateId;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
