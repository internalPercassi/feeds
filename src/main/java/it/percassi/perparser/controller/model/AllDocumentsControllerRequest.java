package it.percassi.perparser.controller.model;

public class AllDocumentsControllerRequest {

	private String length;

	private String start;

	private String[] excludes;

	private String collectionName;

	private String sortField;

	private String sortType;
	
	public AllDocumentsControllerRequest(String length, String start, String[] excludes, String collectionName,
			String sortField, String sortType) {
		super();
		this.length = length;
		this.start = start;
		this.excludes = excludes;
		this.collectionName = collectionName;
		this.sortField = sortField;
		this.sortType = sortType;
	}

	public String getLength() {
		return length;
	}

	public String getStart() {
		return start;
	}

	public String[] getExcludes() {
		return excludes;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public String getSortField() {
		return sortField;
	}

	public String getSortType() {
		return sortType;
	}

}
