package com.mine.utilities;

import java.util.HashMap;
import java.util.Map;

public class MonthName {
	static Map<String,String> monthMap = new HashMap<>();
	static {
		monthMap.put("Jan", "JANUARY");
		monthMap.put("Feb", "FEBRUARY");
		monthMap.put("Mar", "MARCH");
		monthMap.put("Apr", "APRIL");
		monthMap.put("May", "MAY");
		monthMap.put("Jun", "JUNE");
		monthMap.put("Jul", "JULY");
		monthMap.put("Aug", "AUGUST");
		monthMap.put("Sep", "SEPTEMBER");
		monthMap.put("Oct", "OCTOBER");
		monthMap.put("Nov", "NOVEMBER");
		monthMap.put("Dec", "DECEMBER");
	}
	
	public static String getMonth(String key) {
		return monthMap.get(key);
	}
}
