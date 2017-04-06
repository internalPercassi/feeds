package tests.nr.integration_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import it.percassi.perparser.service.newrelic.NewRelicServiceRequest;
import it.percassi.perparser.service.newrelic.NewRelicServiceResponse;
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
	
	private final static Logger LOG = LogManager.getLogger(NewRelicTest.class);
	
	@Test
	public void getWebFrontendAverageRespTime_success() {

		/**
		 * public static LocalDateTime of(int year, int month, int dayOfMonth,int hour, int minute)
		 * 
		 * year - the year to represent,from MIN_YEAR to MAX_YEAR 
		 * month - the
		 * month-of-year to represent, from 1 (January) to 12 (December)
		 * dayOfMonth - the day-of-month to represent, from 1 to 31 
		 * hour - the hour-of-day to represent, from 0 to 23 minute - the minute-of-hour to represent, from 0 to 59 
		 * second - the second-of-minute to represent, from 0 to 59
		 */
		final LocalDateTime fromDate = LocalDateTime.of(2017, 4, 3, 00, 00, 00);
		final LocalDateTime toDate = LocalDateTime.of(2017, 4, 3, 23, 59, 00);
		String metricName = "WebFrontend/QueueTime";
		int samplePeriod = 7200;
		final String[] valuesParam = { "average_response_time"};

		NewRelicServiceRequest request = new NewRelicServiceRequest(fromDate, toDate, metricName, false, samplePeriod,
				valuesParam,feId);
		try {
			NewRelicServiceResponse serviceResponse = nrMetricService.getNrMetric(request);
			System.out.println("NR response is: "+serviceResponse);
			assertNotNull(serviceResponse);
			assertEquals(200, serviceResponse.getStatusCode());
			assertNotNull(serviceResponse.getNewRelicResponse());

		} catch (Exception e) {
			System.err.println("Exception: "+e);
			fail(e.getMessage());
		}

	}
}
