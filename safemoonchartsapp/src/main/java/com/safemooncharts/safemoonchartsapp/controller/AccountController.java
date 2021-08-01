package com.safemooncharts.safemoonchartsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safemooncharts.safemoonchartsapp.model.Account;
import com.safemooncharts.safemoonchartsapp.service.db.AccountDBService;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200", "http://www.safemooncharts.ca/"})
public class AccountController {

	@Autowired
	private AccountDBService accountDBService;
	
	@PostMapping("/accounts/authenticate")
	public String login(@RequestParam String email, @RequestParam String password) {
		System.out.println("login");
		return accountDBService.login(email, password);
	}
	
	@GetMapping("/accounts/{email}")
	public Account getAccount(@PathVariable(value = "email") String email) {
		System.out.println("get account");
		return accountDBService.getAccount(email);
	}
	
	@GetMapping("/accounts")
    public List<Account> getAccounts() {
        return (List<Account>) accountDBService.getAccounts();
    }

    @PostMapping("/accounts")
    public Account register(@RequestBody Account account) {
    	System.out.println("register");
    	return accountDBService.register(account);
    }
}
