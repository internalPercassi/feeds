package it.percassi.perparser.model;

import java.io.Serializable;

/**
 *
 * @author Daniele Sperto
 */

public class MongodbFilter implements Serializable{
	
	private static final long serialVersionUID = -3757431246194756630L;
	private String field;
	private String searchOperator;
	private String searchVal;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSearchOperator() {
		return searchOperator;
	}

	public void setSearchOperator(String searchOperator) {
		this.searchOperator = searchOperator;
	}	

	public String getSearchVal() {
		return searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}	
		
}
