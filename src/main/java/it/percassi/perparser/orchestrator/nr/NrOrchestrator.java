package it.percassi.perparser.orchestrator.nr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import it.percassi.perparser.controller.request.GetNewRelicControllerRequest;
import it.percassi.perparser.orchestrator.BaseOrchestrator;
import it.percassi.perparser.service.newrelic.NewRelicServiceResponse;
import it.percassi.perparser.service.newrelic.NrMetricService;

public class NrOrchestrator implements BaseOrchestrator<GetNewRelicControllerRequest, NrOrchestratorResponse> {

	@Autowired
	@Qualifier("nrMetricService")
	private NrMetricService nrMetricService;

	@Override
	public NrOrchestratorResponse execute(GetNewRelicControllerRequest request) {
		
		//TODO
		//call all the services needed than aggregate data 
		/**
		 * The dataStore total time 
		 * Datastore time = 
		 * ( DataStore_average_response_time * DataStore_call_count ) / HttpDispatcher_call_count

		 */
		return null;
	}

	/**
	 * Method that calculate the memcacheTotalTime from NR api call
	 * 
	 * @param memcache The memcacheAverageTime object {@link NewRelicServiceResponse}
	 * @param dataStoreCallCount  The memcacheCallCount object {@link NewRelicServiceResponse}
	 * @param httpDispatcherCallCount The httpDispatcherCallCount object {@link NewRelicServiceResponse}
	 * @return The aggregate object following this formula:
	 * (memcacheAverageTime*memcacheCallCount)/ httpDispatcherCallCount
	 */
	private DataStoreTotalTime memcacheTotalTime(
			NewRelicServiceResponse memcacheAverageTime,
			NewRelicServiceResponse memcacheCallCount,NewRelicServiceResponse httpDispatcherCallCount ){
		return null;
	}


}
