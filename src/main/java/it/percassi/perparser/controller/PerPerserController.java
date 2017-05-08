package it.percassi.perparser.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;

import it.percassi.perparser.controller.request.DeleteDocumentRequest;
import it.percassi.perparser.controller.request.GetDocumentsRequest;
import it.percassi.perparser.controller.request.UploadFileControllerRequest;
import it.percassi.perparser.controller.response.BaseControllerResponse;
import it.percassi.perparser.controller.validator.GetDocumentsRequestValidator;
import it.percassi.perparser.controller.validator.UploadFileValidator;
import it.percassi.perparser.exception.NotValidFileException;
import it.percassi.perparser.exception.NotValidFilterException;
import it.percassi.perparser.facade.CsvFacade;
import it.percassi.perparser.facade.ParserFacade;
import it.percassi.perparser.facade.QueryFacade;
import it.percassi.perparser.utils.PerPortalUtils;

@RestController
public class PerPerserController {

	@Autowired
	@Qualifier("parserFacade")
	private ParserFacade parserFacade;

	@Autowired
	@Qualifier("queryFacade")
	private QueryFacade queryFacade;

	@Autowired
	@Qualifier("csvFacade")
	private CsvFacade cvsFacade;

	private final static Logger LOG = LogManager.getLogger(PerPerserController.class);
	private final static MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");

	@PostMapping(path = "/parseFile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadFile(UploadFileControllerRequest request, BindingResult bindingResult)
			throws IOException, NotValidFileException {
		final UploadFileValidator uploadValidator = new UploadFileValidator();
		uploadValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {

			List<ObjectError> errors = bindingResult.getAllErrors();

			final String errorMessage = PerPortalUtils.generateErrorMessage(errors);
			final BaseControllerResponse response = new BaseControllerResponse(errorMessage, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(response.getMessage(), response.getErrorCode());
		}

		final MultipartFile file = request.getUploadedFile();
		final String fileType = request.getFileType();
		final String fileName = file.getOriginalFilename();
		final String localeStr = request.getLocaleCod();

		byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		Locale locale = LocaleUtils.toLocale(localeStr);
		final String md5 = parserFacade.parseAndSave(fileName, fileType, locale, bytes);
		LOG.info("md5 of {}  is: {} ", fileName, md5);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/getDocuments")
	public ResponseEntity<String> getDocuments(GetDocumentsRequest request, BindingResult bindingResult)
			throws IOException, NumberFormatException, NoSuchFieldException, NotValidFilterException, ParseException {

		LOG.info("Request is {}", request.toString());

		final GetDocumentsRequestValidator getDocumentsRequestValidator = new GetDocumentsRequestValidator();

		getDocumentsRequestValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {

			List<ObjectError> errors = bindingResult.getAllErrors();
			final String errorMessage = PerPortalUtils.generateErrorMessage(errors);
			final BaseControllerResponse response = new BaseControllerResponse(errorMessage, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(response.getMessage(), response.getErrorCode());
		}

		final JSONObject jsonObj = queryFacade.getDocs(request);

		if (request.isGetCsv()) {

			HttpHeaders httpHeader = new HttpHeaders();
			httpHeader.setContentType(TEXT_CSV_TYPE);
			httpHeader.setContentDispositionFormData("Content-Disposition", "parParser.csv");

			StringBuffer buf = cvsFacade.getCvs((JSONArray) jsonObj.get("data"));

			return new ResponseEntity<String>(buf.toString(), httpHeader, HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(JSON.serialize(jsonObj), HttpStatus.OK);
		}

	}

	@PostMapping("/deleteUploadedFile")
	public ResponseEntity<String> deleteFileUploaded(@RequestBody DeleteDocumentRequest request) throws Exception {
		String message;
		final ObjectMapper objectMapper = new ObjectMapper();
		if (request == null || StringUtils.isBlank(request.getMd5()) || StringUtils.isBlank(request.getFileType())) {
			return new ResponseEntity<String>("Request not valid ", HttpStatus.BAD_REQUEST);
		}
		try {
			boolean deleteSuccess = queryFacade.deleteDocument(request.getMd5(), request.getFileType());
			if (deleteSuccess) {
				message = "Document " + request.getMd5() + " deleted";

				final BaseControllerResponse response = new BaseControllerResponse(
						objectMapper.writeValueAsString(message), HttpStatus.OK);
				return new ResponseEntity<String>(response.getMessage(), response.getErrorCode());
			} else {
				message = "Document not found";
				final BaseControllerResponse response = new BaseControllerResponse(
						objectMapper.writeValueAsString(message), HttpStatus.OK);
				return new ResponseEntity<String>(response.getMessage(), response.getErrorCode());
			}
		} catch (Exception e) {
			final BaseControllerResponse response = new BaseControllerResponse(e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<String>(response.getMessage(), response.getErrorCode());

		}
	}
}
