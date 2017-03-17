package it.percassi.perparser.parsers.products;

import it.percassi.parsers.products.model.FacebookProduct;
import it.percassi.perparser.parsers.BaseParser;
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
@Service("FacebookProductParser")
public class FacebookProductParser extends BaseParser {

	private static final List<String> COLUMN_LIST = Arrays.asList("id",
			"availability",
			"condition",
			"description",
			"image_link",
			"link",
			"title",
			"price",
			"brand",
			"additional_image_link",
			"age_group",
			"color",
			"expiration_date",
			"gender",
			"item_group_id",
			"google_product_category",
			"material",
			"pattern",
			"product_type",
			"sale_price",
			"shipping",
			"shipping_weight",
			"custom_label_0",
			"custom_label_1",
			"custom_label_2",
			"custom_label_3",
			"custom_label_4"
	);
	public static final String TYPE = "FacebookProduct";
	private static final String TITLE = "Facebook Product View";

	@Override
	public JSONObject parseToJson(InputStream stream) throws IOException {		
		JSONArray list = new JSONArray();
		JSONArray headersList = new JSONArray();
		JSONObject ret = null;

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			FacebookProduct fbp = FacebookProduct.build(line);
			if (fbp != null){
				list.add(fbp.toJSONArray());
			}
		}
		headersList = createHeaderList(COLUMN_LIST);
		ret = createJSON(list, headersList, TYPE, TITLE);
		return ret;
	}	

}
