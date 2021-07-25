package com.safemooncharts.safemoonchartsapp.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safemooncharts.safemoonchartsapp.enums.TransactionType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SafemoonTransaction {

	private String value;
	private String from;
	private String to;
	private TransactionType type;
	private LocalDateTime timeStamp;

	public SafemoonTransaction() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		String valueString = value.substring(0, value.length() - 9) + "."
				+ value.substring(value.length() - 8);
		BigDecimal bigDecimalValue = new BigDecimal(valueString);
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		this.value = decimalFormat.format(bigDecimalValue);
	}
	
	public void setValueFormatted(String value) {
		this.value = value;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = Instant.ofEpochSecond(Long.parseLong(timeStamp)).atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "SafemoonTransaction [value=" + value + ", from=" + from + ", to=" + to + ", type=" + type
				+ ", timeStamp=" + timeStamp + "]";
	}

}
