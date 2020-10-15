package com.mine.utilities;

public class TypesAndCategories {
	String cashbookType;
	String cashbookCategory;
	
	String creditType;
	String creditCategory;
	
	String ledgerType;

	public TypesAndCategories(String cashbookType, String cashbookCategory, String creditType, String creditCategory,
			String ledgerType) {
		super();
		this.cashbookType = cashbookType;
		this.cashbookCategory = cashbookCategory;
		this.creditType = creditType;
		this.creditCategory = creditCategory;
		this.ledgerType = ledgerType;
	}

	public String getCashbookType() {
		return cashbookType;
	}

	public void setCashbookType(String cashbookType) {
		this.cashbookType = cashbookType;
	}

	public String getCashbookCategory() {
		return cashbookCategory;
	}

	public void setCashbookCategory(String cashbookCategory) {
		this.cashbookCategory = cashbookCategory;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getCreditCategory() {
		return creditCategory;
	}

	public void setCreditCategory(String creditCategory) {
		this.creditCategory = creditCategory;
	}

	public String getLedgerType() {
		return ledgerType;
	}

	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
	}
}
