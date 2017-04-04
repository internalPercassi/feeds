package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

	@Test
	public void getAllNewRelicMetrics_success() {

		assertNotNull(NR_URL);
		assertNotNull(restTemplate);

		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		headers.set("X-Api-Key", apiKey);

		final Map<String, String> uriParams = new HashMap<String, String>();

		uriParams.put("names", "Agent/MetricsReported/count");

		final StringBuilder urlToCall = new StringBuilder();
		urlToCall.append(NR_URL).append(FORWARD_SLASH).append(feId).append(endUrl);

		final HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		final LocalDateTime toDateAsDateTime = LocalDateTime.parse(toDate, formatter);
		final LocalDateTime fromDateAsDateTime = LocalDateTime.parse(fromDate, formatter);

		final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlToCall.toString())
				.queryParam("names", "Agent/MetricsReported/count").queryParam("from", fromDateAsDateTime)
				.queryParam("to", toDateAsDateTime).queryParam("summarize", true);

		final URI uri = builder.buildAndExpand(uriParams).toUri();

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

}
