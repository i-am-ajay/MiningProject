package com.mine.component.transaction;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
public class Ledger {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="entry_date", insertable=false, updatable=false)
	@Generated(GenerationTime.INSERT)
	protected LocalDateTime entryDate;
	
	protected String type;
	
	// target will be giving
	protected String target;
	
	// source will get the money
	protected String source;
	
	@Column(name="debit_amount")
	protected double debitAmount;
	
	@Column(name="credit_amount")
	protected double creditAmount;
	
	@Column(name="cashbook_link")
	protected CashBookRecord cashbookLinking;
	
	@Column(name="creditbook_link")
	protected CreditRecord creditRecordLinking;
	

	public LocalDateTime getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDateTime entryDate) {
		this.entryDate = entryDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSource() {
		return source;
	}
	// Source will get the money
	public void setSource(String source) {
		this.source = source;
	}

	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmout) {
		this.debitAmount = debitAmout;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmout) {
		this.creditAmount = creditAmout;
	}

	public int getId() {
		return id;
	}

	public CashBookRecord getCashbookLinking() {
		return cashbookLinking;
	}

	public void setCashbookLinking(CashBookRecord cashbookLinking) {
		this.cashbookLinking = cashbookLinking;
	}

	public CreditRecord getCreditRecordLinking() {
		return creditRecordLinking;
	}

	public void setCreditRecordLinking(CreditRecord creditRecordLinking) {
		this.creditRecordLinking = creditRecordLinking;
	}
}
