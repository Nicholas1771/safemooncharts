package com.safemooncharts.safemoonchartsapp.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

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
		String pSafemoonBalance = restService.getPSafemoonBalanceByAddress(address);
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		double sum = 0.0;
		try {
			sum = decimalFormat.parse(safemoonBalance).doubleValue() + decimalFormat.parse(pSafemoonBalance).doubleValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String totalSafemoonBalance = decimalFormat.format(BigDecimal.valueOf(sum));
		model.addAttribute("address", address);
		model.addAttribute("safemoonBalance", safemoonBalance);
		model.addAttribute("pSafemoonBalance", pSafemoonBalance);
		model.addAttribute("totalSafemoonBalance", totalSafemoonBalance);
		return "wallet";
	}	
}
