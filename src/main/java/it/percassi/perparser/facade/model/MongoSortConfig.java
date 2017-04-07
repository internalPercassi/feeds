package it.percassi.perparser.facade.model;

import java.io.Serializable;

/**
 *
 * @author Daniele Sperto
 */
public class MongoSortConfig  implements Serializable{

	private String sortField;
	private Integer sortType;

	public MongoSortConfig() {
	}

	public MongoSortConfig(String sortField, Integer sortType) {
		this.sortField = sortField;
		this.sortType = sortType;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

}
