package it.percassi.perparser.model.newrelic;

/**
 * 
 * @author Sigolotto
 *
 */
public class Values {

	private int call_count;

	private float average_response_time;

	public int getCall_count() {
		return call_count;
	}

	public void setCall_count(int call_count) {
		this.call_count = call_count;
	}

	public float getAverage_response_time() {
		return average_response_time;
	}

	public void setAverage_response_time(float average_response_time) {
		this.average_response_time = average_response_time;
	}
}
