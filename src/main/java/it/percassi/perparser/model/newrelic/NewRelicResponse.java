package it.percassi.perparser.model.newrelic;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NewRelicResponse {

	private List<MetricsData> metrics_data;

	public List<MetricsData> getMetrics_data() {
		return metrics_data;
	}

	public void setMetrics_data(List<MetricsData> metrics_data) {
		this.metrics_data = metrics_data;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	


}
