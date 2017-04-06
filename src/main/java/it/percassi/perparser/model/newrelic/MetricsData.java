package it.percassi.perparser.model.newrelic;

import java.time.LocalDateTime;
import java.util.List;

public class MetricsData {

	private List<String> metrics_not_found;

	private List<String> metrics_found;

	private LocalDateTime from;

	private LocalDateTime to;

	private List<Metrics> metrics;

	public List<String> getMetrics_not_found() {
		return metrics_not_found;
	}

	public void setMetrics_not_found(List<String> metrics_not_found) {
		this.metrics_not_found = metrics_not_found;
	}

	public List<String> getMetrics_found() {
		return metrics_found;
	}

	public void setMetrics_found(List<String> metrics_found) {
		this.metrics_found = metrics_found;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}

	public List<Metrics> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<Metrics> metrics) {
		this.metrics = metrics;
	}
}
