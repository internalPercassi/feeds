package it.percassi.perparser.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Projections;
import it.percassi.perparser.model.UploadedFileModel;
import it.percassi.perparser.properties.PropertiesProvider;
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
public class UploadedFileRepository  extends BaseRepository{

	private final static Logger LOG = LogManager.getLogger(UploadedFileRepository.class);
	
	private MongoCollection getCollection() throws IOException{
		String collectionName = PropertiesProvider.getProperty("mongoDB.collection.UploadedFile");
		MongoCollection collection = this.getDb().getCollection(collectionName);
		return collection;
	}
	
	public void saveUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		List<Document> jsonToInsert = new ArrayList<Document>();		
			String jsonStr = jsonMapper.writeValueAsString(uploadedFile);
			if (jsonStr != null) {
				Document doc = Document.parse(jsonStr);
				jsonToInsert.add(doc);
			}		
		this.getCollection().insertMany(jsonToInsert);
	}
	
	public void updatetUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
		Document jsonToInsert = new Document();		
			String jsonStr = jsonMapper.writeValueAsString(uploadedFile);
			if (jsonStr != null) {
				jsonToInsert = Document.parse(jsonStr);
			}		
		this.getCollection().replaceOne(eq("md5", uploadedFile.getMd5()), jsonToInsert);
	}
	
	public boolean isFileAlreadyUploaded(String md5) throws IOException{		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("md5", md5);
		MongoCursor cursor = this.getCollection().find(whereQuery).iterator();
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
	
	public Long getUploadedFileCount() throws IOException {								
		Long ret = this.getCollection().count();			
		return ret;
	}
	
	public JSONArray getUploadedFile(Integer start, Integer length) throws IOException {		
		int c = 0;
		JSONArray ret = new JSONArray();		
		MongoCursor<Document> cursor = this.getCollection().find().skip(start).limit(length).projection(Projections.excludeId()).iterator();
		try {			
			while (cursor.hasNext()) {
				ret.add(cursor.next());
				c++;
			}
		} finally {
			cursor.close();
		}		
			LOG.trace("get "+c+" rows, skip("+start+").limit("+length+")");			
		return ret;
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
