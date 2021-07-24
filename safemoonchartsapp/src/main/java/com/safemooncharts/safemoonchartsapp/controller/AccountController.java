package com.safemooncharts.safemoonchartsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safemooncharts.safemoonchartsapp.model.Account;
import com.safemooncharts.safemoonchartsapp.service.db.AccountDBService;

@Controller
public class AccountController {

	@Autowired
	private AccountDBService accountDBService;
	
	@RequestMapping("/registerAccount")
	public String registerAccount(@RequestParam String confirmPassword, @ModelAttribute Account account, Model model) {
		accountDBService.register(account, confirmPassword);
		return "index";
	}
	
	@RequestMapping("/loginAccount")
	public String loginAccount(@RequestParam String email, @RequestParam String password, Model model) {
		if (accountDBService.isCorrectCredentials(email, password)) {
			Account account = accountDBService.getAccount(email);
			model.addAttribute("account", account);
		}
		return "index";
	}
}
