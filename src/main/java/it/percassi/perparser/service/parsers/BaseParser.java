package it.percassi.perparser.service.parsers;

import it.percassi.perparser.service.parsers.exception.NotValidFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Daniele Sperto
 */
public abstract class BaseParser<T> {

	public abstract List<T> parse(InputStream stream)  throws IOException,NotValidFileException;
	
	public abstract  void isLineValid(String fields)  throws NotValidFileException;	
}
