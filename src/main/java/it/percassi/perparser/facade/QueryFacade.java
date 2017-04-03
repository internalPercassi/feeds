package it.percassi.perparser.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.percassi.perparser.service.mongo.model.MongodbFilter;
import it.percassi.perparser.service.mongo.MongoService;

/**
 *
 * @author Daniele Sperto
 */
@Service("queryFacade")
public class QueryFacade {

	private final static Logger LOG = LogManager.getLogger(QueryFacade.class);

	@Autowired
	MongoService mongoService;

	public JSONObject getDocs(String collectionName, String jsonfilters, String[] excludes, String sortField, Integer sortType, Integer start, Integer length) throws IOException, NoSuchFieldException {
		List<MongodbFilter> filters = buildFilterList(jsonfilters);
		return mongoService.getDocs(collectionName, filters, excludes, sortField, sortType, start, length);
	}

	private static List<MongodbFilter> buildFilterList(String jsonfilters) throws IOException {
		List<MongodbFilter> ret = new ArrayList<MongodbFilter>();
		if (StringUtils.isBlank(jsonfilters)) {
			return ret;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ret = objectMapper.readValue(jsonfilters, new TypeReference<List<MongodbFilter>>() {
			});
		} catch (Exception e) {
			LOG.warn("", e);
			return new ArrayList<MongodbFilter>();
		}
		return ret;
	}
}
