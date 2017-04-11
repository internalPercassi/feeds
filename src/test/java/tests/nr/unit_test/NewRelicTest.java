package tests.nr.unit_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import it.percassi.perparser.model.newrelic.NewRelicResponse;
import it.percassi.perparser.service.newrelic.NrMetricService;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-test-config.xml")
@ImportResource("classpath:log4j2-test.xml")
@PropertySource("classpath:app.properties")
public class NewRelicTest {

	@Value("${nr.url}")
	private String NR_URL;

	@Value("${nr.be.id}")
	private int beId;

	@Value("${nr.fe.id}")
	private int feId;

	@Value("${nr.end.url}")
	private String endUrl;

	@Value("${nr.api.key}")
	private String apiKey;

	@Value("${mongoDB.DBname}")
	private String mongoDBName;
	@Value("${mongoDB.URI}")
	private String mongoDBUri;

	@Autowired
	@Qualifier("nrMetricService")
	private NrMetricService nrMetricService;

	private static final String FORWARD_SLASH = "/";
	private static final String fromDate = "2017-03-24 00:00:00";
	private static final String toDate = "2017-03-24 12:00:00";
	private RestTemplate restTemplate = new RestTemplate();
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	private final static Logger LOG = LogManager.getLogger(NewRelicTest.class);


	@Test
	public void getAllNewRelicMetrics_success() {

		assertNotNull(NR_URL);
		assertNotNull(restTemplate);

		final HttpHeaders headers = createHttpHeaders(apiKey);

		final MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();

		final String urlToCall = createNewRelicUrl();

		final HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		final LocalDateTime toDateAsDateTime = LocalDateTime.parse(toDate, DATE_FORMATTER);
		final LocalDateTime fromDateAsDateTime = LocalDateTime.parse(fromDate, DATE_FORMATTER);

		final String metrics = "Agent/MetricsReported/count";

		uriParams.add("names", metrics);
		uriParams.add("values", "call_count");
		uriParams.add("values", "average_response_time");

		assertTrue("Fail", uriParams.size() >= 2);

		final URI uri = generateUriToCall(urlToCall, fromDateAsDateTime, toDateAsDateTime, uriParams, true, 0);

		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertNotNull(response.getBody());
			assertNotEquals(0, response.getBody().length());

		} catch (Exception e) {
			System.err.println("Exception occured " + e.getMessage());
			fail("argh");

		}

	}

	@Test
	public void saveJsonToMongoDB_success() {
		
		final MongoClientURI mcu = new MongoClientURI(mongoDBUri);
		final MongoClient mc = new MongoClient(mcu);
		final MongoDatabase mdb = mc.getDatabase(mongoDBName);

		String nrCollection = "new_relic";

		try {

			final File file = new File("src/test/resources/json_mocks/mock_new_relic.json");
			final String jsonAsString = FileUtils.readFileToString(file, Charset.defaultCharset());

			Document doc = Document.parse(jsonAsString);
			mdb.getCollection(nrCollection).insertMany(Arrays.asList(doc));
			Long rowCount = mdb.getCollection(nrCollection).count();

			assertNotNull(rowCount);
			assertTrue(rowCount > 0);

		} catch (Exception e) {
			fail("Exception... " + e.getMessage());
		} finally {
			mc.close();
		}

	}

	/**
	 * Dummy test to convert properly a json
	 */
	@Test
	public void jsonConvertion_success() {

		final File file = new File("src/test/resources/json_mocks/WebFrontend_queueTime_mock.json");
		try {
			final ObjectMapper om = new ObjectMapper();

			final NewRelicResponse nrObj = om.readValue(file, NewRelicResponse.class);

			LOG.info("NewRelicResponse "+nrObj.toString());
			LOG.info("Test end");
			assertNotNull(nrObj);
			assertTrue(nrObj.getMetricData().getMetrics_found().size() > 0);
			assertTrue(nrObj.getMetricData().getMetrics_not_found().size()==0);
			assertTrue(nrObj.getMetricData().getMetrics_found().get(0).contains("WebFrontend/QueueTime"));

		} catch (IOException e) {
			LOG.error("Exception: "+e);
			fail("Exception occured");
		}
	}

	private String createNewRelicUrl() {
		final StringBuilder urlToCall = new StringBuilder();
		urlToCall.append(NR_URL).append(FORWARD_SLASH).append(feId).append(endUrl);
		return urlToCall.toString();
	}

	private HttpHeaders createHttpHeaders(String apiKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		headers.set("X-Api-Key", apiKey);
		return headers;
	}

	/**
	 * Simple method to build the proper url to call nr
	 * 
	 * @param urlToCall
	 * @param fromDate
	 * @param toDate
	 * @param uriParams
	 * @param isSummarize
	 * @param period
	 * @return
	 */
	private URI generateUriToCall(String urlToCall, LocalDateTime fromDate, LocalDateTime toDate,
			MultiValueMap<String, String> uriParams, boolean isSummarize, int period) {

		final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlToCall).queryParams(uriParams)
				.queryParam("from", fromDate).queryParam("to", toDate);

		if (!ObjectUtils.isEmpty(isSummarize) && isSummarize) {
			builder.queryParam("summarize", isSummarize);
		}
		if (period != 0) {
			builder.queryParam("period", period);
		}

		final URI uri = builder.buildAndExpand(uriParams).toUri();
		return uri;

	}

}
