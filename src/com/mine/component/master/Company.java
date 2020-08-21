package com.mine.component.master;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	@Column
	private String name;
	@Column
	private String location;
	@Column
	private String companyContact;
	@Column
	private String companyEmail;
	
	private LocalDate creationDate;
	private int createdBy;
	private LocalDate endDate;
	private int endedBy;
	
	@OneToMany(mappedBy="vehicle_id")
	private List<Vehicle> vehicle = new ArrayList<>();
	
	@OneToMany(mappedBy="client_id")
	private List<Client> clientList = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String clientContact) {
		this.companyContact = clientContact;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String clientEmail) {
		this.companyEmail = clientEmail;
	}

	public List<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(List<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	public int getId() {
		return id;
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
	
	
}
