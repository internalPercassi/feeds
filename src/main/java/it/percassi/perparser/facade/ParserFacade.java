package it.percassi.perparser.facade;

import it.percassi.perparser.model.AppEnum;
import it.percassi.perparser.service.parsers.parser.BaseModel;
import it.percassi.perparser.service.mongo.MongoService;
import it.percassi.perparser.service.parsers.parser.FacebookFeed;
import it.percassi.perparser.service.parsers.parser.GLmodel;
import it.percassi.perparser.service.parsers.parser.OSmodel;
import it.percassi.perparser.model.UploadedFileModel;
import it.percassi.perparser.service.parsers.parser.WAFModel;
import it.percassi.perparser.service.parsers.BaseParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("parserFacade")
public class ParserFacade {

	private final static Logger LOG = LogManager.getLogger(ParserFacade.class);

	@Autowired
	@Qualifier("facebookProductParser")
	BaseParser<FacebookFeed> fbParser;
	
	@Autowired
	@Qualifier("GLParser")
	BaseParser<GLmodel> gLParser;
	
	@Autowired
	@Qualifier("OSParser")
	BaseParser<OSmodel> oSParser;
	
	@Autowired
	@Qualifier("wafParser")
	BaseParser<WAFModel> wafParser;
	
	@Autowired
	MongoService mongoService;

	public String parseAndSave(String fileType, byte[] bytes) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(bytes);
		UploadedFileModel fileModel = new UploadedFileModel(bytes, fileType);
		if (!mongoService.isFileAlreadyUploaded(fileModel.getMd5())) {
			List<BaseModel> feeds = getParser(fileType).parse(inputStream);
			for (BaseModel model : feeds) {
				model.setMd5(fileModel.getMd5());
			}
			mongoService.saveUploadedFileModel(fileModel);
			mongoService.saveDocs(fileType,feeds, fileType);
			fileModel.setRowCount(feeds.size());
			mongoService.updatetUploadedFileModel(fileModel);
		}
		return fileModel.getMd5();
	}

	private BaseParser getParser(String fileType) {
		if (AppEnum.FileType.FACEBOOK.getCode().equalsIgnoreCase(fileType)) {
			return fbParser;
		} else if (AppEnum.FileType.GL.getCode().equalsIgnoreCase(fileType)) {
			return gLParser;
		} else if (AppEnum.FileType.OS.getCode().equalsIgnoreCase(fileType)) {
			return oSParser;
		}else if (AppEnum.FileType.WAF.getCode().equalsIgnoreCase(fileType)) {
			return wafParser;
		}
		return null;
	}

}
