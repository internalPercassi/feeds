package it.percassi.perparser.model.newrelic;

import java.util.List;

public class NewRelicMapperObject {

	private List<Metrics> metrics;

	public List<Metrics> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<Metrics> metrics) {
		this.metrics = metrics;
	}
}
