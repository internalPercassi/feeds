package it.percassi.perparser.controller.exception.handler;

import it.percassi.perparser.exception.PerParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages="it.percassi.perparser.controller")
public class ControllerHandlerAdvice {

	private final static Logger LOG = LogManager.getLogger(ControllerHandlerAdvice.class);
        private final static String DEF_ERR_MSG = "A technical error occurred";
        
	
	@ExceptionHandler(PerParserException.class)
	public ResponseEntity<String> perParserExceptionHandler(Exception ex) {
		LOG.error("Exception occured", ex);
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> genericExceptionHandler(Exception ex) {
		LOG.error("Exception occured", ex);
		return new ResponseEntity<String>(DEF_ERR_MSG, HttpStatus.BAD_REQUEST);

	}
}
