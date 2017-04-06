package it.percassi.perparser.utils;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.util.UriComponentsBuilder;

public class PerPortalUtils {

	public static final String NEW_RELIC_API_KEY_HEADER = "X-Api-Key";
	public static final String NEW_RELIC_NAMES="names";
	public static final String NEW_RELIC_VALUES="values";
	public static final String NEW_RELIC_CALL_COUNT_VALUE="call_count";
	public static final String NEW_RELIC_AVG_RESP_TIME_VALUE="average_response_time";

	private static final String SLASH = "/";

	@Value("${nr.api.key}")
	private String apiKey;

	/**
	 * Simple method to generate an error message given a List of
	 * {@link ObjectError}
	 *
	 * @param errors A list of {@link ObjectError}
	 * @return An error string
	 */
	public static String generateErrorMessage(final List<ObjectError> errors) {

		final StringBuilder errorMessage = new StringBuilder();

		for (ObjectError error : errors) {

			errorMessage.append(error.getDefaultMessage());
		}
		return errorMessage.toString();

	}

	/***
	 *
	 * @param urlToCall
	 * @param fromDate
	 *            The starting date as {@link LocalDateTime}
	 * @param toDate
	 *            The ending date as {@link LocalDateTime}
	 * @param uriParams
	 *            the uri params in this form as {@link MultiValueMap}
	 * @param isSummarize
	 *            True if the result must be a summarize of the data
	 * @param period
	 *            The sampling time (in seconds)
	 * @return The uri {@link URI}
	 */
	public static URI generateUriToCall(String urlToCall, LocalDateTime fromDate, LocalDateTime toDate,
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

	public static HttpEntity<String> builHttpEntityNewRelicApi(String apiKey) {
		final HttpEntity<String> entity = new HttpEntity<String>("parameters", createNewRelicHttpHeaders(apiKey));
		return entity;
	}

	public static String createNewRelicUrl(String url, int machineId, String newRelicEndUrl) {
		final StringBuilder urlToCall = new StringBuilder();
		urlToCall.append(url).append(SLASH).append(machineId).append(newRelicEndUrl);
		return urlToCall.toString();
	}

	private static HttpHeaders createNewRelicHttpHeaders(String apiKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		headers.set(NEW_RELIC_API_KEY_HEADER, apiKey);
		return headers;
	}
}
