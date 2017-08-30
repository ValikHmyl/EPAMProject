package by.khmyl.cafe.receiver;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.Part;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.util.PaginationHelper;

/**
 * Processes admin's requests, validate them and if they are valid sends them
 * for further processing.
 */
public abstract class AdminReceiver {

	/**
	 * Open users.
	 *
	 * @param startIndex
	 *            the start index
	 * @param filter
	 *            the filter
	 * @return the pagination helper
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract PaginationHelper<User> openUsers(int startIndex, String filter) throws ReceiverException;

	/**
	 * Ban user.
	 *
	 * @param userId
	 *            the user id
	 * @param userEmail
	 *            the user email
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract void banUser(int userId, String userEmail) throws ReceiverException;

	/**
	 * Activate user.
	 *
	 * @param userId
	 *            the user id
	 * @param userEmail
	 *            the user email
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract void activateUser(int userId, String userEmail) throws ReceiverException;

	/**
	 * Open orders.
	 *
	 * @param startIndex
	 *            the start index
	 * @param filter
	 *            the filter
	 * @return the pagination helper
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract PaginationHelper<Order> openOrders(int startIndex, String filter) throws ReceiverException;

	/**
	 * Open menu.
	 *
	 * @param startIndex
	 *            the start index
	 * @param filter
	 *            the filter
	 * @return the pagination helper
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract PaginationHelper<MenuItem> openMenu(int startIndex, String filter) throws ReceiverException;

	/**
	 * Add item into the menu.
	 *
	 * @param name
	 *            the name of new menu item
	 * @param category
	 *            the category
	 * @param price
	 *            the price
	 * @param portion
	 *            the portion
	 * @param savePath
	 *            the save path for menu image
	 * @param parts
	 *            the parts
	 * @return  {@code true} if successfully added
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract boolean addMenu(String name, String category, BigDecimal price, String portion, String savePath,
			Collection<Part> parts) throws ReceiverException;

	/**
	 * Removes the from menu.
	 *
	 * @param menuId
	 *            the menu id
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract void removeFromMenu(int menuId) throws ReceiverException;

	/**
	 * Return to menu.
	 *
	 * @param menuId
	 *            the menu id
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract void returnToMenu(int menuId) throws ReceiverException;

	/**
	 * Confirm payment.
	 *
	 * @param orderId
	 *            the order id
	 * @param userId
	 *            the user id
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract void confirmPayment(int orderId, int userId) throws ReceiverException;

	/**
	 * Open profile.
	 *
	 * @return map with statistic to show it on page
	 * @throws ReceiverException
	 *             if occurred an error, while processing requests
	 */
	public abstract HashMap<String, Long> openProfile() throws ReceiverException;

}
