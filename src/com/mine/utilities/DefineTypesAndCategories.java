package com.mine.utilities;

public class DefineTypesAndCategories {
	// For cash sale
	static public TypesAndCategories cashSale = new TypesAndCategories("Sale", "Income",null, null, "Cash Sale"); 
	
	static public TypesAndCategories deposite = new TypesAndCategories("Deposite","Income","Client Deposite", "Deposite", "Client Deposite");
	
	static public TypesAndCategories otherDeposite = new TypesAndCategories("Deposite","Income","Other Deposite", "Deposite", "Other Deposite");
	
	
	static public TypesAndCategories cashExpenseOffice = new TypesAndCategories("Expense Office", "Expense", null, null, "Expense Office");
	
	static public TypesAndCategories cashExpenseSanchalan = new TypesAndCategories("Expense Sanchalan","Expense", "Deposite","Expense Sanchalan Paid","Expense Sanchalan");
	
	static public TypesAndCategories cashExpenseComission = new TypesAndCategories("Expense Comission","Expense", "Deposite","Expense Comission Paid","Expense Comission");
	
	static public TypesAndCategories cashExpenseDriverReturn = new TypesAndCategories("Expense Driver Return","Expense", null,null,"Expense Driver Return");
	
	static public TypesAndCategories cashExpenseOther = new TypesAndCategories("Expense Other","Expense", null,null,"Expense Other");
	
	
	static public TypesAndCategories creditSale = new TypesAndCategories(null, null, "Credit Sale", "Asset", "Credit Sale");
	
	static public TypesAndCategories creditExpenseComission = new TypesAndCategories(null,null,"Expense Comission","Liability","Expense Comission");
	
	static public TypesAndCategories creditExpenseSanchalan = new TypesAndCategories(null,null,"Expense Sanchalan","Liability","Expense Sanchalan");
	
	static public TypesAndCategories creditExpenseOther = new TypesAndCategories(null,null,"Expense Other","Liability","Expense Other");
	
	
	
}
