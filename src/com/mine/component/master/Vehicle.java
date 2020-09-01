package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vehicle {
	@Id
	private String vehicleNo;
	
	private String vehicleType; // Body Gadi or Dumper
	
	private int tyreType; // 10,12,14,16,18,20,22
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="client_id")
	private Client clientId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id")
	private Company companyId;
	
	private LocalDate createdBy;
	
	private LocalDate endDate;
	
	private Integer createdById;
	
	private Integer endedById;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getTyreType() {
		return tyreType;
	}

	public void setTyreType(int tyreType) {
		this.tyreType = tyreType;
	}

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public LocalDate getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(LocalDate createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getCreatedById() {
		return createdById;
	}

	public void setCreatedById(int createdById) {
		this.createdById = createdById;
	}

	public int getEndedById() {
		return endedById;
	}

	public void setEndedById(int endedById) {
		this.endedById = endedById;
	}
}
