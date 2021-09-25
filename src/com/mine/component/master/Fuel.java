package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Fuel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="fuel_quantity")
	private double qty;
	
	@DateTimeFormat(iso=ISO.DATE)
	@Column(name="entry_date")
	private LocalDate entryDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}
}
