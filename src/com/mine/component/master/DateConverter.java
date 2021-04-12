package com.mine.component.master;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

//@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, Date>{

	@Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
		Date date = null;
		if(localDate != null) {
			System.out.println("Local Date");
			date = Date.valueOf(localDate);
		}
		return date;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date date) {
		LocalDate localDate = null;
		if(date != null) {
			System.out.println("Sql Date");
			localDate = date.toLocalDate();
		}
		return localDate;
	}
	
}
