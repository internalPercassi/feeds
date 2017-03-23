package it.percassi.perparser.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import it.percassi.perparser.model.AppEnum;
import it.percassi.perparser.model.parser.BaseModel;
import it.percassi.perparser.properties.PropertiesProvider;
import it.percassi.perparser.model.parser.FacebookFeed;
import it.percassi.perparser.model.parser.GLmodel;
import it.percassi.perparser.model.parser.OSmodel;
import it.percassi.perparser.service.parsers.BaseParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniele Sperto
 */
@Repository
public class FacebookFeedRepository extends BaseRepository {

	private final static Logger LOG = LogManager.getLogger(FacebookFeedRepository.class);

	private MongoCollection getCollection() throws IOException {
		String collectionName = PropertiesProvider.getProperty("mongoDB.collection.facebookFeed");
		MongoCollection collection = this.getDb().getCollection(collectionName);
		return collection;
	}

	public void saveFacebookFeed(List<BaseModel> facebookFeeds,String fileType) throws IOException {
		List<Document> jsonToInsert = new ArrayList<Document>();
		for (BaseModel ff : facebookFeeds) {
			String jsonStr = "";
			jsonStr = jsonMapper.writeValueAsString(ff);
			if (jsonStr != null) {
				Document doc = Document.parse(jsonStr);
				jsonToInsert.add(doc);
			}
		}
		this.getCollection().insertMany(jsonToInsert);
	}

	public Long getFacebookFeedCount(BasicDBObject filters) throws IOException {
		Long ret = this.getCollection().count(filters);
		return ret;
	}

	public JSONArray getFacebookFeed(BasicDBObject filters, Integer start, Integer length) throws IOException {
		int c = 0;
		JSONArray ret = new JSONArray();//Filters.eq("fileMd5", md5)		
		MongoCursor<Document> cursor = this.getCollection().find(filters).skip(start).limit(length).projection(Projections.excludeId()).iterator();
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

	private Class getParser(String fileType) {
		if (AppEnum.FileType.FACEBOOK.getCode().equalsIgnoreCase(fileType)) {
			return FacebookFeed.class;
		} else if (AppEnum.FileType.GL.getCode().equalsIgnoreCase(fileType)) {
			return GLmodel.class;
		} else if (AppEnum.FileType.OS.getCode().equalsIgnoreCase(fileType)) {
			return OSmodel.class;
		}
		return null;
	}
}
