package it.percassi.perparser.service.newrelic;

import java.time.LocalDateTime;

import it.percassi.perparser.orchestrator.BaseRequest;

public class NewRelicServiceRequest extends BaseRequest {

	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private String[] metricName;
	private int samplePeriod;
	private String[] valueParameter;
	private int machineId;

	public NewRelicServiceRequest(LocalDateTime fromDate, LocalDateTime toDate, String[] metricName, int samplePeriod,
			String[] valueParameter, int machineId) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.metricName = metricName;
		this.samplePeriod = samplePeriod;
		this.valueParameter = valueParameter;
		this.machineId = machineId;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public String[] getMetricName() {
		return metricName;
	}

	public int getSamplePeriod() {
		return samplePeriod;
	}

	public String[] getValueParameter() {
		return valueParameter;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}
}
