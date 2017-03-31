package it.percassi.perparser.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

public class FileUploaderValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return MultipartFile.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		final MultipartFile file =(MultipartFile) target;
		
		if(file.isEmpty()){
			errors.reject("Empty file");
		}
		
	}

}
