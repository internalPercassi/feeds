package it.percassi.perparser.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import it.percassi.perparser.service.parsers.parser.BaseModel;
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

//	private MongoCollection getCollection() throws IOException {
//		String collectionName = PropertiesProvider.getProperty("mongoDB.collection.facebookFeed");
//		MongoCollection collection = this.getDb().getCollection(collectionName);
//		return collection;
//	}

	public void saveDocs(String collectionName,List<BaseModel> docs,String fileType) throws IOException {
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

	public Long getDocCount(String collectionName,BasicDBObject filters) throws IOException {
		Long ret = this.getDb().getCollection(collectionName).count(filters);
		return ret;
	}

	public JSONArray getDocs(String collectionName,BasicDBObject filters, Integer start, Integer length) throws IOException {
		int c = 0;
		JSONArray ret = new JSONArray();//Filters.eq("fileMd5", md5)		
		MongoCursor<Document> cursor = this.getDb().getCollection(collectionName).find(filters).skip(start).limit(length).projection(Projections.excludeId()).iterator();
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
