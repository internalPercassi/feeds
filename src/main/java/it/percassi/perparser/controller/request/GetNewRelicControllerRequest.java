package it.percassi.perparser.controller.request;

import java.time.LocalDateTime;

import it.percassi.perparser.orchestrator.BaseRequest;

public class GetNewRelicControllerRequest extends BaseRequest {

	private LocalDateTime fromDate;
	
	private LocalDateTime toDate;

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}
	
	
	
} 
