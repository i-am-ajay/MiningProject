package com.mine.utilities;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.mine.component.master.Token;
import com.mine.service.MiningService;

public class TokenManager {
	private static TokenManager manager;
	
	private TokenManager() {
	}
	
	public String generateToken(MiningService service) {
		Token token = service.getToken();
		LocalDate date = LocalDate.now();
		String tokenNo = null;
		String monthName = date.getMonth().name();
		if(token == null) {
			token = new Token();
			changeTokenSeries(token,monthName);
		}
		else {
			if(!monthName.equalsIgnoreCase(token.getCurrentMonth())){
				changeTokenSeries(token, monthName);
			}
		}
		token.setCounter(token.getCounter()+1);
		tokenNo = token.getShortMonth()+"-"+ Integer.toString(token.getCounter());
		service.updateToken(token);
		return tokenNo;
	}
	
	public void changeTokenSeries(Token token, String currentMonth) {
		String shortMonth = currentMonth.substring(0,3);
		token.setCurrentMonth(currentMonth);
		token.setShortMonth(shortMonth);
		token.setCounter(0);
	}
	
	public static String giveToken(MiningService service) {
		if(manager == null) {
			manager = new TokenManager();
		}
		return manager.generateToken(service);
	}
}
