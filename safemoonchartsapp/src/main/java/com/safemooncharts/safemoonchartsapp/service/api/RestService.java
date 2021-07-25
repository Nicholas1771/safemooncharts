package com.safemooncharts.safemoonchartsapp.service.api;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.safemooncharts.safemoonchartsapp.enums.TransactionType;
import com.safemooncharts.safemoonchartsapp.model.SafemoonTransaction;
import com.safemooncharts.safemoonchartsapp.wrapper.SafemoonTransactionList;
import com.sun.org.apache.xml.internal.utils.ObjectPool;

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
		String url = BSC_BASE + "module=account&action=tokenbalance&contractaddress=" + SAFEMOON_CONTRACT_ADDRESS
				+ "&address=" + address + "&tag=latest&apikey=" + BSC_API_KEY;
		HashMap<String, String> resultMap = restTemplate.getForObject(url, new HashMap<>().getClass());
		String resultString = resultMap.get("result");
		if (!resultString.equals("0")) {
			String balanceString = resultString.substring(0, resultString.length() - 9) + "."
				+ resultString.substring(resultString.length() - 8);
			BigDecimal balance = new BigDecimal(balanceString);
			DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
			return decimalFormat.format(balance);
		} else {
			return "0.00";
		}
	}

	public String getPSafemoonBalanceByAddress(String address) {
		String url = ETH_BASE + "module=account&action=tokenbalance&contractaddress=" + P_SAFEMOON_CONTRACT_ADDRESS
				+ "&address=" + address + "&tag=latest&apikey=" + ETH_API_KEY;
		HashMap<String, String> resultMap = restTemplate.getForObject(url, new HashMap<>().getClass());
		String resultString = resultMap.get("result");
		if (!resultString.equals("0")) {
			String balanceString = resultString.substring(0, resultString.length() - 18) + "." 
								 + resultString.substring(resultString.length() - 17);
			BigDecimal balance = new BigDecimal(balanceString);
			DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
			return decimalFormat.format(balance);
		} else {
			return "0.00";
		}
	}

	public SafemoonTransactionList getSafemoonTransactionsByAddress(String address) {
		String url = BSC_BASE + "module=account&action=tokentx&contractaddress=" + SAFEMOON_CONTRACT_ADDRESS
				+ "&address=" + address + "&apikey=" + BSC_API_KEY;
		SafemoonTransactionList list = restTemplate.getForObject(url, SafemoonTransactionList.class);
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		list.getResult().forEach((transaction) -> {
			if (transaction.getFrom().equalsIgnoreCase(address)) {
				transaction.setType(TransactionType.SENT);
				String value = transaction.getValue();
				double valueDouble = 0.0;
				try {
					valueDouble = decimalFormat.parse(value).doubleValue();
				} catch (ParseException e) {
				}
				double newValueDouble = valueDouble / 0.9;
				String newValue = decimalFormat.format(newValueDouble);
				transaction.setValueFormatted(newValue);
			} else if (transaction.getTo().equalsIgnoreCase(address)) {
				transaction.setType(TransactionType.RECIEVED);
			}
		});
		return list;
	}
}
