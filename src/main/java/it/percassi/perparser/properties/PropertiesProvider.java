package it.percassi.perparser.properties;

import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Daniele Sperto
 */
public class PropertiesProvider {

	private final static Logger LOG = LogManager.getLogger(PropertiesProvider.class);
	private static Properties propertiesFile;

	public static String getProperty(String key) throws IOException {
		if (propertiesFile == null) {
			propertiesFile = new Properties();
			propertiesFile.load(PropertiesProvider.class.getClassLoader().getResourceAsStream("app.properties"));
			
			LOG.info("PropertiesFile initialized");
		}
		return propertiesFile.getProperty(key);
	}
}
