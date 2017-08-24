package by.khmyl.cafe.pool;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Provides a pool of connections to database. If connection to database is not
 * necessary any more, it goes back to pool.
 * <p>
 * All connections are synchronized.
 */
public class ConnectionPool {
	private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
	private static ConnectionPool instance = null;
	private static ReentrantLock lock = new ReentrantLock();
	private static AtomicBoolean flag = new AtomicBoolean();
	private BlockingQueue<ProxyConnection> pool;

	private ConnectionPool() {
		init();
	}

	/**
	 * Gets the single instance of ConnectionPool.
	 *
	 * @return single instance of ConnectionPool
	 */
	public static ConnectionPool getInstance() {
		if (!flag.get()) {
			lock.lock();
			try {
				if (instance == null) {
					instance = new ConnectionPool();
					flag.set(true);
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	/**
	 * Take connection from the pool.
	 *
	 * @return connection
	 */
	public ProxyConnection takeConnection() {
		ProxyConnection connection = null;
		try {
			connection = pool.take();
		} catch (InterruptedException e) {
			LOGGER.log(Level.ERROR, "Can't take connection from pool: ", e);
		}
		return connection;
	}

	/**
	 * Put connection back into the pool.
	 *
	 * @param connection connection
	 */
	public void putConnection(ProxyConnection connection) {
		try {
			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
			pool.put(connection);
		} catch (InterruptedException e) {
			LOGGER.log(Level.ERROR, "Can't put connection to pool: ", e);
		} catch (SQLException e) {
			LOGGER.log(Level.ERROR, "Can't set autocommit: ", e);
		}
	}

	/**
	 * Destroy pool.
	 */
	public void destroyPool() {
		try {
			int poolSize = pool.size();
			for (int i = 0; i < poolSize; i++) {
				pool.take().realClose();
			}
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver driver = drivers.nextElement();
				DriverManager.deregisterDriver(driver);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.log(Level.ERROR, "Destroy pool error:", e);
		}
	}

	/**
	 * Size of pool.
	 *
	 * @return size of pool
	 */
	public int size() {
		return pool.size();
	}

	private void init() {
		try {
			ResourceBundle resource = ResourceBundle.getBundle("resources.db");
			String url = resource.getString("db.url");
			Properties properties = new Properties();
			properties.put("user", resource.getString("db.user"));
			properties.put("password", resource.getString("db.password"));
			int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			pool = new ArrayBlockingQueue<>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				try {
					pool.add(new ProxyConnection(DriverManager.getConnection(url, properties)));
				} catch (SQLException e) {
					LOGGER.log(Level.ERROR, "Can't create connection ", e);
				}
			}
			if (pool.isEmpty()) {
				LOGGER.log(Level.FATAL, "Can't create db connection pool");
				throw new RuntimeException("Can't create db connection pool");
			}
			int sizeDiff = poolSize - pool.size();
			if (sizeDiff > 0) {
				for (int i = 0; i < sizeDiff; i++) {
					try {
						pool.add(new ProxyConnection(DriverManager.getConnection(url, properties)));
					} catch (SQLException e) {
						LOGGER.log(Level.ERROR, "Can't create connection ", e);
					}
				}
			}
		} catch (MissingResourceException e) {
			LOGGER.log(Level.FATAL, "Can't create db connection pool", e);
			throw new RuntimeException("Can't create db connection pool", e);

		} catch (SQLException e) {
			LOGGER.log(Level.ERROR, "Can't create db connection ", e);
		}

	}
}