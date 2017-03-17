package it.percassi.perparser.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("OSParser")
public class OSParser extends BaseParser {

	private static final List<String> COLUMN_LIST = Arrays.asList("Codice Articolo", "Codice Modello", "Magazzino", "Inventario fisico", "In ordinazione", "Soglia riassortimento");
	static final String TYPE = "OS";
	private static final String TITLE = "Overselling File View";

	@Override
	public JSONObject parseToJson(InputStream stream) throws IOException {
		JSONArray tmplist = null;
		JSONArray list = new JSONArray();
		JSONArray headersList = new JSONArray();
		JSONObject ret = null;

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			tmplist = new JSONArray();
			tmplist.add(line.substring(0, 26));
			tmplist.add(line.substring(26, 46));
			tmplist.add(line.substring(46, 56));
			tmplist.add(line.substring(56, 68));
			tmplist.add(line.substring(68, 80));
			tmplist.add(line.substring(86, 92));
			list.add(tmplist);
		}
		headersList = createHeaderList(COLUMN_LIST);
		ret = createJSON(list, headersList, TYPE, TITLE);
		return ret;
	}
}
