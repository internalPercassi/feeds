package it.percassi.perparser.utils;

import java.util.ArrayList;
import java.util.List;

public class PerPortalConstants {

	public static final String NEW_RELIC_API_KEY_HEADER = "X-Api-Key";
	public static final String NEW_RELIC_NAMES="names";
	public static final String NEW_RELIC_VALUES="values";
	public static final String NEW_RELIC_CALL_COUNT_VALUE="call_count";
	public static final String NEW_RELIC_AVG_RESP_TIME_VALUE="average_response_time";
	public static final int SAMPLE_NR_PERIOD = 7200;
	
	public static final List<String> VALID_MIME = new ArrayList<String>();
	static{
		VALID_MIME.add("application/vnd.ms-excel");
		VALID_MIME.add("text/csv");
		VALID_MIME.add("text/plain");
	}
	public static final String[] NR_METRICS ={
			"HttpDispatcher",
			"External/allWeb",
	};
}
