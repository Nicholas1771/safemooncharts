package com.safemooncharts.safemoonchartsapp.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safemooncharts.safemoonchartsapp.enums.TransactionType;
import com.safemooncharts.safemoonchartsapp.model.SafemoonTransaction;
import com.safemooncharts.safemoonchartsapp.service.api.RestService;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "https://safemooncharts-frontend.herokuapp.com/"})
public class AddressController {

	@Autowired
	private RestService restService;

	@RequestMapping("/SubmitAddress")
	public String submitAddress(@RequestParam String address, Model model) {
		String safemoonBalance = restService.getSafemoonBalanceByAddress(address);
		String pSafemoonBalance = restService.getPSafemoonBalanceByAddress(address);
		List<SafemoonTransaction> safemoonTransactions = restService.getSafemoonTransactionsByAddress(address)
				.getResult();
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		double sum = 0.0;
		try {
			sum = decimalFormat.parse(safemoonBalance).doubleValue()
					+ decimalFormat.parse(pSafemoonBalance).doubleValue();
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

	@GetMapping("/transactions")
	public List<SafemoonTransaction> getTransactions(String address) {
		List<SafemoonTransaction> safemoonTransactions = restService.getSafemoonTransactionsByAddress(address).getResult();
		return safemoonTransactions;
	}
	
	@GetMapping("/reflections")
	public String getReflections(String address) {
		List<SafemoonTransaction> safemoonTransactions = restService.getSafemoonTransactionsByAddress(address)
				.getResult();
		String safemoonBalance = restService.getSafemoonBalanceByAddress(address);
		String reflections = getTotalReflections(safemoonTransactions, safemoonBalance);
		System.out.println(reflections);
		return reflections;
	}
	
	@GetMapping("/balances")
	public String getBalance(String address, String type) {
		String balance = "0.00";
		if (type.equals("SFM")) {
			balance = restService.getSafemoonBalanceByAddress(address);
		} else if (type.equals("pSFM")) {
			balance = restService.getPSafemoonBalanceByAddress(address);
		} else if (type.equals("all")) {
			String balanceSfm = restService.getSafemoonBalanceByAddress(address);
			String balancePSfm = restService.getPSafemoonBalanceByAddress(address);
			DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
			double sum = 0.0;
			try {
				sum = decimalFormat.parse(balanceSfm).doubleValue()
						+ decimalFormat.parse(balancePSfm).doubleValue();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			balance = decimalFormat.format(BigDecimal.valueOf(sum));
		}
		return balance;
	}
}
