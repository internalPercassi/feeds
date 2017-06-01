package it.percassi.perparser.facade;

import it.percassi.perparser.service.parsers.JsonToCsv;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("csvFacade")
public class CsvFacade {

	@Autowired
	JsonToCsv jsonToCsv;
	
	public StringBuffer getCvs(JSONArray jsonArr) throws IOException {
		return jsonToCsv.getCvs(jsonArr);
	}
        
        public XSSFWorkbook getXls(JSONArray jsonArr) throws IOException {
		return jsonToCsv.getXls(jsonArr);
	}
}
