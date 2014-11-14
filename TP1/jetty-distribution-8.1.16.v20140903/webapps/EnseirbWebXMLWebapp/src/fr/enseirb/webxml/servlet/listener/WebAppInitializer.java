package fr.enseirb.webxml.servlet.listener;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import fr.enseirb.webxml.data.dao.DBManager;
import fr.enseirb.webxml.data.xml.XMLMediator;

/**
 * Application Lifecycle Listener implementation class WebAppInitializer
 * 
 */
public class WebAppInitializer implements ServletContextListener {
	private static final Logger LOGGER = Logger.getLogger(WebAppInitializer.class.getName());

	public static String ROOT;

	/**
	 * Default constructor.
	 */
	public WebAppInitializer() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ROOT = sce.getServletContext().getResource("/").getPath();
			LOGGER.log(Level.INFO, "Root path is: " + ROOT);

			if (Boolean.parseBoolean(sce.getServletContext().getInitParameter("mustInitDB"))) {
				LOGGER.log(Level.INFO, "Initializing DB");

				LOGGER.log(Level.INFO, "Erasing previous data");
				DBManager.INSTANCE.getTaskDAO().deleteAllTasks();
				DBManager.INSTANCE.getUserDAO().deleteAllUSers();

				LOGGER.log(Level.INFO, "Loading new data");
				XMLMediator.parseInitDB("/init/init_db.xml");
			}
		} catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE, "Error while getting root path");
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// DO NOTHING
	}

}
