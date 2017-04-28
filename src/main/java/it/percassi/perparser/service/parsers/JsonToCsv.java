package it.percassi.perparser.service.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
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
    private static final String MD5_COLUMN_NAME = "md5";

    public StringBuffer getCvs(JSONArray jsonArr) throws IOException {
        StringBuffer buf = new StringBuffer();
        CSVFormat csvFileFormat = CSVFormat.RFC4180.withRecordSeparator(NEW_LINE_SEPARATOR);
        CSVPrinter csvFilePrinter = new CSVPrinter(buf, csvFileFormat);

        Document document = (Document) jsonArr.get(0);

        Set<String> keySet = new HashSet<String>();
        keySet = document.keySet();
        List<String> values = new ArrayList<String>(keySet.size());
        for (String key : keySet) {
            if (StringUtils.equals(MD5_COLUMN_NAME, key)) {
                continue;
            }
            values.add((String) key);
        }
        csvFilePrinter.printRecord(values);

        for (int i = 0; i < jsonArr.size(); i++) {
            values = new ArrayList<String>(keySet.size());
            document = (Document) jsonArr.get(i);
            for (String key : keySet) {
                if (StringUtils.equals(MD5_COLUMN_NAME, key)) {
                    continue;
                }
                Object val = document.get(key);
                String strTmp = "";
                if (val != null) {
                    strTmp = val.toString();
                }
                values.add(strTmp);
            }
            csvFilePrinter.printRecord(values);
        }
        return buf;
    }
}
