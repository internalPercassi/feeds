package it.percassi.perparser.service.parsers;

import it.percassi.perparser.service.parsers.exception.NotValidFileException;
import it.percassi.perparser.service.parsers.model.GLmodel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("GLParser")
public class GLParser extends BaseParser<GLmodel> {

	private final static Logger LOG = LogManager.getLogger(GLParser.class);
	private static final int ROW_LENGTH = 225;

	@Override
	public List<GLmodel> parse(InputStream stream) throws IOException, NotValidFileException {
		List<GLmodel> ret = new ArrayList<GLmodel>();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		int c = 0;
		while ((line = bufferedReader.readLine()) != null) {
			if (c%10==0){
				isLineValid(line);
			}
			c++;
			GLmodel tmp = new GLmodel();
			tmp.setGL(line.substring(0, 2).trim());
			tmp.setINS(line.substring(2, 5).trim());
			tmp.setPertinencySite(line.substring(5, 15).trim());
			tmp.setPertinencySiteDesc(line.substring(15, 18).trim());
			tmp.setUniqueProductCode(line.substring(28, 64).trim());
			tmp.setDepositor(line.substring(64, 67).trim());
			tmp.setStockedQty(Integer.parseInt(line.substring(150, 160).replaceFirst("^0+(?!$)", "").trim()));
			tmp.setBookedQty(Integer.parseInt(line.substring(163, 173).replaceFirst("^0+(?!$)", "").trim()));
			tmp.setAccountingState(line.substring(176, 191).trim());
			ret.add(tmp);
		}
		return ret;
	}

	@Override
	public void isLineValid(String line) throws NotValidFileException {
		if (line.length() < ROW_LENGTH-10 || line.length() > ROW_LENGTH+10) {
			LOG.trace(line);
			throw new NotValidFileException("Length expected:" + ROW_LENGTH + ", get: " + line.length());
		}
		
		if (!StringUtils.isNumeric(line.substring(150, 160).replaceFirst("^0+(?!$)", "").trim())){
			LOG.trace(line);
			throw new NotValidFileException("StockedQty: expected number, get: " + line.length());
		}
		
		if (!StringUtils.isNumeric(line.substring(163, 173).replaceFirst("^0+(?!$)", "").trim())){
			LOG.trace(line);
			throw new NotValidFileException("BookedQty: expected number, get: " + line.length());
		}
	}

}
