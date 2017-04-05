package it.percassi.perparser.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.util.JSON;

import it.percassi.perparser.controller.request.GetDocumentsRequest;
import it.percassi.perparser.controller.request.GetNewRelicControllerRequest;
import it.percassi.perparser.controller.request.UploadFileControllerRequest;
import it.percassi.perparser.controller.response.BaseControllerResponse;
import it.percassi.perparser.controller.validator.GetDocumentsRequestValidator;
import it.percassi.perparser.controller.validator.UploadFileValidator;
import it.percassi.perparser.facade.CsvFacade;
import it.percassi.perparser.facade.ParserFacade;
import it.percassi.perparser.facade.QueryFacade;
import it.percassi.perparser.service.parsers.exception.NotValidFileException;
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
	// public ResponseEntity<Void> uploadFile(@RequestParam("uploadedFile")
	// MultipartFile file,
	// @RequestParam("fileType") String fileType) throws IOException {
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

		byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		final String md5 = parserFacade.parseAndSave(fileName, fileType, bytes);
		LOG.info("md5 of {}", fileName + "is: {}", md5);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/getDocuments")
	public ResponseEntity<String> getDocuments(

			// @RequestParam(value = "length", defaultValue="100000",required =
			// false) Integer length,
			// @RequestParam("collectionName") String collectionName,
			// @RequestParam(value ="exclude",required = false) String[]
			// excludes,
			// @RequestParam(value = "start",defaultValue="0" ,required = false)
			// Integer start,
			// @RequestParam(value = "filters", required = false) String
			// filters,
			// @RequestParam(value = "sortField", required = false) String
			// sortField,
			// @RequestParam(value = "sortType", required = false) Integer
			// sortType,
			// @RequestParam(value ="getCsv",required = false) boolean getCsv

			GetDocumentsRequest request, BindingResult bindingResult)
			throws IOException, NumberFormatException, NoSuchFieldException {

		LOG.info("Request is ", request.toString());

		final GetDocumentsRequestValidator getDocumentsRequestValidator = new GetDocumentsRequestValidator();

		getDocumentsRequestValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {

			List<ObjectError> errors = bindingResult.getAllErrors();
			final String errorMessage = PerPortalUtils.generateErrorMessage(errors);
			final BaseControllerResponse response = new BaseControllerResponse(errorMessage, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<String>(response.getMessage(), response.getErrorCode());
		}

		final JSONObject jsonObj = queryFacade.getDocs(request.getCollectionName(), request.getFilters(),
				request.getExclude(), request.getSortField(), request.getSortType(), request.getStart(),
				request.getLength());

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
	@GetMapping("/getNewRelicData")
	public ResponseEntity<Void> getNewRelicApi(GetNewRelicControllerRequest request,BindingResult bindResult){
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		
	}
	
	

}
