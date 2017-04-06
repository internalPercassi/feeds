package it.percassi.perparser.facade.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.percassi.perparser.utils.IsoDateSerializer;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;

/**
 *
 * @author Daniele Sperto
 */
public class UploadedFileModel implements Serializable {

	private String md5;
	private String fileName;
	private String type;
//	@JsonSerialize(using = IsoDateSerializer.class)
	private Date date;
	private Integer rowCount;

	public UploadedFileModel() {
	}

	public UploadedFileModel(String fileName, byte[] bytes, String type) throws IOException {
		String md5 = this.getMD5(bytes);
		this.date = new Date();
		this.md5 = md5;
		this.rowCount = 0;
		this.type = type;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

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

	private static String getMD5(byte[] bytes) throws IOException {
		String md5 = DigestUtils.md5Hex(bytes);
		return md5;
	}

	public Document toBSONDoc() {
		Document ret = new Document();
		ret.append("date", this.date);
		ret.append("md5", this.md5);
		ret.append("rowCount", this.rowCount);
		ret.append("type", this.type);
		ret.append("fileName", this.fileName);
		return ret;
	}
}
