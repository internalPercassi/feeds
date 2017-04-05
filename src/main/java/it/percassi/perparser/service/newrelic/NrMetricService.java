package it.percassi.perparser.service.newrelic;

import java.io.IOException;

import org.springframework.web.client.RestClientException;

import it.percassi.perparser.exception.ServiceException;

public interface NrMetricService {

	public NewRelicServiceResponse getNrMetric(NewRelicServiceRequest request)
			throws ServiceException, RestClientException, IOException;


}
