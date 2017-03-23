package it.percassi.perparser.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import it.percassi.perparser.properties.PropertiesProvider;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Daniele Sperto
 */
public class BaseRepository {

	ObjectMapper jsonMapper = new ObjectMapper();
	MongoClient mongoClient;

	protected MongoDatabase getDb() throws IOException {
		String dbName = PropertiesProvider.getProperty("mongoDB.DBname");
		return mongoClient.getDatabase(dbName);
	}

	@PostConstruct
	public void postConstruct() throws IOException {
		String dbURI = PropertiesProvider.getProperty("mongoDB.URI");
		MongoClientURI uri = new MongoClientURI(dbURI);
		mongoClient = new MongoClient(uri);
	}
		
}
