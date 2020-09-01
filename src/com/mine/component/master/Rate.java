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
	
	private Integer createdById;
	
	private Integer endedById;

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

	public Integer getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}

	public Integer getEndedById() {
		return endedById;
	}

	public void setEndedById(Integer endedById) {
		this.endedById = endedById;
	}

	public int getRateId() {
		return rateId;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public void setCreatedById(int createdById) {
		this.createdById = createdById;
	}

	public void setEndedById(int endedById) {
		this.endedById = endedById;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
