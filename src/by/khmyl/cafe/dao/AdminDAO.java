package by.khmyl.cafe.dao;

import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;

/**
 * Provides a set of available requests to database for admin.
 */
public abstract class AdminDAO extends AbstractDAO {

	/**
	 * Ban user from the system.
	 *
	 * @param user
	 *            the user 
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void banUser(User user) throws DAOException;

	/**
	 * Activate user back to the system.
	 *
	 * @param userId
	 *            the user id
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void activateUser(int userId) throws DAOException;

	/**
	 * Make an item from menu inactive.
	 *
	 * @param menuItemId
	 *            the menu item id
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void removeFromMenu(int menuItemId) throws DAOException;

	/**
	 * Make an item from menu active.
	 *
	 * @param menuItemId
	 *            the menu item id
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void returnToMenu(int menuItemId) throws DAOException;

	/**
	 * Confirm payment for an order.
	 *
	 * @param orderId
	 *            the order id
	 * @param user
	 *            the user
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void confirmPayment(int orderId, User user) throws DAOException;

}
