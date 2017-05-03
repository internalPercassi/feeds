package it.percassi.perparser.service.parsers;

import it.percassi.perparser.exception.NotValidFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Daniele Sperto
 */
public abstract class BaseParser<T> {

    public abstract List<T> parse(InputStream stream, Locale locale) throws IOException, NotValidFileException;

}
