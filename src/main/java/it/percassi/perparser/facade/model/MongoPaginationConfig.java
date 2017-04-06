package it.percassi.perparser.facade.model;

import java.io.Serializable;

/**
 *
 * @author Daniele Sperto
 */
public class MongoPaginationConfig implements Serializable{
	private Integer start;
	private Integer length;

	public MongoPaginationConfig(Integer start, Integer length) {
		this.start = start;
		this.length = length;
	}

	public MongoPaginationConfig() {
	}

	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
	
}
