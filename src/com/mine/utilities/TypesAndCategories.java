package com.mine.utilities;

public class TypesAndCategories {
	String creditLedgerType;
	String debitLedgerType;
	String debitRemarks;
	String creditRemarks;

	public TypesAndCategories(String creditLedgerType, String debitLedgerType) {
		this.creditLedgerType = creditLedgerType;
		this.debitLedgerType = debitLedgerType;
	}
	
	public TypesAndCategories(String creditLedgerType, String creditRemarks, String debitLedgerType, String debitRemarks) {
		this(creditLedgerType,debitLedgerType);
		this.debitRemarks = debitRemarks;
		this.creditRemarks = creditRemarks;
	}

	public String getCreditLedgerType() {
		return creditLedgerType;
	}

	public void setCreditLedgerType(String creditLedgerType) {
		this.creditLedgerType = creditLedgerType;
	}

	public String getDebitLedgerType() {
		return debitLedgerType;
	}

	public void setDebitLedgerType(String debitLedgerType) {
		this.debitLedgerType = debitLedgerType;
	}
	
	
}
