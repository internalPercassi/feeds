package it.percassi.perparser.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Daniele Sperto
 */
public abstract class BaseParser {

	public abstract JSONObject parseToJson(InputStream stream) throws IOException;

	public JSONArray createHeaderList(List<String> columnList) {
		JSONArray headersList = new JSONArray();

		for (String headerStr : columnList) {
			JSONObject headerObj = new JSONObject();
			headerObj.put("title", headerStr);
			headersList.add(headerObj);
		}
		return headersList;
	}

	public JSONObject createJSON(JSONArray contentList, JSONArray headersList, String type, String title) {
		JSONObject ret = new JSONObject();
		ret.put("title", title);
		ret.put("type", type);
		ret.put("data", contentList);
		ret.put("headers", headersList);
		return ret;
	}
}
