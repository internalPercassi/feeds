package it.percassi.perparser.service.parsers;

import it.percassi.perparser.facade.model.AppEnum;
import it.percassi.perparser.service.parsers.exception.NotValidFileException;
import it.percassi.perparser.service.parsers.model.FacebookFeed;
import it.percassi.perparser.service.parsers.model.RegexPatterns;
import it.percassi.perparser.service.parsers.model.WAFModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.spi.http.HttpExchange;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("wafParser")
public class WAFParser extends BaseParser<WAFModel> {

	private final static Logger LOG = LogManager.getLogger(WAFParser.class);
	private static final int ROW_LENGTH = 28;
	public static final String FIELD_SEPARATOR = ",";

	@Override
	public List<WAFModel> parse(InputStream stream) throws IOException {
		List<WAFModel> ret = new ArrayList<WAFModel>();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		final CSVFormat csvFileFormat = CSVFormat.RFC4180;
		for (CSVRecord record : csvFileFormat.parse(bufferedReader)) {
			WAFModel fbp = this.build(record);
			if (fbp != null) {
				ret.add(fbp);
			}
		}
		return ret;
	}

	private WAFModel build(CSVRecord record) {
		WAFModel ret = new WAFModel();
		int c = 0;
		for (String field : record) {
			String tmp = field;
			switch (++c) {
				case 1:
					ret.setRemote_addr(tmp);
					break;
				case 2:
					ret.setTime_iso8601(tmp);
					break;
				case 3:
					ret.setRemote_user(tmp);
					break;
				case 4:
					ret.setStatus(tmp);
					break;
				case 5:
					ret.setBytes_sent(tmp);
					break;
				case 6:
					ret.setMethod(tmp);
					break;
				case 7:
					ret.setRequest(tmp);
					break;
				case 8:
					ret.setProto_version(tmp);
					break;
				case 9:
					ret.setHttp_user_agent(tmp);
					break;
				case 10:
					ret.setBlocked(tmp);
					break;
				case 11:
					ret.setIs_human(tmp);
					break;
				case 12:
					ret.setBlock_reason(tmp);
					break;
				case 13:
					ret.setGeoip_city_country_name(tmp);
					break;
				case 14:
					ret.setGeoip_city(tmp);
					break;
				case 15:
					ret.setGeoip_longitude(tmp);
					break;
				case 16:
					ret.setGeoip_latitude(tmp);
					break;
				case 17:
					ret.setRequest_id(tmp);
					break;
				case 18:
					ret.setMatched_asnum(tmp);
					break;
				case 19:
					ret.setCaptured_vector(tmp);
					break;
				case 20:
					ret.setRequest_time(tmp);
					break;
				case 21:
					ret.setUpstream_addr(tmp);
					break;
				case 22:
					ret.setUpstream_response_time(tmp);
					break;
				case 23:
					ret.setDomain_name(tmp);
				case 24:
					ret.setHost(tmp);
					break;
				case 25:
					ret.setReferer(tmp);
					break;
				case 26:
					ret.setRequest_headers(tmp);
					break;
				case 27:
					ret.setOrganization(tmp);
					break;
				case 28:
					ret.setUpstream_status(tmp);
					break;
				case 29:
					ret.setUri(tmp);
					break;
				case 30:
					ret.setHostname(tmp);
					break;
				case 31:
					ret.setIs_tor(tmp);
					break;
				case 33:
					ret.setIs_vpn(tmp);
					break;
				case 34:
					ret.setIs_anonymizer(tmp);
					break;
				case 35:
					ret.setIs_proxy(tmp);
					break;
				case 36:
					ret.setRbzsessionid(tmp);
					break;
				case 37:
					ret.setRequest_length(tmp);
					break;
				case 38:
					ret.setSent_http_cache_control(tmp);
					break;
				case 39:
					ret.setSent_http_expires(tmp);
					break;
				case 40:
					ret.setCookie_rbzid(tmp);
					break;
				case 41:
					ret.setSent_http_content_type(tmp);
					break;
				case 42:
					ret.setAnything_else(tmp);
					break;
			}
		}
		return ret;
	}

	@Override
	public void isLineValid(String line) throws NotValidFileException {
		String[] tokens = StringUtils.splitPreserveAllTokens(line, FacebookFeed.FIELD_SEPARATOR);
		if (tokens.length != ROW_LENGTH) {
			throw new NotValidFileException("Length expeted " + ROW_LENGTH + ", get " + tokens.length);
		}
//		if (!tokens[0].matches(RegexPatterns.IPADDRESS_PATTERN)) {
//			throw new NotValidFileException("Invalid URL found: " + tokens[0]);
//		}
//		if (!StringUtils.isNumeric(tokens[3])) {
//			throw new NotValidFileException("Invalid HTTP Status Code found: " + tokens[3]);
//		}
	}
}
