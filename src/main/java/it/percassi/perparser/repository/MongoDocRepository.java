package it.percassi.perparser.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import it.percassi.perparser.model.UploadedFileModel;
import it.percassi.perparser.service.parsers.model.BaseModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniele Sperto
 */
@Repository
public class MongoDocRepository extends BaseRepository {

	private final static Logger LOG = LogManager.getLogger(MongoDocRepository.class);
	private final static String UPLOAD_FILE_COLLECTION = "uploadedFile";
	
	public void saveDocs(String collectionName, List<BaseModel> docs, String fileType) throws IOException {
		List<Document> jsonToInsert = new ArrayList<Document>();
		for (BaseModel ff : docs) {
			String jsonStr = "";
			jsonStr = jsonMapper.writeValueAsString(ff);
			if (jsonStr != null) {
				Document doc = Document.parse(jsonStr);
				jsonToInsert.add(doc);
			}
		}
		this.getDb().getCollection(collectionName).insertMany(jsonToInsert);
	}

	public Long getDocCount(String collectionName, BasicDBObject filters) throws IOException {
		Long ret = this.getDb().getCollection(collectionName).count(filters);
		return ret;
	}

	public JSONArray getDocs(String collectionName, BasicDBObject filters, String[] excludes, BasicDBObject sort, Integer start, Integer length) throws IOException {
		int c = 0;
		JSONArray ret = new JSONArray();//Filters.eq("fileMd5", md5)	
		int excludesLength = 1;
		if (excludes != null) {
			excludesLength++;
		}
		BasicDBObject bexcludes = new BasicDBObject(excludesLength);
		if (excludes != null) {
			for (String ex : excludes) {
				bexcludes.append(ex, false);
			}
		}
		bexcludes.append("_id", false);
		MongoCursor<Document> cursor = null;
		if (sort != null) {
			cursor = this.getDb().getCollection(collectionName).find(filters).sort(sort).skip(start).limit(length).projection(bexcludes).iterator();
		} else {
			cursor = this.getDb().getCollection(collectionName).find(filters).skip(start).limit(length).projection(bexcludes).iterator();
		}
		try {
			while (cursor.hasNext()) {
				ret.add(cursor.next());
				c++;
			}
		} finally {
			cursor.close();
		}
		LOG.trace("get " + c + " rows, skip(" + start + ").limit(" + length + ")");
		return ret;
	}

	
	public void saveUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		List<Document> jsonToInsert = new ArrayList<Document>();		
			String jsonStr = jsonMapper.writeValueAsString(uploadedFile);
			if (jsonStr != null) {
				Document doc = Document.parse(jsonStr);
				jsonToInsert.add(doc);
			}		
		this.getDb().getCollection(UPLOAD_FILE_COLLECTION).insertMany(jsonToInsert);
	}
	
	public void updatetUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		Document jsonToInsert = new Document();		
			String jsonStr = jsonMapper.writeValueAsString(uploadedFile);
			if (jsonStr != null) {
				jsonToInsert = Document.parse(jsonStr);
			}		
		this.getDb().getCollection(UPLOAD_FILE_COLLECTION).replaceOne(eq("md5", uploadedFile.getMd5()), jsonToInsert);
	}
	
	public boolean isFileAlreadyUploaded(String md5) throws IOException{		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("md5", md5);
		MongoCursor cursor = this.getDb().getCollection(UPLOAD_FILE_COLLECTION).find(whereQuery).iterator();
		try {
			if (cursor.hasNext()) {
				return true;
			} else {
				return false;
			}
		} finally {
			cursor.close();
		}
	}
	
	@PreDestroy
	public void cleanUp() throws Exception {
		try {
			this.mongoClient.close();
			LOG.info("MongoDb connection close");
		} catch (Exception e) {
			LOG.error(LOG, e);
		}
		LOG.info("contextDestroyed");
	}

}
