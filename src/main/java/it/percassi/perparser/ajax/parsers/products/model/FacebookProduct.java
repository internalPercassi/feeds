package it.percassi.perparser.parsers.products.model;

import com.perparser.parsers.products.model.AvailabilityEnum;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;

/**
 *
 * @author Daniele Sperto
 */
public class FacebookProduct {

//	private final static Logger LOG = LoggerFactory.getLogger(FacebookProduct.class);
	
	private static final String FIELD_SEPARATOR = "|";
	private String id;
	private String availability;
	private String condition = "new";
	private String description;
	private String imageLink;
	private String link;
	private String title;
	private String price;
	private String brand = "KIK";
	private String additionalImageLink;
	private String ageGroup;
	private String color;
	private String expirationDate;
	private String gender;
	private String itemGroupId;
	private String googleProductCategory="Health & Beauty > Personal Care > Cosmetics > Makeup > Eye Makeup > Mascara";
	private String material;
	private String pattern;
	private String productType;
	private String salePrice;
	private String shipping;
	private String shippingWeight;	
	private String customLabel0;
	private String customLabel1;
	private String customLabel2;
	private String customLabel3;
	private String customLabel4;

	public static FacebookProduct build(String line) {		
		FacebookProduct ret = new FacebookProduct();
		String[] tokens = StringUtils.splitPreserveAllTokens(line, FIELD_SEPARATOR);
		String priceTmp = "";
		String idTmp = "";
		String currencyCodeTmp = "";		
		for (int c=0;c<tokens.length;c++) {			
			String tmp = tokens[c];
			switch (c+1) {					
				case 1: idTmp = tmp; break;
				case 2: ret.title = tmp; break;
				case 3: ret.description = tmp; break;
				case 6: priceTmp = tmp; break;
				case 7: ret.brand = tmp; break;
				case 8: ret.link = tmp; break;
				case 10: currencyCodeTmp = tmp; break;	
				case 12: idTmp += ("_"+tmp); break;
				case 16: ret.productType = tmp; break;
				case 17: ret.imageLink = tmp; break;
				case 20: ret.availability = AvailabilityEnum.fromString(tmp).getFbCode(); break;
				case 23: ret.itemGroupId = tmp; break;
				case 27: ret.color = tmp; break;
			}			
		}
		ret.id=idTmp;
		ret.price = priceTmp+" "+currencyCodeTmp;
		return ret;
	}

	private FacebookProduct() {
	}
	
	
	public JSONArray toJSONArray(){
		JSONArray ret = new JSONArray();
		ret.add(this.id);
		ret.add(this.availability);
		ret.add(this.condition);
		ret.add(this.description);
		ret.add(this.imageLink);
		ret.add(this.link);
		ret.add(this.title);
		ret.add(this.price);
		ret.add(this.brand);
		ret.add(this.additionalImageLink);
		ret.add(this.ageGroup);
		ret.add(this.color);
		ret.add(this.expirationDate);
		ret.add(this.gender);
		ret.add(this.itemGroupId);
		ret.add(this.googleProductCategory);
		ret.add(this.material);
		ret.add(this.pattern);
		ret.add(this.productType);
		ret.add(this.salePrice);
		ret.add(this.shipping);
		ret.add(this.shippingWeight);
		ret.add(this.customLabel0);
		ret.add(this.customLabel1);
		ret.add(this.customLabel2);
		ret.add(this.customLabel3);
		ret.add(this.customLabel4);
		return ret;
	}
}
