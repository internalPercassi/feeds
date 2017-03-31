package it.percassi.perparser.controller.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.percassi.perparser.controller.model.AllDocumentsControllerRequest;

public class DocumentsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return AllDocumentsControllerRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		final AllDocumentsControllerRequest request = (AllDocumentsControllerRequest) target;

		final String sortField = request.getSortField();
		final String sortType = request.getSortType();
		final String length = request.getLength();
		if (StringUtils.isNotBlank(sortField) && !StringUtils.isNumeric(sortField)) {

			errors.rejectValue("sortField", "is not numeric");
		}
		
		if (StringUtils.isNotBlank(sortType) && !StringUtils.isNumeric(sortType)) {

			errors.rejectValue("sortType", "is not numeric");
		}
		
		if (StringUtils.isNotBlank(length) && !StringUtils.isNumeric(length)) {

			errors.rejectValue("length", "is not numeric");
		}
		

	}

}
