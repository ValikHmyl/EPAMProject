package by.khmyl.cafe.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import by.khmyl.cafe.pool.ConnectionPool;

@WebListener
public class ServletListener implements ServletContextListener {
	static Logger LOGGER = LogManager.getLogger(ServletListener.class);
	private ConnectionPool pool;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		pool.destroyPool();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		pool = ConnectionPool.getInstance();
		LOGGER.log(Level.INFO, "Connection pool as successfully initialized.");
	}

}
