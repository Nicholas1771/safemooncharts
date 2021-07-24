package com.safemooncharts.safemoonchartsapp.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
