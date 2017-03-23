package it.percassi.perparser.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import it.percassi.perparser.properties.PropertiesProvider;
import it.percassi.perparser.model.FacebookFeed;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class FacebookFeedRepository extends BaseRepository {

	private final static Logger LOG = LogManager.getLogger(FacebookFeedRepository.class);

	private static MongoCollection getCollection() throws IOException {
		String collectionName = PropertiesProvider.getProperty("mongoDB.collection.facebookFeed");
		MongoCollection collection = BaseRepository.getDb().getCollection(collectionName);
		return collection;
	}

	public void saveFacebookFeed(List<FacebookFeed> facebookFeeds) throws IOException {
		List<Document> jsonToInsert = new ArrayList<Document>();
		for (FacebookFeed ff : facebookFeeds) {
			String jsonStr = jsonMapper.writeValueAsString(ff);
			if (jsonStr != null) {
				Document doc = Document.parse(jsonStr);
				jsonToInsert.add(doc);
			}
		}
		FacebookFeedRepository.getCollection().insertMany(jsonToInsert);
	}

	public Long getFacebookFeedCount(BasicDBObject filters) throws IOException {
		Long ret = FacebookFeedRepository.getCollection().count(filters);
		return ret;
	}

	public JSONArray getFacebookFeed(BasicDBObject filters, Integer start, Integer length) throws IOException {
		int c = 0;
		JSONArray ret = new JSONArray();//Filters.eq("fileMd5", md5)		
		MongoCursor<Document> cursor = FacebookFeedRepository.getCollection().find(filters).skip(start).limit(length).projection(Projections.excludeId()).iterator();
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

}
