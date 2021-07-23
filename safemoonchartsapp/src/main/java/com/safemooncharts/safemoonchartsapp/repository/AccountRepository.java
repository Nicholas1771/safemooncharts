package com.safemooncharts.safemoonchartsapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safemooncharts.safemoonchartsapp.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	public Optional<Account> findByEmailIgnoreCase(String email);
}
