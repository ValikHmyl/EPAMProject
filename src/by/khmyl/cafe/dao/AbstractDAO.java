package by.khmyl.cafe.dao;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

public abstract class AbstractDAO {
	static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class);

	public void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, "Closing statement error", e);
			}
		}
	}

	public void close(ProxyConnection cn) {
		if (cn != null) {
			ConnectionPool.getInstance().putConnection(cn);
		}
	}
}
