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

	public boolean register(Account account, String confirmPass) {
		boolean passMatch = account.getPassword().equals(confirmPass);
		boolean emailExists = accountRepository.findByEmailIgnoreCase(account.getEmail()).isPresent();
		if (passMatch && !emailExists) {
			accountRepository.save(account);
			System.out.println("Registration successful");
			return true;
		}
		System.out.println("Registration unsuccessful");
		return false;
	}

	public boolean isCorrectCredentials(String email, String password) {
		Optional<Account> account = accountRepository.findByEmailIgnoreCase(email);
		if (account.isPresent()) {
			System.out.println("Account exists");
			if (account.get().getPassword().equals(password)) {
				System.out.println("Correct password");
				return true;
			} else {
				System.out.println("Incorrect password");
				return false;
			}
		} else {
			System.out.println("Account does not exist");
			return false;
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

	public List<Account> findAll() {
		return accountRepository.findAll();
	}
}
