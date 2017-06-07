package it.percassi.perparser.repository;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;

import it.percassi.perparser.facade.model.MongoPaginationConfig;
import it.percassi.perparser.facade.model.UploadedFileModel;
import it.percassi.perparser.service.parsers.model.BaseModel;

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

    public JSONArray getDocs(String collectionName, BasicDBObject filters, String[] excludes, BasicDBObject sort,
            MongoPaginationConfig pagConfig) throws IOException {
        int c = 0;
        JSONArray ret = new JSONArray();// Filters.eq("fileMd5", md5)
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

        int start = 0;
        int length = 100000;
        if (pagConfig != null) {
            if (pagConfig.getStart() != null) {
                start = pagConfig.getStart();
            }
            if (pagConfig.getLength() != null) {
                length = pagConfig.getLength();
            }
        }
        MongoCursor<Document> cursor = null;
        if (sort != null) {
            cursor = this.getDb().getCollection(collectionName).find(filters).sort(sort).skip(start).limit(length)
                    .projection(bexcludes).iterator();
        } else {
            cursor = this.getDb().getCollection(collectionName).find(filters).skip(start).limit(length)
                    .projection(bexcludes).iterator();
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
        // LOG.trace("Result: "+Arrays.toString(ret.toArray()));
        return ret;
    }

    public void saveUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
        Document jsonToInsert = uploadedFile.toBSONDoc();
        this.getDb().getCollection(UPLOAD_FILE_COLLECTION).insertOne(jsonToInsert);
    }

    public void updatetUploadedFileModel(UploadedFileModel uploadedFile) throws IOException {
        Document jsonToInsert = uploadedFile.toBSONDoc();
        this.getDb().getCollection(UPLOAD_FILE_COLLECTION).replaceOne(eq("md5", uploadedFile.getMd5()), jsonToInsert);
    }

    public boolean isFileAlreadyUploaded(String md5,String type) throws IOException {
        List<BasicDBObject> whereClauses = new ArrayList<>();
        BasicDBObject whereQueryMd5 = new BasicDBObject();
        whereQueryMd5.put("md5", md5);
        whereClauses.add(whereQueryMd5);
        BasicDBObject whereQueryType = new BasicDBObject();
        whereQueryType.put("type", type);
        whereClauses.add(whereQueryMd5);
        BasicDBObject where = new BasicDBObject();
        where.put("$and", whereClauses);
        MongoCursor cursor = this.getDb().getCollection(UPLOAD_FILE_COLLECTION).find(where).iterator();
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

    public boolean deleteDocument(String md5, String fileType) throws MongoException, IOException {

        BasicDBObject filter = new BasicDBObject();
        filter.put("md5", md5);
        DeleteResult documentFound = this.getDb().getCollection(fileType).deleteMany(filter);
        return (!Objects.isNull(documentFound));
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
