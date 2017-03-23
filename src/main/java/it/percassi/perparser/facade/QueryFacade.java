package it.percassi.perparser.facade;

import it.percassi.perparser.model.MongodbFilter;
import it.percassi.perparser.service.mongo.MongoService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("queryFacade")
public class QueryFacade {

	private final static Logger LOG = LogManager.getLogger(QueryFacade.class);
	
	@Autowired
	MongoService mongoService;

	public JSONObject getFacebookFeed(String jsonfilters, Integer start, Integer length) throws IOException {
		List<MongodbFilter> filters = buildFilterList(jsonfilters);
		return mongoService.getFacebookFeed(filters, start, length);
	}

	public JSONObject getUploadedFile(String md5, String jsonfilters, Integer start, Integer length) throws IOException {
		List<MongodbFilter> filters = buildFilterList(jsonfilters);
		return mongoService.getUploadedFile(start, length);
	}

	private static List<MongodbFilter> buildFilterList(String jsonfilters) throws IOException {
		List<MongodbFilter> ret = new ArrayList<MongodbFilter>();
		if (StringUtils.isBlank(jsonfilters)){
			return ret;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ret = objectMapper.readValue(jsonfilters, new TypeReference<List<MongodbFilter>>() {
			});
		} catch (Exception e) {
			LOG.warn("",e);
			return new ArrayList<MongodbFilter>();
		}
		return ret;
	}
}
