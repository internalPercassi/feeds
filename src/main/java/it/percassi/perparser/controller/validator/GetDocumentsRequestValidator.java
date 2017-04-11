package it.percassi.perparser.controller.validator;

import it.percassi.perparser.controller.exception.handler.ControllerHandlerAdvice;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.percassi.perparser.controller.request.GetDocumentsRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetDocumentsRequestValidator implements Validator {

	private final static Logger LOG = LogManager.getLogger(ControllerHandlerAdvice.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return GetDocumentsRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		final GetDocumentsRequest request = (GetDocumentsRequest) target;		
		if (Objects.isNull(request)) {
			String errMsg = "Request is null";
			LOG.error(errMsg);
			errors.rejectValue("GetDocumentsRequest", "request is null", new Object[] { "'getDocumentsRequest'" }, errMsg);
		}

		if(StringUtils.isBlank(request.getCollectionName())){
			String errMsg = "Collection Name must be not null";
			LOG.error(errMsg);
			errors.rejectValue("collectionName", "collectionName is null", new Object[] { "'collectionName'" }, errMsg);
		}

	}

}
