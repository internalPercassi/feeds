package it.percassi.perparser.service.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Daniele Sperto
 */
public abstract class BaseParser<T> {

	public abstract List<T> parse(InputStream stream)  throws IOException;			
}
