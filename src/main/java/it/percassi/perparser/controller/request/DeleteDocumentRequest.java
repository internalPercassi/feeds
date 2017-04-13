package it.percassi.perparser.controller.request;

import it.percassi.perparser.orchestrator.BaseRequest;

public class DeleteDocumentRequest extends BaseRequest{
	
	private String md5;
	private String fileType;
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
	
	

}
