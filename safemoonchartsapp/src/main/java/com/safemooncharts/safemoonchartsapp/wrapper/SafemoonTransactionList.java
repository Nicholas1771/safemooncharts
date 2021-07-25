package com.safemooncharts.safemoonchartsapp.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safemooncharts.safemoonchartsapp.model.SafemoonTransaction;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SafemoonTransactionList {

	private String status;
	private String message;
	private List<SafemoonTransaction> result;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<SafemoonTransaction> getResult() {
		return result;
	}

	public void setResult(List<SafemoonTransaction> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "SafemoonTransactionList [status=" + status + ", message=" + message + "]";
	}

}
