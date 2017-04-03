package it.percassi.perparser.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.util.JSON;

import it.percassi.perparser.facade.CsvFacade;
import it.percassi.perparser.facade.ParserFacade;
import it.percassi.perparser.facade.QueryFacade;


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
	public ResponseEntity<Void> uploadFile(@RequestParam("uploadedFile") MultipartFile file,
			   @RequestParam("fileType") String fileType) throws IOException {
	
		final String fileName = file.getOriginalFilename();

		byte[] bytes = IOUtils.toByteArray(file.getInputStream());
		final String md5 = parserFacade.parseAndSave(fileName, fileType, bytes);
		LOG.info("md5 of {}",fileName +"is: {}",md5);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/getDocuments")
	public ResponseEntity<String> getDocuments(
			@RequestParam(value = "length", defaultValue="100000",required = false) Integer length,
			@RequestParam("collectionName") String collectionName, 
			@RequestParam(value ="exclude",required = false) String[] excludes,
			@RequestParam(value = "start",defaultValue="0" ,required = false) Integer start,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "sortField", required = false) String sortField,
			@RequestParam(value = "sortType", required = false) Integer sortType,
			@RequestParam(value ="getCsv",required = false) boolean getCsv

	) throws IOException, NumberFormatException, NoSuchFieldException {
		
		
		LOG.info("length {}; collectionName {};start {};filters {};sortField {};sortType {} ", length, collectionName,
				start, filters, sortField, sortType);
		
		
		final JSONObject jsonObj = queryFacade.getDocs(collectionName, filters, excludes, sortField, sortType,
				start, length);

		if (getCsv) {
			
			HttpHeaders httpHeader = new HttpHeaders();
			httpHeader.setContentType(TEXT_CSV_TYPE);
			httpHeader.setContentDispositionFormData("Content-Disposition", "parParser.csv");
			
			StringBuffer buf = cvsFacade.getCvs((JSONArray) jsonObj.get("data"));
			
			return new ResponseEntity<String>(buf.toString(),httpHeader,HttpStatus.OK);
		
		} else {
		
			return new ResponseEntity<String>(JSON.serialize(jsonObj),HttpStatus.OK);
		}
	
	}

}
