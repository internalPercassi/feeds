package it.percassi.perparser.service.mongo;

import com.mongodb.BasicDBObject;
import it.percassi.perparser.repository.MongoDocRepository;
import it.percassi.perparser.model.MongodbFilter;
import it.percassi.perparser.model.UploadedFileModel;
import it.percassi.perparser.service.parsers.model.BaseModel;
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
	private MongoDocRepository mongoRepository;

	public void saveDocs(String collectionName,List<BaseModel> feedToSave,String fileType) throws IOException {
		mongoRepository.saveDocs(collectionName,feedToSave,fileType);
	}

	public JSONObject getDocs(String collectionName,List<MongodbFilter> filters, String[] excludes, String sortField, Integer sortType, Integer start, Integer length) throws IOException {
		BasicDBObject filter = buildFilter(filters);
		BasicDBObject sort = buildSort(sortField, sortType);
		JSONArray jarr = mongoRepository.getDocs(collectionName,filter,excludes,sort,start, length);
		Long count = mongoRepository.getDocCount(collectionName,filter);
		JSONObject ret = new JSONObject();
		ret.put("data",jarr);
		ret.put("recordsTotal",count);
		return ret;
	}		

	public boolean isFileAlreadyUploaded(String md5) throws IOException {
		return mongoRepository.isFileAlreadyUploaded(md5);
	}

	public void saveUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		mongoRepository.saveUploadedFileModel(uploadedFile);
	}
	
	public void updatetUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		mongoRepository.updatetUploadedFileModel(uploadedFile);
	}
	
	public static final BasicDBObject buildFilter(List<MongodbFilter> filters) {
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> objList = new ArrayList<BasicDBObject>();
		BasicDBObject subQ = null;
		for (MongodbFilter filter : filters) {			
			subQ = new BasicDBObject();
			subQ.put(filter.getField(), new BasicDBObject(filter.getSearchOperator(),filter.getSearchVal()));
			objList.add(subQ);
		}
		
		if (objList.size()>1){			
			query.put("$and", objList);
			return query;
		} else if (objList.size() == 1){
			return subQ;
		}
		return new BasicDBObject();
	}
	
	
	public static final BasicDBObject buildSort(String sortField, Integer sortType) {
		BasicDBObject sort = null;
		if (sortType != null && sortField != null){
			sort = new BasicDBObject(sortField,sortType);
		}
		return sort;
	}
}
