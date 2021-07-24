package com.mine.utilities;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePropertyEditor extends PropertyEditorSupport{
	public void setAsText(String param) {
		LocalDate date = LocalDate.parse(param,DateTimeFormatter.ofPattern("y-M-d"));
		setValue(date);
	}
}
