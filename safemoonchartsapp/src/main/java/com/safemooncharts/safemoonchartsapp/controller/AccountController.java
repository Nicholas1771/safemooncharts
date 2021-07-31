package com.safemooncharts.safemoonchartsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safemooncharts.safemoonchartsapp.model.Account;
import com.safemooncharts.safemoonchartsapp.service.db.AccountDBService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
	
	@GetMapping("/accounts")
    public List<Account> getAccounts() {
        return (List<Account>) accountDBService.findAll();
    }

    @PostMapping("/accounts")
    void addAccount(@RequestBody Account account, @RequestBody String confirmPassword) {
    	accountDBService.register(account, confirmPassword);
    }
}
