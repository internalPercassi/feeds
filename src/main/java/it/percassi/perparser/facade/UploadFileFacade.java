package it.percassi.perparser.facade;

import it.percassi.perparser.model.AppEnum;
import it.percassi.perparser.service.mongo.MongoService;
import it.percassi.perparser.service.parsers.FacebookProductParser;
import it.percassi.perparser.model.FacebookFeed;
import it.percassi.perparser.model.UploadedFileModel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("uploadFileFacade")
public class UploadFileFacade {

	private final static Logger LOG = LogManager.getLogger(UploadFileFacade.class);

	@Autowired
	@Qualifier("facebookProductParser")
	FacebookProductParser fbParser;
	@Autowired
	MongoService mongoService;

	public String parseAndSaveFacebook(byte[] bytes) throws IOException {				
		InputStream inputStream = new ByteArrayInputStream(bytes);
		UploadedFileModel fileModel = getFacebookFileModel(bytes);
		if (!mongoService.isFileAlreadyUploaded(fileModel.getMd5())){
			List<FacebookFeed> feeds = fbParser.parse(inputStream,fileModel.getMd5());
			mongoService.saveUploadedFileModel(fileModel);
			mongoService.saveFacebookFeed(feeds);
			fileModel.setRowCount(feeds.size());
			mongoService.updatetUploadedFileModel(fileModel);
		}
		return fileModel.getMd5();
	}

	/*todo: spostare questo metodo*/
	private UploadedFileModel getFacebookFileModel(byte[] bytes) throws IOException {
		String md5 = this.getMD5(bytes);
		UploadedFileModel fileModel = new UploadedFileModel();
		fileModel.setDate(new Date());
		fileModel.setMd5(md5);
		fileModel.setRowCount(0);
		fileModel.setType(AppEnum.FeedType.FACEBOOK.getCode());
		return fileModel;
	}

//	public JSONArray getColumnNames(String fileType) {
//		if (StringUtils.equalsIgnoreCase(FacebookProductParser.TYPE, fileType)) {
//			return fbParser.createHeaderList();
//		}
//		return new JSONArray();
//	}
//
//	public String getTitle(String fileType) {
//		if (StringUtils.equalsIgnoreCase(FacebookProductParser.TYPE, fileType)) {
//			return FacebookProductParser.TITLE;
//		}
//		return "TITLE";
//	}

	private String getMD5(byte[] bytes) throws IOException {												
		String md5 = DigestUtils.md5Hex(bytes);				
		return md5;
	}
}
