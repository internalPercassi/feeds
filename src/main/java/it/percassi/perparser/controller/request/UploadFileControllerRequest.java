package it.percassi.perparser.controller.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileControllerRequest {

	private MultipartFile multipartFile;
	
	private String fileType;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	

}
