package it.percassi.perparser.service.newrelic;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.percassi.perparser.exception.ServiceException;
import it.percassi.perparser.model.newrelic.NewRelicResponse;
import it.percassi.perparser.utils.PerPortalUtils;

@Service
@Qualifier("nrMetricService")
public class NrMetricServiceImpl implements NrMetricService {

	@Value("${nr.url}")
	private String nrUrl;

	@Value("${nr.be.id}")
	private String beId;

	@Value("${nr.fe.id}")
	private String feId;

	@Value("${nr.end.url}")
	private String endUrl;

	@Value("${nr.api.key}")
	private String apiKey;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Override
	public NewRelicServiceResponse getNrMetric(final NewRelicServiceRequest request)
			throws ServiceException, RestClientException, IOException {

		final MultiValueMap<String, String> uriParams = new LinkedMultiValueMap<String, String>();
		final ObjectMapper om = new ObjectMapper();
		NewRelicServiceResponse response = null;


		uriParams.add(PerPortalUtils.NEW_RELIC_NAMES, request.getMetricName());
		uriParams.put(PerPortalUtils.NEW_RELIC_VALUES, Arrays.asList(request.getValueParameter()));

		final String newRelicUrl = PerPortalUtils.createNewRelicUrl(nrUrl, request.getMachineId(), endUrl);
		final HttpEntity<String> newRelicEntity = PerPortalUtils.builHttpEntityNewRelicApi(apiKey);
		final URI uri = PerPortalUtils.generateUriToCall(newRelicUrl,

				request.getFromDate(), request.getToDate(), uriParams, request.isToSummarize(),
				request.getSamplePeriod());

		ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.GET, newRelicEntity, String.class);

		if (res != null && HttpStatus.OK.equals(res.getStatusCode())) {

			NewRelicResponse nrObj = om.readValue(res.getBody(), NewRelicResponse.class);
			response = new NewRelicServiceResponse("Data retrieved successfully", res.getStatusCodeValue(), nrObj);
			return response;
		}
		response = new NewRelicServiceResponse("Service data failed", res.getStatusCodeValue(), new NewRelicResponse());
		return response;
	}

}
