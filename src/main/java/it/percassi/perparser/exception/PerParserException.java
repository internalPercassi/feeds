/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.percassi.perparser.exception;

/**
 *
 * @author Daniele Sperto
 */
public class PerParserException extends Exception{
 
    public PerParserException() {
	}

	public PerParserException(String message) {
		super(message);
	}

	public PerParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public PerParserException(Throwable cause) {
		super(cause);
	}

	public PerParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
