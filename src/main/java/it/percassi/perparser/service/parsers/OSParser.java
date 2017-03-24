package it.percassi.perparser.service.parsers;

import it.percassi.perparser.service.parsers.parser.OSmodel;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

	@Override
	public List<OSmodel> parse(InputStream stream) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
