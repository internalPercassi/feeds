package it.percassi.perparser.service.parsers;

import it.percassi.perparser.model.AppEnum;
import it.percassi.perparser.service.parsers.model.FacebookFeed;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("facebookProductParser")
public class FacebookProductParser extends BaseParser<FacebookFeed> {	


	@Override
	public List<FacebookFeed> parse(InputStream stream) throws IOException {
		List<FacebookFeed> ret = new ArrayList<FacebookFeed>();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			FacebookFeed fbp = this.build(line);
			if (fbp != null){
				ret.add(fbp);
			}
		}
		return ret;
	}
	
	
	private FacebookFeed build(String line) {		
		FacebookFeed ret = new FacebookFeed();
		String[] tokens = StringUtils.splitPreserveAllTokens(line, FacebookFeed.FIELD_SEPARATOR);
		String priceTmp = "";
		String idTmp = "";
		String currencyCodeTmp = "";		
		for (int c=0;c<tokens.length;c++) {			
			String tmp = tokens[c];
			switch (c+1) {					
				case 1: idTmp = tmp; break;
				case 2: ret.setTitle(tmp); break;
				case 3: ret.setDescription(tmp); break;
				case 6: priceTmp = tmp; break;
				case 7: ret.setBrand(tmp); break;
				case 8: ret.setLink(tmp); break;
				case 10: currencyCodeTmp = tmp; break;	
				case 12: idTmp += ("_"+tmp); break;
				case 16: ret.setProductType(tmp); break;
				case 17: ret.setImageLink(tmp); break;
				case 20: ret.setAvailability(AppEnum.FacebookAvailability.fromString(tmp).getFbCode()); break;
				case 23: ret.setItemGroupId(tmp); break;
				case 27: ret.setColor(tmp); break;
			}			
		}
		ret.setId(idTmp);
		ret.setPrice(priceTmp+" "+currencyCodeTmp);
		return ret;
	}

}
