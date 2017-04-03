package it.percassi.perparser.utils;

import java.util.List;

import org.springframework.validation.ObjectError;

public class Utils {

	public static String generateErrorMessage(final List<ObjectError> errors) {

		final StringBuilder errorMessage = new StringBuilder();

		for (ObjectError error : errors) {

			errorMessage.append(error.getDefaultMessage());
		}

		return errorMessage.toString();

	}
}
