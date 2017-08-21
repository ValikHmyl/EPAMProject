package by.khmyl.cafe.dao;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

/**
 * The Class AbstractDAO.
 */
public abstract class AbstractDAO {
	
	/** The Constant LOGGER. */
	static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class);

	/**
	 * Closing statement.
	 *
	 * @param statement statement
	 */
	public void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, "Closing statement error", e);
			}
		}
	}

	/**
	 * Return connection into pool.
	 *
	 * @param connection connection
	 */
	public void close(ProxyConnection connection) {
		if (connection != null) {
			ConnectionPool.getInstance().putConnection(connection);
		}
	}
}
