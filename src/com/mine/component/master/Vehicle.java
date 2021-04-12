package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Audited
public class Vehicle {
	@Id
	private String vehicleNo;
	
	private String vehicleType; // Body Gadi or Dumper
	
	private int tyreType; // 10,12,14,16,18,20,22
	
	@Column(columnDefinition="double(10,2) default 0.0")
	private double discount; 
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="client_id")
	private Client clientId;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id")
	private Company companyId;
	
	//@Generated(GenerationTime.ALWAYS)
	@Column(name="creation_time_stamp")
	private LocalDate creationTime;
	
	private LocalDate endDate;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdById;
	
	private Integer endedById;
	
	private boolean status;

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

	public LocalDate getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDate createdBy) {
		this.creationTime = createdBy;
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

	public int getEndedById() {
		return endedById;
	}

	public void setEndedById(int endedById) {
		this.endedById = endedById;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public double getDiscount() {
		return this.discount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
