package it.percassi.perparser.servlet.listeners;

import it.percassi.perparser.repository.BaseRepository;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Daniele Sperto
 */
@WebListener
public class PerParContextListener implements ServletContextListener {

	private final static Logger LOG = LogManager.getLogger(PerParContextListener.class);

	public void contextInitialized(ServletContextEvent sce) {
		System.setProperty("DEBUG.MONGO", "true");
		System.setProperty("DB.TRACE", "true");
		LOG.info("contextInitialized");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			BaseRepository.destroy();
		} catch (Exception e) {
			LOG.error(LOG, e);
		}
		LOG.info("contextDestroyed");
	}

}
