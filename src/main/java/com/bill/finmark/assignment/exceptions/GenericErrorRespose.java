package com.bill.finmark.assignment.exceptions;

import java.util.Date;
import java.util.List;

public class GenericErrorRespose {

	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;
	private List<String> details;

	public GenericErrorRespose(int statusCode, Date timestamp, String message, List<String> details,
			String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.description = description;
	}

	public GenericErrorRespose() {
		
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
