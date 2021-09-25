package com.ajay.tests;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mine.service.FuelDistributionService;

public class TestClass {
	@Test
	public void testMachineList() {
		ApplicationContext context = new FileSystemXmlApplicationContext("/WebContent/WEB-INF/mainConfig.xml");
		FuelDistributionService service = context.getBean("fuel-dist",FuelDistributionService.class);
	}
}
