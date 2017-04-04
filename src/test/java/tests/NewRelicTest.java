package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-test-config.xml")
@ImportResource("classpath:log4j2-test.xml")
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

	private static final String FORWARD_SLASH = "/";
	private static final String fromDate = "2017-03-24 00:00:00";
	private static final String toDate = "2017-03-24 12:00:00";
	private RestTemplate restTemplate = new RestTemplate();
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

		final URI uri = generateUriToCall(urlToCall, fromDateAsDateTime, toDateAsDateTime, uriParams, true);

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
	public void getWebFrontendAverageRespTime_call() {
	
		/**
		 * public static LocalDateTime of(int year, int month, int dayOfMonth,
		 * int hour, int minute)
		 *
		 * year - the year to represent, from MIN_YEAR to MAX_YEAR month - the
		 * month-of-year to represent, from 1 (January) to 12 (December)
		 * dayOfMonth - the day-of-month to represent, from 1 to 31 hour - the
		 * hour-of-day to represent, from 0 to 23 minute - the minute-of-hour to
		 * represent, from 0 to 59 second - the second-of-minute to represent,
		 * from 0 to 59
		 */
		final LocalDateTime fromDate = LocalDateTime.of(2017, 4, 3, 00, 00, 00);
		final LocalDateTime toDate = LocalDateTime.of(2017, 4, 3, 23, 59, 00);
		final HttpHeaders headers = createHttpHeaders(apiKey);
		final MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
		final String urlToCall = createNewRelicUrl();
		final HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		String metrics = "WebFrontend/QueueTime";
		uriParams.add("names", metrics);
		uriParams.add("values", "call_count");
		uriParams.add("values", "average_response_time");

		final URI uri = generateUriToCall(urlToCall, fromDate, toDate, uriParams, true);

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

	private URI generateUriToCall(String urlToCall, LocalDateTime fromDate, LocalDateTime toDate,
			MultiValueMap<String, String> uriParams, boolean isSummarize) {

		final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlToCall).queryParams(uriParams)
				.queryParam("from", fromDate).queryParam("to", toDate);

		if (isSummarize) {
			builder.queryParam("summarize", isSummarize);
		}

		final URI uri = builder.buildAndExpand(uriParams).toUri();
		return uri;

	}
}
