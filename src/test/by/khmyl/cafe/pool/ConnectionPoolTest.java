package test.by.khmyl.cafe.pool;

import org.junit.Test;

import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.BeforeClass;

public class ConnectionPoolTest {
	private static ConnectionPool pool;
	private static int poolSize;

	@BeforeClass
	public static void init() {
		pool = ConnectionPool.getInstance();
		ResourceBundle resource = ResourceBundle.getBundle("resources.db");
		poolSize = Integer.parseInt(resource.getString("db.poolsize"));
	}

	@Test
	public void putConnectionTest() {
		ProxyConnection cn = pool.takeConnection();
		pool.putConnection(cn);
	}

	@Test
	public void initPoolTest() {
		int expected = poolSize;
		int actual = pool.size();
		assertEquals(expected, actual);
	}

	@Test
	public void getInstance() throws Exception {
		assertSame(pool, ConnectionPool.getInstance());
	}

	@Test
	public void destroyPoolTest() {
		pool.destroyPool();
		assertEquals(0, pool.size());
	}
}
