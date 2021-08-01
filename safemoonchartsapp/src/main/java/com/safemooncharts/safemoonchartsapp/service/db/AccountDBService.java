package com.safemooncharts.safemoonchartsapp.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safemooncharts.safemoonchartsapp.exception.AccountNotFoundException;
import com.safemooncharts.safemoonchartsapp.model.Account;
import com.safemooncharts.safemoonchartsapp.repository.AccountRepository;

@Service
public class AccountDBService {

	@Autowired
	private AccountRepository accountRepository;

	public Account register(Account account) {
		return accountRepository.save(account);
	}

	public String login (String email, String password) {
		Optional<Account> account = accountRepository.findByEmailIgnoreCase(email);
		if (account.isPresent()) {
			if (account.get().getPassword().equals(password)) {
				return "success";
			} else {
				return "Incorrect password";
			}
		} else {
			return "Account does not exist";
		}
	}

	public Account getAccount(String email) {
		Optional<Account> account = accountRepository.findByEmailIgnoreCase(email);
		if (account.isPresent()) {
			return account.get();
		} else {
			throw new AccountNotFoundException("Account with email " + email + " not found"); 
		}
	}

	public List<Account> getAccounts() {
		return accountRepository.findAll();
	}
}
