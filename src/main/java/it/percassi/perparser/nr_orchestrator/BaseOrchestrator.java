package it.percassi.perparser.nr_orchestrator;

public interface BaseOrchestrator<T extends BaseRequest, U extends BaseResponse> {
	
public U execute(T request);

}
