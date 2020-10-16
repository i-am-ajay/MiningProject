package com.mine.component.transaction;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.mine.component.master.Client;
import com.mine.component.master.Vehicle;

@Entity
@Table(name="cashbook_table")
public class CashBookRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="client")
	private Client party;
	
	@JoinColumn(name="sales_id")
	@OneToOne(fetch=FetchType.EAGER)
	private SupplyDetails sales;

	private double amount;
	
	// sales/ expanse / deposite
	private String type;
	
	// income / expanse
	private String category;
	
	@Generated(GenerationTime.INSERT)
	@Column(name = "creation_date")
	private LocalDateTime entryDate;
	
	private boolean status;
	
	// links for ledger and credit book
	
	@OneToOne(mappedBy = "cashbookDepositeLink")
	CreditRecord creditRecord;
	
	@OneToOne(mappedBy = "cashbookLinking")
	Ledger ledger;

	public Client getParty() {
		return party;
	}

	public void setParty(Client party) {
		this.party = party;
	}

	public SupplyDetails getSales() {
		return sales;
	}

	public void setSales(SupplyDetails sales) {
		this.sales = sales;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDateTime entryDate) {
		this.entryDate = entryDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public CreditRecord getCreditRecord() {
		return creditRecord;
	}

	public void setCreditRecord(CreditRecord creditRecord) {
		this.creditRecord = creditRecord;
	}

	public Ledger getLedger() {
		return ledger;
	}

	public void setLedger(Ledger ledger) {
		this.ledger = ledger;
	}
}
