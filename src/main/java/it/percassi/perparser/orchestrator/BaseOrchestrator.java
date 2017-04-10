package it.percassi.perparser.orchestrator;

public interface BaseOrchestrator<T extends BaseRequest, U extends BaseResponse> {
	
public U execute(T request);

}
