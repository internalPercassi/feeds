package it.percassi.perparser.service.parsers;

import it.percassi.perparser.exception.NotValidFileException;
import it.percassi.perparser.service.parsers.model.GLmodel;
import it.percassi.perparser.service.parsers.model.OSmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("OSParser")
public class OSParser extends BaseParser<OSmodel> {
	
//	@Override
//	public JSONObject parseToJson(InputStream stream) throws IOException {
//		JSONArray tmplist = null;
//		JSONArray list = new JSONArray();
//		JSONArray headersList = new JSONArray();
//		JSONObject ret = null;
//
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
//		String line = null;
//		while ((line = bufferedReader.readLine()) != null) {
//			tmplist = new JSONArray();
//			tmplist.add(line.substring(0, 26));
//			tmplist.add(line.substring(26, 46));
//			tmplist.add(line.substring(46, 56));
//			tmplist.add(line.substring(56, 68));
//			tmplist.add(line.substring(68, 80));
//			tmplist.add(line.substring(86, 92));
//			list.add(tmplist);
//		}
//		headersList = createHeaderList();
//		ret = createJSON(list, headersList,  AppEnum.FeedType.OS.getCode(), AppEnum.FeedTitle.OS.getTitleText());
//		return ret;
//	}	
	
	private final static Logger LOG = LogManager.getLogger(OSParser.class);
	private static final int ROW_LENGTH_A = 237;
	private static final int ROW_LENGTH_B = 224;
	
	@Override
	public List<OSmodel> parse(InputStream stream) throws IOException, NotValidFileException {
		List<OSmodel> ret = new ArrayList<OSmodel>();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		int c = 0;
		boolean skipInsering = false;
		while ((line = bufferedReader.readLine()) != null) {

			c++;
			OSmodel tmp = new OSmodel();
			tmp.setProductCode(line.substring(0, 26).trim());
			tmp.setModelCode(line.substring(26, 46).trim());
			tmp.setWarehouse(line.substring(46, 56).trim());
			tmp.setPhysicalInventory(line.substring(56, 68).trim());
			tmp.setOrderStatus(line.substring(68, 80).trim());
			tmp.setReplenishmentLevel(line.substring(86, 92).trim());

			ret.add(tmp);
		}
		if (skipInsering){
			return new ArrayList<OSmodel>();
		}
		return ret;
	}

	public void isLineValid(String line) throws NotValidFileException {

		
		String tmp = line.substring(150, 160).replaceFirst("^0+(?!$)", "").trim();
		if (!StringUtils.isNumeric(tmp)) {
			throw new NotValidFileException("StockedQty: expected number, get: " + tmp);
		}
		tmp = line.substring(163, 173).replaceFirst("^0+(?!$)", "").trim();
		if (!StringUtils.isNumeric(tmp)) {
			throw new NotValidFileException("BookedQty: expected number, get: " + tmp);
		}
	}

}
