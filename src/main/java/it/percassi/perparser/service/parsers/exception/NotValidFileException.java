package it.percassi.perparser.service.parsers.exception;

/**
 *
 * @author Daniele Sperto
 */
public class NotValidFileException extends Exception{

	public final static String MSG_PREABLE = "Not valid file: ";
	
	public NotValidFileException() {
	}

	public NotValidFileException(String message) {
		super(MSG_PREABLE+message);
	}

	public NotValidFileException(String message, Throwable cause) {
		super(MSG_PREABLE+message, cause);
	}

	public NotValidFileException(Throwable cause) {
		super(cause);
	}

	public NotValidFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(MSG_PREABLE+message, cause, enableSuppression, writableStackTrace);
	}

}
