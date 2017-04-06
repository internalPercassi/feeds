package it.percassi.perparser.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Daniele Sperto
 */
public class UploadedFileModel {

	private String md5;
	private String fileName;
	private String type;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
	private Date date;
	private Integer rowCount;

	public UploadedFileModel() {
	}
	
	public UploadedFileModel(String fileName,byte[] bytes,String type) throws IOException {
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
}
