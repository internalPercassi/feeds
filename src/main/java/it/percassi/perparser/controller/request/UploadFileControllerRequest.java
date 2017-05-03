package it.percassi.perparser.controller.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileControllerRequest {

	private MultipartFile uploadedFile;

	private String fileType;

        private String localeCod;
        
	public MultipartFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(MultipartFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

    public String getLocaleCod() {
        return localeCod;
    }

    public void setLocaleCod(String localeCod) {
        this.localeCod = localeCod;
    }
	

}
