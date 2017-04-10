package tests.nr.unit_test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.percassi.perparser.model.newrelic.NewRelicResponse;
import it.percassi.perparser.model.newrelic.NrAggregationTotalTime;
import it.percassi.perparser.model.newrelic.Timeslices;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring-test-config.xml")
@ImportResource("classpath:log4j2-test.xml")
@PropertySource("classpath:app.properties")
public class MemcacheTotalTimeTest {

	final String webExternalCallCountPath = "src/test/resources/json_mocks/web_external_call_count.json";
	final String webExternalAverageTimePath = "src/test/resources/json_mocks/web_external_average_time.json";
	final String httpDispatcherPath = "src/test/resources/json_mocks/httpDispatcher_all_count.json";
	
	private final static Logger LOG = LogManager.getLogger(MemcacheTotalTimeTest.class);


	NewRelicResponse webExternalCallCountMockResponse = null;
	NewRelicResponse webExternalAverageTimeMockResponse = null;
	NewRelicResponse httpDispatcherCallCountMockResponse = null;

	FileInputStream webExternalAverageTimeFile = null;
	FileInputStream webExternalCallCountFile = null;
	FileInputStream httpDispatcherFile = null;

	@Before
	public void readMockDataFromFiles() {

		try {
			webExternalAverageTimeFile = new FileInputStream(webExternalAverageTimePath);
			webExternalCallCountFile = new FileInputStream(webExternalCallCountPath);
			httpDispatcherFile = new FileInputStream(httpDispatcherPath);

		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test for calculating Memcache total time with this formula
	 * (webExternalAverageTime*webExternalCallCount)/ httpDispatcherCallCount
	 */
	@Test
	public void memcacheTotalTimeAggregation_success() {

		final ObjectMapper objectMapper = new ObjectMapper();
		
		try {

			webExternalAverageTimeMockResponse = objectMapper.readValue(webExternalAverageTimeFile,
					NewRelicResponse.class);
			webExternalCallCountMockResponse = objectMapper.readValue(webExternalCallCountFile, NewRelicResponse.class);
			httpDispatcherCallCountMockResponse = objectMapper.readValue(httpDispatcherFile, NewRelicResponse.class);

			assertNotNull(webExternalAverageTimeMockResponse);
			assertNotNull(webExternalCallCountMockResponse);
			assertNotNull(httpDispatcherCallCountMockResponse);

			List<Timeslices> webExternalAverageSamples = webExternalAverageTimeMockResponse.getMetricData().getMetrics()
					.get(0).getTimeslices();
			List<Timeslices> webExternalCallCountSamples = webExternalCallCountMockResponse.getMetricData().getMetrics()
					.get(0).getTimeslices();
			List<Timeslices> httpDispatcherSamples = httpDispatcherCallCountMockResponse.getMetricData().getMetrics()
					.get(0).getTimeslices();
			List<NrAggregationTotalTime> memCacheTotalTimes = calcuteTotalTime(webExternalAverageSamples,
					webExternalCallCountSamples, httpDispatcherSamples);
			assertNotNull(memCacheTotalTimes);
			
			memCacheTotalTimes.forEach(item->assertNotNull(item));
			memCacheTotalTimes.forEach(item->System.out.println("Memcache Total time element: "+item));

		} catch (IOException e) {

			fail(e.getMessage());
		}
	}

	private List<NrAggregationTotalTime> calcuteTotalTime(List<Timeslices> averageTimeSamples,
			List<Timeslices> callCountSamples, List<Timeslices> httpDispatcherSamples) {

		final List<NrAggregationTotalTime> memCacheTotalTimeList = new ArrayList<>();
		
		if(averageTimeSamples.size() !=callCountSamples .size()){
			return null;
		}
		

		for (int i = 0; i < httpDispatcherSamples.size(); i++) {

			NrAggregationTotalTime memCacheTotalTime = new NrAggregationTotalTime();
			memCacheTotalTime.setFrom(httpDispatcherSamples.get(i).getFrom());
			memCacheTotalTime.setToDate(httpDispatcherSamples.get(i).getTo());
			memCacheTotalTime.setHttpDispatcherCallCount(httpDispatcherSamples.get(i).getValues().getCallCount());
			memCacheTotalTime
					.setWebExternalAverageTime(averageTimeSamples.get(i).getValues().getAverageResponseTime());
			memCacheTotalTime.setWebExternalCallCount(callCountSamples.get(i).getValues().getCallCount());

			
			if (httpDispatcherSamples.get(i).getValues().getCallCount() != 0) {
				
				LOG.info("webExternal call count {},"
						+ "webExternal average response time {}"
						+"httpDispatcher call count {}"
						,callCountSamples.get(i).getValues().getCallCount()
						,averageTimeSamples.get(i).getValues().getAverageResponseTime()
						,httpDispatcherSamples.get(i).getValues().getCallCount());
				
				memCacheTotalTime.setTotalTime(
						(averageTimeSamples.get(i).getValues().getAverageResponseTime()
						* callCountSamples.get(i).getValues().getCallCount())
						/ httpDispatcherSamples.get(i).getValues().getCallCount());
			} else {
				memCacheTotalTime.setTotalTime(0);
			}

			memCacheTotalTimeList.add(memCacheTotalTime);

		}
		return memCacheTotalTimeList;

	}

}
