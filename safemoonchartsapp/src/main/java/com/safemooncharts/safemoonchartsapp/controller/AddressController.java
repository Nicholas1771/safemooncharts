package com.safemooncharts.safemoonchartsapp.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safemooncharts.safemoonchartsapp.service.api.RestService;

@Controller
public class AddressController {

	@Autowired
	private RestService restService;
	
	@RequestMapping("/SubmitAddress")
	public String submitAddress(@RequestParam String address, Model model) {
		String safemoonBalance = restService.getSafemoonBalanceByAddress(address);
		String transactions = restService.getNormalTransactionsByAddress(address);
		model.addAttribute("address", address);
		model.addAttribute("safemoonBalance", safemoonBalance);
		model.addAttribute("transactions", transactions);
		return "wallet";
	}
	
}
