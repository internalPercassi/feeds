package it.percassi.perparser.repository;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Daniele Sperto
 */
public class BaseRepository {

    ObjectMapper jsonMapper = new ObjectMapper();
    MongoClient mongoClient;

    private final static Logger LOG = LogManager.getLogger(BaseRepository.class);
    @Value("${mongoDB.DBname}")
    private String mongoDBName;
    @Value("${mongoDB.URI}")
    private String mongoDBUri;

    protected MongoDatabase getDb() throws IOException {
        return mongoClient.getDatabase(mongoDBName);
    }

    @PostConstruct
    public void postConstruct() {
        try {
            final MongoClientURI uri = new MongoClientURI(mongoDBUri);
            mongoClient = new MongoClient(uri);
        } catch (Exception e) {
            LOG.error("Unable to close mongo connection",e);
        }
    }

}
