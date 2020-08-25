package com.mine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mine.component.master.Client;
import com.mine.component.master.Company;

@Controller
public class MasterController {
	Company company = new Company();
	// Creates a new Client
	@RequestMapping("client_creation")
	public String createClient(Model model) {
		Client client = new Client();
		client.setCompany(company);
		model.addAttribute("client",client);
		return "client";
	}
	
	public String createCompany(Model model) {
		Company company = new Company();
		model.addAttribute("company",company);
		return "client";
	}
}
