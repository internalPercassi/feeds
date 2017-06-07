package it.percassi.perparser.controller;

import java.util.List;
import org.springframework.validation.ObjectError;

/**
 *
 * @author spedan
 */
public class ControllerUtils {

    /**
     * Simple method to generate an error message given a List of
     * {@link ObjectError}
     *
     * @param errors A list of {@link ObjectError}
     * @return An error string
     */
    public static String generateErrorMessage(final List<ObjectError> errors) {
        final StringBuffer errorMessage = new StringBuffer();
        for (ObjectError error : errors) {
            errorMessage.append(error.getDefaultMessage());
        }
        return errorMessage.toString();

    }
}
