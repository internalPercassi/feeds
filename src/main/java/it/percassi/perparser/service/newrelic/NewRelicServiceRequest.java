package it.percassi.perparser.service.newrelic;

import java.time.LocalDateTime;

import it.percassi.perparser.orchestrator.BaseRequest;

public class NewRelicServiceRequest extends BaseRequest {

	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private String metricName;
	private int samplePeriod;
	private boolean isSummarize;
	private String valueParameter;
	private int machineId;

	public NewRelicServiceRequest(LocalDateTime fromDate, LocalDateTime toDate, String metricName, int samplePeriod,
			boolean isSummarize, String valueParameter, int machineId) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.metricName = metricName;
		this.samplePeriod = samplePeriod;
		this.isSummarize = isSummarize;
		this.valueParameter = valueParameter;
		this.machineId = machineId;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public String getMetricName() {
		return metricName;
	}

	public int getSamplePeriod() {
		return samplePeriod;
	}

	public boolean isSummarize() {
		return isSummarize;
	}

	public String getValueParameter() {
		return valueParameter;
	}

	public int getMachineId() {
		return machineId;
	}



}


