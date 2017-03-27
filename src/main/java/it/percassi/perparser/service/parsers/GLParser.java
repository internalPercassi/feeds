package it.percassi.perparser.service.parsers;

import it.percassi.perparser.service.parsers.model.GLmodel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("GLParser")
public class GLParser extends BaseParser<GLmodel> {	

	@Override
	public List<GLmodel> parse(InputStream stream) throws IOException {
		List<GLmodel> ret = new ArrayList<GLmodel>();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			GLmodel tmp = new GLmodel();
			tmp.setGL(line.substring(0, 2));
			tmp.setINS(line.substring(2, 5));
			tmp.setPertinencySite(line.substring(5, 15));
			tmp.setPertinencySiteDesc(line.substring(15, 18));
			tmp.setUniqueProductCode(line.substring(28, 64));
			tmp.setDepositor(line.substring(64, 67));
			tmp.setStockedQty(Integer.parseInt(line.substring(150, 160).replaceFirst("^0+(?!$)", "")));
			tmp.setBookedQty(Integer.parseInt(line.substring(163, 173).replaceFirst("^0+(?!$)", "")));
			tmp.setAccountingState(line.substring(176, 191));
			ret.add(tmp);
		}
		return ret;
	}

}
