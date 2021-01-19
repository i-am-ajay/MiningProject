package com.mine.component.transaction;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.mine.component.master.User;

@Entity
public class Ledger {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	
	@Column(name="entry_date")
	//@Generated(GenerationTime.INSERT)
	protected LocalDateTime entryDate;
	
	protected String description;
	
	// target will be giving
	protected String account;
	
	@Column(name="debit_amount")
	protected double debitAmount;
	
	@Column(name="credit_amount")
	protected double creditAmount;
	
	@Column(name="remarks")
	protected String remarks;
	
	@ManyToOne
	@JoinColumn(name="created_by")
	protected User createdBy;
	
	@OneToOne
	@JoinColumn(name="parent_link")
	protected Ledger parentEntryLink;
	
	@OneToOne(mappedBy="parentEntryLink")
	protected Ledger childLink;
	
	@OneToOne
	@JoinColumn(name="sales_id")
	protected SupplyDetails salesLink;
	
	protected boolean status;
	
	public LocalDateTime getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDateTime entryDate) {
		this.entryDate = entryDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String target) {
		this.account = target;
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

	public SupplyDetails getSalesLink() {
		return salesLink;
	}

	public void setSalesLink(SupplyDetails salesLink) {
		this.salesLink = salesLink;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return this.status;
	}

	public Ledger getParentEntryLink() {
		return parentEntryLink;
	}

	public void setParentEntryLink(Ledger parentEntryLink) {
		this.parentEntryLink = parentEntryLink;
	}

	public Ledger getChildLink() {
		return childLink;
	}

	public void setChildLink(Ledger childLink) {
		this.childLink = childLink;
	}
}
