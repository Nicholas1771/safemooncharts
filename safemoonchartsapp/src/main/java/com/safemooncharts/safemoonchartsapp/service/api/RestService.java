package com.safemooncharts.safemoonchartsapp.service.api;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

	private final RestTemplate restTemplate;
	
	private final String BASE_URL = "https://api.bscscan.com/api?";
	
	private final String API_KEY = "36QM78IWHC999YBCCDX3KGHDKJ5348GMQK";
	
	public RestService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public String getBnbBalanceByAddress(String address) {
		String url = BASE_URL + "module=account&action=balance&address=" + address + "&tag=latest&apikey=" + API_KEY;
		return this.restTemplate.getForObject(url, String.class);
	}
	
	public String getNormalTransactionsByAddress(String address) {
		String url = BASE_URL + "module=account&action=txlist&address=" + address + "&tag=latest&apikey=" + API_KEY;
		return this.restTemplate.getForObject(url, String.class);		
	}
}
