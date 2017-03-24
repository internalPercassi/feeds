package it.percassi.perparser.service.parsers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("jsonToCsv")
public class JsonToCsv {

	private static final String NEW_LINE_SEPARATOR = "\n";

	public StringBuffer getCvs(JSONArray jsonArr) throws IOException {
		StringBuffer buf = new StringBuffer();
		CSVFormat csvFileFormat = CSVFormat.RFC4180.withRecordSeparator(NEW_LINE_SEPARATOR);
		CSVPrinter csvFilePrinter = new CSVPrinter(buf, csvFileFormat);

		Document document = (Document) jsonArr.get(1);

		Set<String> keySet = new HashSet<String>();
		keySet = document.keySet();
		List<String> values = new ArrayList(keySet.size());
		for (String key : keySet) {			
			values.add((String) key);
		}
		csvFilePrinter.printRecord(values);
		
		for (int i = 0; i < jsonArr.size(); i++) {
			values = new ArrayList(keySet.size());
			document = (Document) jsonArr.get(i);
			for (String key : keySet) {
				values.add((String) document.get(key));
			}
			csvFilePrinter.printRecord(values);
		}
		return buf;
	}
}
