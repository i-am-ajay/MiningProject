package com.mine.utilities;

public class DefineTypesAndCategories {
	// For cash sale
	static public TypesAndCategories cashSale = new TypesAndCategories("Sale", "Income",null, null, "Cash Sale"); 
	
	static public TypesAndCategories deposite = new TypesAndCategories("Deposite","Income","Client Deposite", "Deposite", "Client Deposite");
	
	static public TypesAndCategories otherDeposite = new TypesAndCategories("Deposite","Income","Other Deposite", "Deposite", "Other Deposite");
	
	
	static public TypesAndCategories cashExpenseOffice = new TypesAndCategories("Expense", "Expense", null, null, "Expense");
	
	static public TypesAndCategories cashExpenseSanchalan = new TypesAndCategories("Expense Sanchalan","Expense", "Deposite","Expense Sanchalan Paid","Expense Sanchalan");
	
	static public TypesAndCategories cashExpenseComission = new TypesAndCategories("Expense Comission","Expense", "Deposite","Expense Comission Paid","Expense Comission");
	
	
	static public TypesAndCategories creditSale = new TypesAndCategories(null, null, "Credit Sale", "Asset", "Credit Sale");
	
	static public TypesAndCategories creditExpenseComission = new TypesAndCategories(null,null,"Expense Comission","Liability","Expense Comission");
	
	static public TypesAndCategories creditExpenseSanchalan = new TypesAndCategories(null,null,"Expense Sanchalan","Liability","Expense Sanchalan");
	
	
	
}
