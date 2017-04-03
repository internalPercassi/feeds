package it.percassi.perparser.controller.response;

import org.springframework.http.HttpStatus;

public class BaseControllerResponse {
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public HttpStatus getErrorCode() {
		return statusCode;
	}

	private HttpStatus statusCode;
	
	public BaseControllerResponse(String message,HttpStatus statusCode){
		this.message = message;
		this.statusCode = statusCode;
	}

}
