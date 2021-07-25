package com.safemooncharts.safemoonchartsapp.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safemooncharts.safemoonchartsapp.enums.TransactionType;
import com.safemooncharts.safemoonchartsapp.model.SafemoonTransaction;
import com.safemooncharts.safemoonchartsapp.service.api.RestService;

@Controller
public class AddressController {

	@Autowired
	private RestService restService;
	
	@RequestMapping("/SubmitAddress")
	public String submitAddress(@RequestParam String address, Model model) {
		String safemoonBalance = restService.getSafemoonBalanceByAddress(address);
		String pSafemoonBalance = restService.getPSafemoonBalanceByAddress(address);
		List<SafemoonTransaction> safemoonTransactions = restService.getSafemoonTransactionsByAddress(address).getResult();
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
		model.addAttribute("safemoonTransactions", safemoonTransactions);
		model.addAttribute("reflections", getTotalReflections(safemoonTransactions, safemoonBalance));
		return "wallet"; 
	}
	
	private String getTotalReflections(List<SafemoonTransaction> safemoonTransactions, String balance) {
		double receivedSentDifference = 0.0;
		double totalReflections = 0.0;
		double sBalance = 0.0;
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		try {
			sBalance = decimalFormat.parse(balance).doubleValue();
		} catch (ParseException e1) {
		}
		for (int i = 0; i < safemoonTransactions.size(); i++) {
			SafemoonTransaction transaction = safemoonTransactions.get(i);
			double value = 0.0;
			try {
				value = decimalFormat.parse(transaction.getValue()).doubleValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (transaction.getType() == TransactionType.SENT) {
				receivedSentDifference -= value;
			} else if (transaction.getType() == TransactionType.RECIEVED) {
				receivedSentDifference += value;
			}
		}
		
		return decimalFormat.format(sBalance - receivedSentDifference);
	}
}
