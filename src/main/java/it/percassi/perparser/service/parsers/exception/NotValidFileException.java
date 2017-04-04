package it.percassi.perparser.service.parsers.exception;

/**
 *
 * @author Daniele Sperto
 */
public class NotValidFileException extends Exception{

	public NotValidFileException() {
	}

	public NotValidFileException(String message) {
		super(message);
	}

	public NotValidFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidFileException(Throwable cause) {
		super(cause);
	}

	public NotValidFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
