package it.percassi.perparser.facade;

import java.io.IOException;
import java.text.ParseException;
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

import it.percassi.perparser.controller.request.GetDocumentsRequest;
import it.percassi.perparser.exception.NotValidFilterException;
import it.percassi.perparser.facade.model.AppEnum;
import it.percassi.perparser.facade.model.MongoPaginationConfig;
import it.percassi.perparser.facade.model.MongoSortConfig;
import it.percassi.perparser.facade.model.MongodbFilter;
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

	public JSONObject getDocs(GetDocumentsRequest request) throws IOException, NoSuchFieldException, NotValidFilterException, ParseException {
		List<MongodbFilter> filters = buildFilterList(request.getFilters());
		MongoSortConfig sortConfig = new MongoSortConfig(request.getSortField(), request.getSortType());
		MongoPaginationConfig pagConfig = new MongoPaginationConfig(request.getStart(), request.getLength());
		return mongoService.getDocs(request.getCollectionName(), filters, request.getExclude(), sortConfig, pagConfig);
	}

	private static List<MongodbFilter> buildFilterList(String jsonfilters) throws IOException, NotValidFilterException {
		List<MongodbFilter> ret = new ArrayList<MongodbFilter>();
		if (StringUtils.isBlank(jsonfilters) || StringUtils.equals(jsonfilters, "{}")) {
			return ret;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		ret = objectMapper.readValue(jsonfilters, new TypeReference<List<MongodbFilter>>() {
		});
		for (MongodbFilter filter : ret) {
			AppEnum.MongoFilterOperator.fromString(filter.getSearchOperator());//throw an exception if the operator is not valid
		}
		return ret;
	}

	public boolean deleteDocument(String md5,String fileType) throws IOException{
		if( mongoService.deleteDocument(md5, fileType)){
			
			return mongoService.deleteDocument(md5, AppEnum.FileType.UPLOADED_FILE.getCode());									
		}
		return false;
		
				
	}
}
