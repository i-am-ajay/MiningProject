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

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int clientId;
	
	private String name;
	
	// For owner it will be discount, for contractor it will be commission and won't be deducted 
	// from vehicle rate.
	@Column(columnDefinition="double(10,2) default 0.0")
	private double discount;
	
	private String clientAddress;
	private String clientContact;
	private LocalDate creationDate;
	private Integer createdBy;
	private LocalDate endDate;
	private Integer endedBy;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="type_id")
	private GeneralData clientType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id")
	private Company company;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientContact() {
		return clientContact;
	}

	public void setClientContact(String clientContact) {
		this.clientContact = clientContact;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getEndedBy() {
		return endedBy;
	}

	public void setEndedBy(int endedBy) {
		this.endedBy = endedBy;
	}

	public GeneralData getClientType() {
		return clientType;
	}

	public void setClientType(GeneralData clientType) {
		this.clientType = clientType;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getClientId() {
		return clientId;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public double getDiscount() {
		return discount;
	}
}
