package it.percassi.perparser.orchestrator.nr;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import it.percassi.perparser.controller.request.GetNewRelicControllerRequest;
import it.percassi.perparser.model.newrelic.NewRelicResponse;
import it.percassi.perparser.model.newrelic.NrAggregationTotalTime;
import it.percassi.perparser.model.newrelic.Timeslices;
import it.percassi.perparser.orchestrator.BaseOrchestrator;
import it.percassi.perparser.service.newrelic.NrMetricService;

public class NrOrchestrator implements BaseOrchestrator<GetNewRelicControllerRequest, NrOrchestratorResponse> {

	@Autowired
	@Qualifier("nrMetricService")
	private NrMetricService nrMetricService;
	
	@Value("${nr.be.id}")
	private int beId;

	@Value("${nr.fe.id}")
	private int feId;

	private final static Logger LOG = LogManager.getLogger(NrOrchestrator.class);

	@Override
	public NrOrchestratorResponse execute(GetNewRelicControllerRequest request) {

		// TODO call all the services needed than aggregate data
		final LocalDateTime fromDate = request.getFromDate();
		final LocalDateTime toDate = request.getToDate();
		

		return null;
	}

	/**
	 * Method that aggregate data samples retrieved using this formula
	 * <b>(AverageTime multiply by webExternalCallCount) divided by
	 * httpDispatcherCallCount</b>
	 * 
	 * @param averageTimeSamples
	 *            The response of NR API contains the average time
	 *            {@link NewRelicResponse}
	 * @param callCountSamples
	 *            The response of NR API contains the call count
	 *            {@link NewRelicResponse}
	 * @param httpDispatcherSamples
	 *            The response of NR API of HttpDispatcher call count
	 *            {@link NewRelicResponse}
	 * @return The aggregateObject @
	 */
	private List<NrAggregationTotalTime> calculateTotalTime(List<Timeslices> averageTimeSamples,
			List<Timeslices> callCountSamples, List<Timeslices> httpDispatcherSamples) {

		final List<NrAggregationTotalTime> memCacheTotalTimeList = new ArrayList<>();

		if (averageTimeSamples.size() != callCountSamples.size()) {
			return null;
		}

		for (int i = 0; i < httpDispatcherSamples.size(); i++) {

			NrAggregationTotalTime aggregationTotalTime = new NrAggregationTotalTime();
			aggregationTotalTime.setFrom(httpDispatcherSamples.get(i).getFrom());
			aggregationTotalTime.setToDate(httpDispatcherSamples.get(i).getTo());
			aggregationTotalTime.setHttpDispatcherCallCount(httpDispatcherSamples.get(i).getValues().getCallCount());
			aggregationTotalTime.setWebExternalAverageTime(averageTimeSamples.get(i).getValues().getAverageResponseTime());
			aggregationTotalTime.setWebExternalCallCount(callCountSamples.get(i).getValues().getCallCount());

			if (httpDispatcherSamples.get(i).getValues().getCallCount() != 0) {

				LOG.info(
						"webExternal call count {}," + "webExternal average response time {}"
								+ "httpDispatcher call count {}",
						callCountSamples.get(i).getValues().getCallCount(),
						averageTimeSamples.get(i).getValues().getAverageResponseTime(),
						httpDispatcherSamples.get(i).getValues().getCallCount());

				aggregationTotalTime.setTotalTime((averageTimeSamples.get(i).getValues().getAverageResponseTime()
						* callCountSamples.get(i).getValues().getCallCount())
						/ httpDispatcherSamples.get(i).getValues().getCallCount());
			} else {
				aggregationTotalTime.setTotalTime(0);
			}

			memCacheTotalTimeList.add(aggregationTotalTime);

		}
		return memCacheTotalTimeList;

	}

	private boolean isAggregationNull(List<Timeslices> aggregationToCheck) {
		return Objects.isNull(aggregationToCheck);
	}
}
