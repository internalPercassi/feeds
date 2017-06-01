package it.percassi.perparser.service.parsers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.text.DateFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    private final static Logger LOG = LogManager.getLogger(JsonToCsv.class);

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

    public XSSFWorkbook getXls(JSONArray jsonArr) {
        XSSFWorkbook workBook = null;
        workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet("sheet1");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        int rowCount = 0;
        int cellCount = 0;

        XSSFCellStyle style = workBook.createCellStyle();
        XSSFFont font = workBook.createFont();
        font.setBold(true);
        style.setFont(font);

        Document documentForHeader = (Document) jsonArr.get(0);
        Set<String> keySet = documentForHeader.keySet();
        XSSFRow headerRow = sheet.createRow(rowCount++);
        for (String key : keySet) {
            if (StringUtils.equals(MD5_COLUMN_NAME, key)) {
                continue;
            }
            XSSFCell hCell = headerRow.createCell(cellCount++);
            hCell.setCellValue(key);
            hCell.setCellStyle(style);
        }

        for (int i = 0; i < jsonArr.size(); i++) {
            Document document = (Document) jsonArr.get(i);
            cellCount = 0;
            XSSFRow currentRow = sheet.createRow(rowCount++);
            for (String key : keySet) {
                if (StringUtils.equals(MD5_COLUMN_NAME, key)) {
                    continue;
                }
                try {
                    Object objVal = document.get(key);
                    String val = "";
                    if (objVal instanceof String) {
                        val = (String) document.get(key);
                    } else if (objVal instanceof java.util.Date) {
                        val = formatter.format((java.util.Date) objVal);
                    } else if (objVal instanceof Integer) {
                        val = ((Integer) objVal).toString();
                    } else if (objVal instanceof Long) {
                        val = ((Long) objVal).toString();
                    } else if (objVal instanceof Long) {
                        val = ((Long) objVal).toString();
                    }

                    currentRow.createCell(cellCount++).setCellValue(val);
                } catch (Exception e) {
                    LOG.warn("", e);
                }
            }
        }

        return workBook;
    }
}
