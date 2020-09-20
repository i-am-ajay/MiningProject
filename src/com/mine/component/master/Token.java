package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="current_month")
	private String currentMonth;
	
	@Column(name="short_month")
	private String shortMonth;
	
	private int counter;

	public String getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getId() {
		return id;
	}

	public String getShortMonth() {
		return shortMonth;
	}

	public void setShortMonth(String shortMonth) {
		this.shortMonth = shortMonth;
	}
	
}
