package it.percassi.perparser.servlet.listeners;

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
		LOG.info("contextInitialized");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		LOG.info("contextDestroyed");
	}

}
