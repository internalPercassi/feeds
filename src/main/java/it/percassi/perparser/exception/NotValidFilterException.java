package it.percassi.perparser.exception;

/**
 *
 * @author Daniele Sperto
 */
public class NotValidFilterException extends Exception {

	public final static String MSG_PREABLE = "Not valid filter: ";

	public NotValidFilterException() {
	}

	public NotValidFilterException(String message) {
		super(MSG_PREABLE + message);
	}

	public NotValidFilterException(String message, Throwable cause) {
		super(MSG_PREABLE + message, cause);
	}

	public NotValidFilterException(Throwable cause) {
		super(cause);
	}

	public NotValidFilterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(MSG_PREABLE + message, cause, enableSuppression, writableStackTrace);
	}
}
