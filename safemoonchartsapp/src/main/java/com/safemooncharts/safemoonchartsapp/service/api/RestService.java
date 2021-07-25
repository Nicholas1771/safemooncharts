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
	
	private final String BSC_BASE = "https://api.bscscan.com/api?";
	private final String ETH_BASE = "https://api.etherscan.io/api?";
	
	private final String SAFEMOON_CONTRACT_ADDRESS = "0x8076c74c5e3f5852037f31ff0093eeb8c8add8d3";
	private final String P_SAFEMOON_CONTRACT_ADDRESS = "0x16631e53c20fd2670027c6d53efe2642929b285c";
	
	private final String BSC_API_KEY = "36QM78IWHC999YBCCDX3KGHDKJ5348GMQK";
	private final String ETH_API_KEY = "VIR6IDCHUDTDV3IHJ9A3ACR9HUSKXPXQTE";
	
	public RestService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public String getSafemoonBalanceByAddress(String address) {
		String url = BSC_BASE + "module=account&action=tokenbalance&contractaddress=" + SAFEMOON_CONTRACT_ADDRESS + "&address=" + address + "&tag=latest&apikey=" + BSC_API_KEY;
		HashMap<String, String> resultMap = restTemplate.getForObject(url, new HashMap<>().getClass());
		String resultString = resultMap.get("result");
		String balanceString = resultString.substring(0, resultString.length() - 9) + "." + resultString.substring(resultString.length() - 8);
		BigDecimal balance = new BigDecimal(balanceString);
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		return decimalFormat.format(balance);
	}
	
	public String getPSafemoonBalanceByAddress(String address) {
		String url = ETH_BASE + "module=account&action=tokenbalance&contractaddress=" + P_SAFEMOON_CONTRACT_ADDRESS + "&address=" + address + "&tag=latest&apikey=" + ETH_API_KEY;
		HashMap<String, String> resultMap = restTemplate.getForObject(url, new HashMap<>().getClass());
		String resultString = resultMap.get("result");
		String balanceString = resultString.substring(0, resultString.length() - 18) + "." + resultString.substring(resultString.length() - 8);
		BigDecimal balance = new BigDecimal(balanceString);
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		return decimalFormat.format(balance);
	}
	
	public String getNormalTransactionsByAddress(String address) {
		String url = BSC_BASE + "module=account&action=txlist&address=" + address + "&tag=latest&apikey=" + BSC_API_KEY;
		return this.restTemplate.getForObject(url, String.class);		
	}
}
