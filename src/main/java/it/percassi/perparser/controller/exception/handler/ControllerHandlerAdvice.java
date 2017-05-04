package it.percassi.perparser.controller.exception.handler;

import it.percassi.perparser.controller.response.BaseControllerResponse;
import it.percassi.perparser.exception.NotValidFileException;
import it.percassi.perparser.exception.PerParserException;
import java.io.IOException;

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
		final BaseControllerResponse res = new BaseControllerResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(res.getMessage(), res.getErrorCode());

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> genericExceptionHandler(Exception ex) {
		LOG.error("Exception occured", ex);
		final BaseControllerResponse res = new BaseControllerResponse(DEF_ERR_MSG, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(res.getMessage(), res.getErrorCode());

	}
}
