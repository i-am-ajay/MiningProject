package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class GeneralData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String category;
	private String description;
	private LocalDate createdOn;
	private int createdBy;
	private LocalDate endDate;
	private int endedBy;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
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
	public int getId() {
		return id;
	}
}
