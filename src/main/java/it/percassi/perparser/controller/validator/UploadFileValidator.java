package it.percassi.perparser.controller.validator;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import it.percassi.perparser.controller.exception.handler.ControllerHandlerAdvice;
import it.percassi.perparser.controller.request.UploadFileControllerRequest;

public class UploadFileValidator implements Validator {

	private final static Logger LOG = LogManager.getLogger(ControllerHandlerAdvice.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UploadFileControllerRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		final UploadFileControllerRequest req = (UploadFileControllerRequest) target;

		final MultipartFile uploadFile = req.getUploadedFile();
		final String fileType = req.getFileType();
	

		if (Objects.isNull(uploadFile)) {
			String errMsg = "File is mandatory";
			LOG.error(errMsg);
			errors.rejectValue("uploadedFile", "file not present", new Object[] { "'uploadFile'" },errMsg);
		}

		if (StringUtils.isBlank(fileType)) {
			String errMsg = "A filter must be selected";
			LOG.error(errMsg);
			errors.rejectValue("fileType", "empty filter", new Object[] { "'uploadFile'" },errMsg);
		}

		if (!Objects.isNull(uploadFile) && uploadFile.isEmpty()) {
			String errMsg = "Uploaded file is empty";
			LOG.error(errMsg);
			errors.rejectValue("uploadedFile", "empty file", new Object[] { "'uploadFile'" }, errMsg);
		}

	}

}
