package com.mine.component.master;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Rate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int rateId;
	
	private String tyreType;
	
	private String quantity;
	
	private String materialType;
	
	private String truckType;
	
	private double rate;
	
	private LocalDate createdBy;
	
	private LocalDate endDate;
	
	private int createdById;
	
	private int endedById;
}
