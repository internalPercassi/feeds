package it.percassi.perparser.service.mongo;

import com.mongodb.BasicDBObject;
import it.percassi.perparser.repository.FacebookFeedRepository;
import it.percassi.perparser.model.MongodbFilter;
import it.percassi.perparser.model.UploadedFileModel;
import it.percassi.perparser.service.parsers.parser.BaseModel;
import it.percassi.perparser.repository.UploadedFileRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("mongoService")
public class MongoService {

	private final static Logger LOG = LogManager.getLogger(MongoService.class);

	@Autowired
	private FacebookFeedRepository facebookFeedRepository;
	@Autowired
	private UploadedFileRepository uploadedFileRepository;

	public void saveFacebookFeed(List<BaseModel> feedToSave,String fileType) throws IOException {
		facebookFeedRepository.saveFacebookFeed(feedToSave,fileType);
	}

	public JSONObject getFacebookFeed(List<MongodbFilter> filters,Integer start, Integer length) throws IOException {
		BasicDBObject filter = buildFilter(filters);
		JSONArray jarr = facebookFeedRepository.getFacebookFeed(filter,start, length);
		Long count = facebookFeedRepository.getFacebookFeedCount(filter);
		JSONObject ret = new JSONObject();
		ret.put("data",jarr);
		ret.put("recordsTotal",count);
		return ret;
	}
	
	public JSONObject getUploadedFile(Integer start, Integer length) throws IOException {
		JSONArray jarr = uploadedFileRepository.getUploadedFile(start, length);
		Long count = uploadedFileRepository.getUploadedFileCount();
		JSONObject ret = new JSONObject();
		ret.put("data",jarr);
		ret.put("recordsTotal",count);
		return ret;
	}

	public boolean isFileAlreadyUploaded(String md5) throws IOException {
		return uploadedFileRepository.isFileAlreadyUploaded(md5);
	}

	public void saveUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		uploadedFileRepository.saveUploadedFileModel(uploadedFile);
	}
	
	public void updatetUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		uploadedFileRepository.updatetUploadedFileModel(uploadedFile);
	}
	
	public static final BasicDBObject buildFilter(List<MongodbFilter> filters) {
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> objList = new ArrayList<BasicDBObject>();
		for (MongodbFilter filter : filters) {			
			BasicDBObject subQ = new BasicDBObject();
			subQ.put(filter.getField(), new BasicDBObject(filter.getSearchOperator(),filter.getSearchVal()));
			objList.add(subQ);
		}
		if (objList.size()>0){			
			query.put("$and", objList);
		}
		return query;
	}
}
