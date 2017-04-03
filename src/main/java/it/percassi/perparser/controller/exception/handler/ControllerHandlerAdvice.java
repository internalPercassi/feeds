package it.percassi.perparser.controller.exception.handler;

import it.percassi.perparser.controller.response.BaseControllerResponse;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice("PerPerserController")
public class ControllerHandlerAdvice {

	private final static Logger LOG = LogManager.getLogger(ControllerHandlerAdvice.class);

	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handlerException(Exception ex) {
		LOG.error("Exception occured", ex);
		final BaseControllerResponse res = new BaseControllerResponse(ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<String>(res.getMessage(), res.getErrorCode());
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<String> numberFormatException(Exception ex) {
		LOG.error("Exception occured", ex);
		final BaseControllerResponse res = new BaseControllerResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(res.getMessage(), res.getErrorCode());

	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> genericExceptionHandler(Exception ex){
		LOG.error("Exception occured", ex);
		final BaseControllerResponse res = new BaseControllerResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<String>(res.getMessage(), res.getErrorCode());
		
		
	}

}
