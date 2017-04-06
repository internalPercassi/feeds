package it.percassi.perparser.facade.model.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Daniele Sperto
 */
public class IsoDateSerializer extends JsonSerializer<Date> {

	private static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	@Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        String isoDate = ISO_DATE_FORMAT.format(value);
        jgen.writeRaw("ISODATE(\"" + isoDate + "\")");
    }
}
