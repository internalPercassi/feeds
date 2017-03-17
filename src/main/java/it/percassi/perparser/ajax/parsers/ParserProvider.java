package it.percassi.perparser.parsers;


import it.percassi.perparser.parsers.products.FacebookProductParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniele Sperto
 */
@Service("parserProvider")
public class ParserProvider {

	private static final String NOT_PARSER_FOUND_MSG = "Not Valid Parser Found for parser type: ";
	private static final String TYPE_NULL_MSG = "THe type param is null or blank";
	
	@Autowired
	@Qualifier("GLParser")
	private BaseParser glParser;
	@Autowired
	@Qualifier("OSParser")
	private BaseParser osParser;
	@Autowired
	@Qualifier("FacebookProductParser")
	private BaseParser facebookProductParser;
	
	public BaseParser getParser(String type) throws ClassNotFoundException,IllegalArgumentException{
		if (StringUtils.isBlank(type) ){
			throw new IllegalArgumentException(TYPE_NULL_MSG);
		}
		if (OSParser.TYPE.equalsIgnoreCase(type)){
			return osParser;
		} else if (GLParser.TYPE.equalsIgnoreCase(type)){
			return glParser;
		} else if (FacebookProductParser.TYPE.equalsIgnoreCase(type)){
			return facebookProductParser;
		} else {
			throw new ClassNotFoundException(NOT_PARSER_FOUND_MSG+type);
		}
	}
}
