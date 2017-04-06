package it.percassi.perparser.facade.model;

import java.io.Serializable;

/**
 *
 * @author Daniele Sperto
 */

public class MongodbFilter implements Serializable{
	private String field;
	private String searchOperator;
	private String searchVal;
	private Class valueType;

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

	public Class getValueType() {
		return valueType;
	}

	public void setValueType(Class valueType) {
		this.valueType = valueType;
	}			
}
