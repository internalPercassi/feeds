package it.percassi.perparser.service.mongo;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;

import it.percassi.perparser.facade.model.UploadedFileModel;
import it.percassi.perparser.repository.MongoDocRepository;
import it.percassi.perparser.service.mongo.model.MongodbFilter;
import it.percassi.perparser.service.parsers.model.BaseModel;

/**
 *
 * @author Daniele Sperto
 */
@Service("mongoService")
public class MongoService {

	private final static Logger LOG = LogManager.getLogger(MongoService.class);

	@Autowired
	private MongoDocRepository mongoRepository;
	@Autowired
	private MongoFilterService mongoFilterService;

	public void saveDocs(String collectionName,List<BaseModel> feedToSave,String fileType) throws IOException {
		mongoRepository.saveDocs(collectionName,feedToSave,fileType);
	}

	//TODO: vincolare collection name ai valori della enum ApEnum.FileType
	public JSONObject getDocs(String collectionName,List<MongodbFilter> filters, String[] excludes, String sortField, Integer sortType, Integer start, Integer length) throws IOException, NoSuchFieldException {
		BasicDBObject filter = mongoFilterService.buildFilter(filters,collectionName);
		BasicDBObject sort = mongoFilterService.buildSort(sortField, sortType);
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
	
	
}
