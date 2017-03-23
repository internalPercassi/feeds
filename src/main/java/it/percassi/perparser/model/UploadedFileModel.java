package it.percassi.perparser.model;

import java.util.Date;

/**
 *
 * @author Daniele Sperto
 */
public class UploadedFileModel {

	private String md5;
	private String type;
	private Date date;
	private Integer rowCount;

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
		
}
