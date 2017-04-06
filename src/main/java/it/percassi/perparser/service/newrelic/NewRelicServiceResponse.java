package it.percassi.perparser.service.newrelic;

import it.percassi.perparser.model.newrelic.NewRelicResponse;
import it.percassi.perparser.orchestrator.BaseResponse;

public class NewRelicServiceResponse extends BaseResponse {

	private NewRelicResponse newRelicResponse;

	public NewRelicResponse getNewRelicResponse() {

		return newRelicResponse;
	}

	public NewRelicServiceResponse(NewRelicResponse newRelicResponse) {

		this.newRelicResponse = newRelicResponse;
	}

}
