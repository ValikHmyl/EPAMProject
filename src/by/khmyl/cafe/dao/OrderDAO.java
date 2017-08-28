package by.khmyl.cafe.dao;

import java.util.ArrayList;
import java.util.HashMap;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;

/**
 * Provides a set of available requests to database for order.
 */
public abstract class OrderDAO extends AbstractDAO {

	/**
	 * Find concrete order by id.
	 *
	 * @param orderId
	 *            id of order
	 * @return Object, that encapsulated information about order
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract Order findOrder(int orderId) throws DAOException;

	/**
	 * Add new user's order in database.
	 *
	 * @param user
	 *            user
	 * @param cart
	 *            cart with menu items
	 * @param datetime
	 *            date that user chosen for pick up his order
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void addOrder(User user, HashMap<MenuItem, Integer> cart, String datetime) throws DAOException;

	public abstract ArrayList<Order> findOrders(int startIndex, int lastIndex, String filter) throws DAOException;

	/**
	 * Find all user's orders.
	 *
	 * @param userId
	 *            id of user
	 * @param startIndex
	 *            the start index
	 * @return list of all user's {@link Order}s
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract ArrayList<Order> findUserOrders(int userId, int startIndex, int lastIndex, String filter)
			throws DAOException;

	/**
	 * Count user's orders .
	 *
	 * @param userId
	 *            id of user
	 * @return amount of orders
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract int countUserOrders(int userId, String filter) throws DAOException;

	public abstract int countOrders(String filter) throws DAOException;

	/**
	 * Cancel user's order.
	 *
	 * @param orderId
	 *            id of order
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void cancelOrder(int orderId) throws DAOException;

	public abstract void editOrder(int orderId, String newDatetime) throws DAOException;

	public abstract void changeStatus(int orderId, String status) throws DAOException;

}
