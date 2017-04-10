package it.percassi.perparser.controller.validator;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.percassi.perparser.controller.request.GetDocumentsRequest;

public class GetDocumentsRequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return GetDocumentsRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		final GetDocumentsRequest request = (GetDocumentsRequest) target;		
		if (Objects.isNull(request)) {
			errors.rejectValue("GetDocumentsRequest", "request is null", new Object[] { "'getDocumentsRequest'" }, "Request is null");
		}

		if(StringUtils.isBlank(request.getCollectionName())){
			errors.rejectValue("collectionName", "collectionName is null", new Object[] { "'collectionName'" }, "Collection Name must be not null");

		}

	}

}
