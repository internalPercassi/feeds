package it.percassi.perparser.repository;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import it.percassi.perparser.properties.PropertiesProvider;

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
