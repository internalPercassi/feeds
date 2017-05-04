package tests.nr.integration_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.percassi.perparser.model.newrelic.NewRelicDailyModel;
import it.percassi.perparser.model.newrelic.NewRelicResponse;
import it.percassi.perparser.model.newrelic.Values;
import it.percassi.perparser.service.newrelic.NewRelicServiceRequest;
import it.percassi.perparser.service.newrelic.NewRelicServiceResponse;
import it.percassi.perparser.service.newrelic.NrMetricService;
import it.percassi.perparser.utils.PerPortalConstants;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-test-config.xml")
@ImportResource("classpath:log4j2-test.xml")
@PropertySource("classpath:app.properties")
public class NewRelicTest {

	@Value("${nr.url}")
	private String NR_URL;

	@Value("${nr.be.id}")
	private String beId;

	@Value("${nr.fe.id}")
	private String feId;

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

	private final static Logger LOG = LogManager.getLogger(NewRelicTest.class);

	private String nrResponseAsJSON;


	@Test
	public void getHttpDispatcher_call_count_daily_success() {

		/**
		 * public static LocalDateTime of(int year, int month, int
		 * dayOfMonth,int hour, int minute)
		 * 
		 * year - the year to represent,from MIN_YEAR to MAX_YEAR month - the
		 * month-of-year to represent, from 1 (January) to 12 (December)
		 * dayOfMonth - the day-of-month to represent, from 1 to 31 hour - the
		 * hour-of-day to represent, from 0 to 23 minute - the minute-of-hour to
		 * represent, from 0 to 59 second - the second-of-minute to represent,
		 * from 0 to 59
		 */

		final LocalDateTime fromDate = LocalDateTime.now().minusDays(1).toLocalDate().atStartOfDay();
		final LocalDateTime toDate = LocalDateTime.now().toLocalDate().atStartOfDay().minusSeconds(1);
		String metricName = PerPortalConstants.NR_METRICS[0];
		String metricValue = PerPortalConstants.NEW_RELIC_CALL_COUNT_VALUE;

		final NewRelicServiceRequest serviceRequest = new NewRelicServiceRequest(fromDate, toDate, metricName, 0, true,
				metricValue, Integer.valueOf(feId));
		try {
			NewRelicServiceResponse serviceResponse = nrMetricService.getNrMetric(serviceRequest);
			System.out.println("NR response is: " + serviceResponse);
			assertNotNull(serviceResponse);
			assertEquals(200, serviceResponse.getStatusCode());
			assertNotNull(serviceResponse.getNewRelicResponse());
			nrResponseAsJSON = convertToJSONNewRelicResponse(serviceResponse.getNewRelicResponse());
			assertNotNull(nrResponseAsJSON);


		} catch (Exception e) {
			System.err.println("Exception: " + e);
			fail(e.getMessage());
		}
	}

	@Test
	public void getHttpDispatcher_average_resp_time_daily_success() {

		/**
		 * public static LocalDateTime of(int year, int month, int
		 * dayOfMonth,int hour, int minute)
		 * 
		 * year - the year to represent,from MIN_YEAR to MAX_YEAR month - the
		 * month-of-year to represent, from 1 (January) to 12 (December)
		 * dayOfMonth - the day-of-month to represent, from 1 to 31 hour - the
		 * hour-of-day to represent, from 0 to 23 minute - the minute-of-hour to
		 * represent, from 0 to 59 second - the second-of-minute to represent,
		 * from 0 to 59
		 */

		final LocalDateTime fromDate = LocalDateTime.now().minusDays(1).toLocalDate().atStartOfDay();
		final LocalDateTime toDate = LocalDateTime.now().toLocalDate().atStartOfDay().minusSeconds(1);
		String metricName = PerPortalConstants.NR_METRICS[0];

		String metricValue = PerPortalConstants.NEW_RELIC_AVG_RESP_TIME_VALUE;

		final NewRelicServiceRequest serviceRequest = new NewRelicServiceRequest(fromDate, toDate, metricName, 0, true,
				metricValue, Integer.valueOf(feId));
		try {
			NewRelicServiceResponse serviceResponse = nrMetricService.getNrMetric(serviceRequest);
			System.out.println("NR response is: " + serviceResponse);
			assertNotNull(serviceResponse);
			assertEquals(200, serviceResponse.getStatusCode());
			assertNotNull(serviceResponse.getNewRelicResponse());
			nrResponseAsJSON = convertToJSONNewRelicResponse(serviceResponse.getNewRelicResponse());
			assertNotNull(nrResponseAsJSON);

		} catch (Exception e) {
			System.err.println("Exception: " + e);
			fail(e.getMessage());
		}
	}

	@Test
	public void getEndUser_call_count_daily_success() {

		/**
		 * public static LocalDateTime of(int year, int month, int
		 * dayOfMonth,int hour, int minute)
		 * 
		 * year - the year to represent,from MIN_YEAR to MAX_YEAR month - the
		 * month-of-year to represent, from 1 (January) to 12 (December)
		 * dayOfMonth - the day-of-month to represent, from 1 to 31 hour - the
		 * hour-of-day to represent, from 0 to 23 minute - the minute-of-hour to
		 * represent, from 0 to 59 second - the second-of-minute to represent,
		 * from 0 to 59
		 */

		final LocalDateTime fromDate = LocalDateTime.now().minusDays(1).toLocalDate().atStartOfDay();
		final LocalDateTime toDate = LocalDateTime.now().toLocalDate().atStartOfDay().minusSeconds(1);
		String metricName = PerPortalConstants.NR_METRICS[1];

		String metricValue = PerPortalConstants.NEW_RELIC_CALL_COUNT_VALUE;

		final NewRelicServiceRequest serviceRequest = new NewRelicServiceRequest(fromDate, toDate, metricName, 0, true,
				metricValue, Integer.valueOf(feId));
		try {
			NewRelicServiceResponse serviceResponse = nrMetricService.getNrMetric(serviceRequest);
			System.out.println("NR response is: " + serviceResponse);
			assertNotNull(serviceResponse);
			assertEquals(200, serviceResponse.getStatusCode());
			assertNotNull(serviceResponse.getNewRelicResponse());
			nrResponseAsJSON = convertToJSONNewRelicResponse(serviceResponse.getNewRelicResponse());
			assertNotNull(nrResponseAsJSON);

		} catch (Exception e) {
			System.err.println("Exception: " + e);
			fail(e.getMessage());
		}
	}

	@Test
	public void getEndUser_average_resp_time_daily_success() {

		/**
		 * public static LocalDateTime of(int year, int month, int
		 * dayOfMonth,int hour, int minute)
		 * 
		 * year - the year to represent,from MIN_YEAR to MAX_YEAR month - the
		 * month-of-year to represent, from 1 (January) to 12 (December)
		 * dayOfMonth - the day-of-month to represent, from 1 to 31 hour - the
		 * hour-of-day to represent, from 0 to 23 minute - the minute-of-hour to
		 * represent, from 0 to 59 second - the second-of-minute to represent,
		 * from 0 to 59
		 */

		final LocalDateTime fromDate = LocalDateTime.now().minusDays(1).toLocalDate().atStartOfDay();
		final LocalDateTime toDate = LocalDateTime.now().toLocalDate().atStartOfDay().minusSeconds(1);
		String metricName = PerPortalConstants.NR_METRICS[1];

		String metricValue = PerPortalConstants.NEW_RELIC_AVG_RESP_TIME_VALUE;

		final NewRelicServiceRequest serviceRequest = new NewRelicServiceRequest(fromDate, toDate, metricName, 0, true,
				metricValue, Integer.valueOf(feId));
		try {
			NewRelicServiceResponse serviceResponse = nrMetricService.getNrMetric(serviceRequest);
			System.out.println("NR response is: " + serviceResponse);
			assertNotNull(serviceResponse);
			assertEquals(200, serviceResponse.getStatusCode());
			assertNotNull(serviceResponse.getNewRelicResponse());
			nrResponseAsJSON = convertToJSONNewRelicResponse(serviceResponse.getNewRelicResponse());
			assertNotNull(nrResponseAsJSON);

		} catch (Exception e) {
			System.err.println("Exception: " + e);
			fail(e.getMessage());
		}
	}

	private String convertToJSONNewRelicResponse(NewRelicResponse response) throws JsonProcessingException {

		LocalDateTime fromDate = response.getMetricData().getFrom();
		ZonedDateTime zdt = fromDate.atZone(ZoneId.systemDefault());
		Values values = response.getMetricData().getMetrics().get(0).getTimeslices().get(0).getValues();
		Date day = Date.from(zdt.toInstant());

		NewRelicDailyModel newRelicMongoItem = new NewRelicDailyModel();

		boolean isAverageTimeNull = (values.getAverageResponseTime() == 0);
		float summarizeValue = (!isAverageTimeNull) ? values.getAverageResponseTime() : values.getCallCount();
		String valueName = (!isAverageTimeNull) ? PerPortalConstants.NEW_RELIC_AVG_RESP_TIME_VALUE
				: PerPortalConstants.NEW_RELIC_CALL_COUNT_VALUE;

		newRelicMongoItem.setDay(day);
		newRelicMongoItem.setMetricName(response.getMetricData().getMetrics().get(0).getName());
		newRelicMongoItem.setValueName(valueName);
		newRelicMongoItem.setValue(summarizeValue);

		Document mongoDocument = newRelicMongoItem.toBSONDoc();

		LOG.info("newRelicMongoItem: " + mongoDocument.toString());

		return mongoDocument.toJson();
	}
}
