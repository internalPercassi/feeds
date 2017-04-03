package it.percassi.perparser.repository;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Daniele Sperto
 */

public class BaseRepository {

	ObjectMapper jsonMapper = new ObjectMapper();
	MongoClient mongoClient;
	
	@Value("${mongoDB.DBname}")
	private String mongoDBName;
	@Value("${mongoDB.URI}")
	private String mongoDBUri;
	

	protected MongoDatabase getDb() throws IOException {
		return mongoClient.getDatabase(mongoDBName);
	}

	@PostConstruct
	public void postConstruct() throws IOException {
		final MongoClientURI uri = new MongoClientURI(mongoDBUri);
		mongoClient = new MongoClient(uri);
	}
		
}
