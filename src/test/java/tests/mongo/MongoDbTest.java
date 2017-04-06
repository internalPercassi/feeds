package tests.mongo;

import it.percassi.perparser.controller.request.GetDocumentsRequest;
import it.percassi.perparser.exception.NotValidFilterException;
import it.percassi.perparser.facade.QueryFacade;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Daniele Sperto
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring-test-config.xml", "classpath:spring-test-beans.xml"})
public class MongoDbTest {

	@Autowired
	@Qualifier("queryFacade")
	private QueryFacade queryFacade;

//	@AfterClass
//	public static void inDestroy(){
//		BaseRepository.class
//	}
	private static final String buildFilterString(String field, String searchOperator, String searchVal) {
		return "{\"field\":\"" + field + "\",\"searchOperator\":\"" + searchOperator + "\",\"searchVal\":\"" + searchVal + "\"}";
	}
	
	private static final String buildFilterArrayString(String filter){
		String[] filters = new String[1];
		filters[0] = filter;
		return buildFilterArrayString(filters);
	}
	
	private static final String buildFilterArrayString(String[] filters) {
		String ret = "[";
		for (int i = 0; i < filters.length; i++){
			String filter = filters[i];
			ret += filter;
			if (i != filters.length-1){
				ret += ",";
			}
		}
		ret += "]";
		return ret;
	}

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	

	@Test(expected = NotValidFilterException.class)
	public void testMongoFilterEnum() throws Exception {
		GetDocumentsRequest req = new GetDocumentsRequest();
		req.setCollectionName("GL");
		String filtersStr = buildFilterString("uniqueProductCode", "jjj", "NIST 2IN1 KC0010100100144");//jjj is not valid filter operator, the query must return more than one result
		req.setFilters(buildFilterArrayString(filtersStr));
		JSONObject ret = queryFacade.getDocs(req);
	}
		
	private String uniqueProductCodeVal = "NIST 2IN1 KC0010100100144";
	private String md5Val = "0b73fbf5c848bc39ecb49c18d4fa2352";
	@Test
	public void testMongoFilterEQ() throws Exception {
		GetDocumentsRequest req = new GetDocumentsRequest();
		req.setCollectionName("GL");
		String[] filters = new String[2];
		filters[0] = buildFilterString("uniqueProductCode", "$eq", uniqueProductCodeVal);
		filters[1] = buildFilterString("md5", "$eq", md5Val);
		String filtersStr = buildFilterArrayString(filters);
		req.setFilters(filtersStr);
		JSONObject ret = queryFacade.getDocs(req);
		JSONArray data = (JSONArray)ret.get("data");
		for (int i = 0 ; i < data.size(); i++) {
			org.bson.Document otmp = (org.bson.Document)data.get(i);
			String md5 = (String)otmp.get("md5");
			String uniqueProductCode = (String)otmp.get("uniqueProductCode");
			assertEquals(md5, md5Val);
			assertEquals(uniqueProductCode, uniqueProductCode);
		}
	}
	
	private String stockedQtyVal = "1000";
	@Test
	public void testMongoFilterGT() throws Exception {
		Integer val = Integer.parseInt(stockedQtyVal);
		GetDocumentsRequest req = new GetDocumentsRequest();
		req.setCollectionName("GL");
		String filter = buildFilterString("stockedQty", "$gt", stockedQtyVal);		
		req.setFilters(buildFilterArrayString(filter));
		JSONObject ret = queryFacade.getDocs(req);
		JSONArray data = (JSONArray)ret.get("data");		
		for (int i = 0 ; i < data.size(); i++) {
			org.bson.Document otmp = (org.bson.Document)data.get(i);			
			Integer qty = (Integer)otmp.get("stockedQty");
			assertTrue(qty > val);
		}
	}
		
	@Test
	public void testMongoFilterLT() throws Exception {
		Integer val = Integer.parseInt(stockedQtyVal);
		GetDocumentsRequest req = new GetDocumentsRequest();
		req.setCollectionName("GL");
		String filter = buildFilterString("stockedQty", "$lt", stockedQtyVal);		
		req.setFilters(buildFilterArrayString(filter));
		JSONObject ret = queryFacade.getDocs(req);
		JSONArray data = (JSONArray)ret.get("data");		
		for (int i = 0 ; i < data.size(); i++) {
			org.bson.Document otmp = (org.bson.Document)data.get(i);			
			Integer qty = (Integer)otmp.get("stockedQty");
			assertTrue(qty < val);
		}
	}
	
	
	
	private String dateVal = "2017-04-06 16:48";
	@Test
	public void testMongoFilterDateGT() throws Exception {
		Integer val = Integer.parseInt(stockedQtyVal);
		GetDocumentsRequest req = new GetDocumentsRequest();
		req.setCollectionName("uploadedFile");
		String filter = buildFilterString("date", "$gt", dateVal);		
		req.setFilters(buildFilterArrayString(filter));
		JSONObject ret = queryFacade.getDocs(req);
		JSONArray data = (JSONArray)ret.get("data");		
		for (int i = 0 ; i < data.size(); i++) {
			org.bson.Document otmp = (org.bson.Document)data.get(i);			
			Long dateDb = (Long)otmp.get("date");
			assertTrue(dateDb > val);
		}
	}
}
