package com.safemooncharts.safemoonchartsapp.service.api;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

	private final RestTemplate restTemplate;
	
	private final String BASE_URL = "https://api.bscscan.com/api?";
	
	private final String SAFEMOON_CONTRACT_ADDRESS = "0x8076c74c5e3f5852037f31ff0093eeb8c8add8d3";
	
	private final String API_KEY = "36QM78IWHC999YBCCDX3KGHDKJ5348GMQK";
	
	public RestService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public String getBnbBalanceByAddress(String address) {
		String url = BASE_URL + "module=account&action=balance&address=" + address + "&tag=latest&apikey=" + API_KEY;
		return this.restTemplate.getForObject(url, String.class);
	}
	
	public String getSafemoonBalanceByAddress(String address) {
		String url = BASE_URL + "module=account&action=tokenbalance&contractaddress=" + SAFEMOON_CONTRACT_ADDRESS + "&address=" + address + "&tag=latest&apikey=" + API_KEY;
		HashMap<String, String> result = restTemplate.getForObject(url, new HashMap<>().getClass());
		String resultString = result.get("result");
		String balanceString = resultString.substring(0, resultString.length() - 9) + "." + resultString.substring(resultString.length() - 8);
		BigDecimal balance = new BigDecimal(balanceString);
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		return decimalFormat.format(balance);
	}
	
	public String getNormalTransactionsByAddress(String address) {
		String url = BASE_URL + "module=account&action=txlist&address=" + address + "&tag=latest&apikey=" + API_KEY;
		return this.restTemplate.getForObject(url, String.class);		
	}
}
