package it.percassi.perparser.service.mongo;

import com.mongodb.BasicDBObject;
import it.percassi.perparser.facade.model.AppEnum;
import it.percassi.perparser.repository.MongoDocRepository;
import it.percassi.perparser.facade.model.MongodbFilter;
import it.percassi.perparser.facade.model.UploadedFileModel;
import it.percassi.perparser.facade.model.MongoPaginationConfig;
import it.percassi.perparser.facade.model.MongoSortConfig;
import it.percassi.perparser.service.parsers.model.BaseModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	public void saveDocs(String collectionName, List<BaseModel> feedToSave, String fileType) throws IOException {
		mongoRepository.saveDocs(collectionName, feedToSave, fileType);
	}

	//TODO: vincolare collection name ai valori della enum ApEnum.FileType
	public JSONObject getDocs(String collectionName, List<MongodbFilter> filters, String[] excludes, MongoSortConfig sortConfig, MongoPaginationConfig pagConfig) throws IOException, NoSuchFieldException, ParseException {
		BasicDBObject filter = buildFilter(filters, collectionName);
		BasicDBObject sort = buildSort(sortConfig);
		JSONArray jarr = mongoRepository.getDocs(collectionName, filter, excludes, sort, pagConfig);
		Long count = mongoRepository.getDocCount(collectionName, filter);
		JSONObject ret = new JSONObject();
		ret.put("data", jarr);
		ret.put("recordsTotal", count);
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
	
	public boolean deleteDocument(String md5,String fileType) throws IOException{
		return mongoRepository.deleteDocument(md5, fileType);
		
	}

	private final static BasicDBObject buildFilter(List<MongodbFilter> filters, String fileType) throws NoSuchFieldException, ParseException {
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> objList = new ArrayList<BasicDBObject>();
		BasicDBObject subQ = null;
		Class modelClass = getModelClass(fileType);
		//TODO mettere il formato data in una classe di costante (o nel properties)
		DateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (MongodbFilter filter : filters) {
			subQ = new BasicDBObject();
			Class fieldType = getFieldType(filter.getField(), modelClass);
			if (fieldType.equals(Integer.class)) {
				Integer searchValInt = Integer.parseInt(filter.getSearchVal());
				subQ.put(filter.getField(), new BasicDBObject(filter.getSearchOperator(), searchValInt));
			} else if (fieldType.equals(String.class)) {
				subQ.put(filter.getField(), new BasicDBObject(filter.getSearchOperator(), filter.getSearchVal()));
			}else if (fieldType.equals(Date.class)) {
				 Date qDate= formatter.parse(filter.getSearchVal());
				subQ.put(filter.getField(), new BasicDBObject(filter.getSearchOperator(), qDate));
			}

			objList.add(subQ);
		}

		if (objList.size() > 1) {
			query.put("$and", objList);
			return query;
		} else if (objList.size() == 1) {
			return subQ;
		}
		return new BasicDBObject();
	}

	private static final BasicDBObject buildSort(MongoSortConfig sortConfig) {
		BasicDBObject sort = null;
		if (sortConfig == null && sortConfig.getSortType() != null && sortConfig.getSortField() != null) {
			sort = new BasicDBObject(sortConfig.getSortField(), sortConfig.getSortType());
		}
		return sort;
	}

	private static Class getModelClass(String fileType) {
		Class ret = null;
		AppEnum.FileType e = AppEnum.FileType.getByCode(fileType);
		ret = e.getModelClass();
		return ret;
	}

	private static Class getFieldType(String fieldName, Class type) throws NoSuchFieldException {
		Class ret = null;
		Field field = null;
		try {
			field = type.getDeclaredField(fieldName);
		} catch (Exception e) {
			field = type.getSuperclass().getDeclaredField(fieldName);
		}
		ret = field.getType();
		return ret;
	}
}
