package it.percassi.perparser.service;

public class BaseServiceResponse {

	public BaseServiceResponse(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	private String message;
	
	private int statusCode;
	

	public String getMessage() {
		return message;
	}
	public int getStatusCode() {
		return statusCode;
	}
}
