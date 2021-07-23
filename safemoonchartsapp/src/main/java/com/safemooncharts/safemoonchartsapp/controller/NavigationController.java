package com.safemooncharts.safemoonchartsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.safemooncharts.safemoonchartsapp.model.Account;

@Controller
public class NavigationController {
	
	@Autowired
	private ApplicationContext context;
	
	@RequestMapping({"/", "/goToIndex"})
	public String goToIndex() {
		return "index";
	}
	
	@RequestMapping("/goToSignUp")
	public String goToSignUp(Model model) {
		model.addAttribute("account", context.getBean("account", Account.class));
		return "signup";
	}
	
	@RequestMapping("/goToLogin")
	public String goToLogin() {
		return "login";
	}
	
	@RequestMapping("/goToWallet")
	public String goToWallet() {
		return "wallet";
	}
	
	@RequestMapping("/goToSafemoon")
	public String goToSafemoon() {
		return "safemoon";
	}
	
}
