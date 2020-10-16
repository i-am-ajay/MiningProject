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
@Table(name="credit_table")
public class CreditRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="party")
	private Client client;
	
	@JoinColumn(name="sales_id")
	@OneToOne(fetch=FetchType.EAGER)
	private SupplyDetails sales;
	
	@Column(name="credit_amount")
	private double amount;
	
	@Generated(GenerationTime.INSERT)
	@Column(name = "creation_date")
	private LocalDateTime entryDate;
	
	@Column(name="status")
	private boolean status;
	
	// sales / expanse / expanse comission / expanse sanchalan
	@Column(name="type")
	private String type;
	
	// asset / liability / deposit
	private String category;
	
	@OneToOne
	@JoinColumn(name="cashbook_link_for_deposite")
	private CashBookRecord cashbookDepositeLink;
	
	@OneToOne(mappedBy = "creditRecordLinking")
	@JoinColumn(name="ledger_linking")
	private Ledger ledgerLinking;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public CashBookRecord getCashbookDepositeLink() {
		return cashbookDepositeLink;
	}

	public void setCashbookDepositeLink(CashBookRecord cashbookDepositeLink) {
		this.cashbookDepositeLink = cashbookDepositeLink;
	}

	public Ledger getLedgerLinking() {
		return ledgerLinking;
	}

	public void setLedgerLinking(Ledger ledgerLinking) {
		this.ledgerLinking = ledgerLinking;
	}
}
