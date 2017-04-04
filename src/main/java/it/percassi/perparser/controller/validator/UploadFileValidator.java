package it.percassi.perparser.controller.validator;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import it.percassi.perparser.controller.request.UploadFileControllerRequest;

public class UploadFileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UploadFileControllerRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		final UploadFileControllerRequest req = (UploadFileControllerRequest) target;

		final MultipartFile uploadFile = req.getMultipartFile();
		final String fileType = req.getFileType();

		if (Objects.isNull(uploadFile)) {

			errors.rejectValue("uploadedFile", "file not present", new Object[] { "'uploadFile'" }, "File is mandatory");
		}

		if (StringUtils.isBlank(fileType)) {
			errors.rejectValue("fileType", "empty filter", new Object[] { "'uploadFile'" },
					"A filter must be selected");
		}
		
		if(!Objects.isNull(uploadFile) && uploadFile.isEmpty()){
			errors.rejectValue("uploadedFile", "empty file", new Object[] { "'uploadFile'" },
					"Uploaded file is empty");
		}

	}

}
