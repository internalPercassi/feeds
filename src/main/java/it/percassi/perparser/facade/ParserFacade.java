package it.percassi.perparser.facade;

import it.percassi.perparser.facade.model.AppEnum;
import it.percassi.perparser.service.parsers.model.BaseModel;
import it.percassi.perparser.service.mongo.MongoService;
import it.percassi.perparser.service.parsers.model.FacebookFeed;
import it.percassi.perparser.service.parsers.model.GLmodel;
import it.percassi.perparser.service.parsers.model.OSmodel;
import it.percassi.perparser.facade.model.UploadedFileModel;
import it.percassi.perparser.service.parsers.model.WAFModel;
import it.percassi.perparser.service.parsers.BaseParser;
import it.percassi.perparser.service.parsers.exception.NotValidFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

	public String parseAndSave(String fileName, String fileType, byte[] bytes) throws IOException, NotValidFileException {
		InputStream inputStream = new ByteArrayInputStream(bytes);
		UploadedFileModel fileModel = new UploadedFileModel(fileName, bytes, fileType);
		long startTime = System.nanoTime();
		LOG.info("Start parsing of file {} , type is {}, length is {} Bytes", fileName, fileType, bytes.length);
		if (!mongoService.isFileAlreadyUploaded(fileModel.getMd5())) {
			List<BaseModel> feeds = getParser(fileType).parse(inputStream);
			if (feeds.size() > 0) {
				for (BaseModel model : feeds) {
					model.setMd5(fileModel.getMd5());
				}
				mongoService.saveUploadedFileModel(fileModel);
				mongoService.saveDocs(fileType, feeds, fileType);
				fileModel.setRowCount(feeds.size());
				mongoService.updatetUploadedFileModel(fileModel);
			} else {
				LOG.info("Nothing to import for the file {} ", fileName);
			}
		} else {
			LOG.info("File {} is already present with md5 {} ", fileName, fileModel.getMd5());
		}
		long difference = System.nanoTime() - startTime;
		LOG.info("Parsing executed in {} secons",Long.toString(TimeUnit.SECONDS.convert(difference, TimeUnit.NANOSECONDS)));
		return fileModel.getMd5();
	}

	private BaseParser getParser(String fileType) {
		if (AppEnum.FileType.FACEBOOK.getCode().equalsIgnoreCase(fileType)) {
			LOG.info("Using parser {} ", AppEnum.FileType.FACEBOOK.name());
			return fbParser;
		} else if (AppEnum.FileType.GL.getCode().equalsIgnoreCase(fileType)) {
			LOG.info("Using parser {} ", AppEnum.FileType.GL.name());
			return gLParser;
		} else if (AppEnum.FileType.OS.getCode().equalsIgnoreCase(fileType)) {
			LOG.info("Using parser {} ", AppEnum.FileType.OS.name());
			return oSParser;
		} else if (AppEnum.FileType.WAF.getCode().equalsIgnoreCase(fileType)) {
			LOG.info("Using parser {} ", AppEnum.FileType.WAF.name());
			return wafParser;
		}
		return null;
	}

}
