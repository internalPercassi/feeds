package it.percassi.perparser.service.newrelic;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import it.percassi.perparser.model.newrelic.NewRelicResponse;
import it.percassi.perparser.service.BaseServiceResponse;

public class NewRelicServiceResponse extends BaseServiceResponse{

	private NewRelicResponse newRelicResponse;

	public NewRelicResponse getNewRelicResponse() {
		return newRelicResponse;
	}

	public NewRelicServiceResponse(String message, int statusCode, NewRelicResponse newRelicResponse) {
		super(message, statusCode);
		this.newRelicResponse = newRelicResponse;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
