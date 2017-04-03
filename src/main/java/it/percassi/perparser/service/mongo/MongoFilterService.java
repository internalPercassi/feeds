package it.percassi.perparser.service.mongo;

import com.mongodb.BasicDBObject;
import it.percassi.perparser.facade.model.AppEnum;
import it.percassi.perparser.service.mongo.model.MongodbFilter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("mongoFilterService")
public class MongoFilterService {

	private final static Logger LOG = LogManager.getLogger(MongoFilterService.class);
	
	public final BasicDBObject buildFilter(List<MongodbFilter> filters, String fileType) throws NoSuchFieldException {
		BasicDBObject query = new BasicDBObject();
		List<BasicDBObject> objList = new ArrayList<BasicDBObject>();
		BasicDBObject subQ = null;
		Class modelClass = getModelClass(fileType);
		for (MongodbFilter filter : filters) {
			subQ = new BasicDBObject();
			Class fieldType = getFieldType(filter.getField(), modelClass);
			if (fieldType.equals(Integer.class)){
				Integer searchValInt = Integer.parseInt(filter.getSearchVal());
				subQ.put(filter.getField(), new BasicDBObject(filter.getSearchOperator(), searchValInt));
			} else if (fieldType.equals(String.class)){
				subQ.put(filter.getField(), new BasicDBObject(filter.getSearchOperator(), filter.getSearchVal()));
			}

			objList.add(subQ);
		}

		if (objList.size() > 1) {
			query.put("$and", objList);
			return query;
		} else if (objList.size() == 1) {
			return subQ;
		}
		return new BasicDBObject();
	}

	public final BasicDBObject buildSort(String sortField, Integer sortType) {
		BasicDBObject sort = null;
		if (sortType != null && sortField != null) {
			sort = new BasicDBObject(sortField, sortType);
		}
		return sort;
	}

	private Class getModelClass(String fileType) {
		Class ret = null;
		AppEnum.FileType e = AppEnum.FileType.getByCode(fileType);
		ret = e.getModelClass();
		return ret;				
	}
	
	private Class getFieldType(String fieldName,Class type) throws NoSuchFieldException{
		Class ret = null;	
		Field field = null;
		try{
		field = type.getDeclaredField(fieldName);	
		} catch(Exception e){
			field = type.getSuperclass().getDeclaredField(fieldName);
		}
		ret = field.getType();
		return ret;	
	}
}
