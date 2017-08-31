package by.khmyl.cafe.receiver.impl;

import java.util.HashMap;

import by.khmyl.cafe.dao.MenuDAO;
import by.khmyl.cafe.dao.OrderDAO;
import by.khmyl.cafe.dao.impl.MenuDAOImpl;
import by.khmyl.cafe.dao.impl.OrderDAOImpl;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.OrderReceiver;
import by.khmyl.cafe.util.Validator;

/**
 * Realizes validation of user's order requests and if they are valid sends them
 * for further processing.
 */
public class OrderReceiverImpl extends OrderReceiver {

	/** 
	 * @see by.khmyl.cafe.receiver.OrderReceiver#addToCart(int, int,
	 * java.util.HashMap)
	 */
	@Override
	public MenuItem addToCart(int itemId, int amount, HashMap<MenuItem, Integer> cart) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		MenuItem menuItem = null;
		try {
			menuItem = dao.findMenuItem(itemId);
			if (cart.containsKey(menuItem)) {
				int currentAmount = cart.get(menuItem);
				cart.put(menuItem, currentAmount + amount);
			} else {
				cart.put(menuItem, amount);
			}
		} catch (DAOException e) {
			throw new ReceiverException("Finding menu item exception: " + e.getMessage(), e);
		}
		return menuItem;
	}

	/** 
	 * @see by.khmyl.cafe.receiver.OrderReceiver#deleteFromCart(int,
	 * java.util.HashMap)
	 */
	@Override
	public MenuItem deleteFromCart(int itemId, HashMap<MenuItem, Integer> cart) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		MenuItem menuItem = null;
		try {
			menuItem = dao.findMenuItem(itemId);
			cart.remove(menuItem);
		} catch (DAOException e) {
			throw new ReceiverException("Finding menu item exception: " + e.getMessage(), e);
		}
		return menuItem;

	}

	/** 
	 * @see
	 * by.khmyl.cafe.receiver.OrderReceiver#makeAnOrder(by.khmyl.cafe.entity.User, java.util.HashMap, java.lang.String)
	 */
	@Override
	public boolean makeAnOrder(User user, HashMap<MenuItem, Integer> cart, String datetime) throws ReceiverException {
		if (!Validator.validateDatetime(datetime,1)) {
			return false;
		}
		OrderDAO dao = new OrderDAOImpl();
		try {
			dao.addOrder(user, cart, datetime);
		} catch (DAOException e) {
			throw new ReceiverException("Making order exception: " + e.getMessage(), e);
		}
		return true;
	}

	/** 
	 * @see by.khmyl.cafe.receiver.OrderReceiver#cancelOrder(int,
	 * java.lang.StringBuilder)
	 */
	@Override
	public boolean cancelOrder(int orderId, StringBuilder message) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		Order order = null;
		try {
			order = dao.findOrder(orderId);
			if (Validator.validateDatetime(order.getConfirmDate(),1)) {
				dao.cancelOrder(orderId);
			} else {
				message.append(
						"You can't cancel this order already! Order can't be when there is less then an hour before choosen datetime");
				return false;
			}
		} catch (DAOException e) {
			message.append("Something goes wrong! Try again leter.");
			throw new ReceiverException("Cancel order exception: " + e.getMessage(), e);
		}

		return true;
	}

	/**
	 * @see by.khmyl.cafe.receiver.OrderReceiver#editOrder(int, java.lang.String)
	 */
	@Override
	public boolean editOrder(int orderId, String newDatetime) throws ReceiverException {
		if (!Validator.validateDatetime(newDatetime,1)) {
			return false;
		}
		OrderDAO dao = new OrderDAOImpl();
		try {
			dao.editOrder(orderId, newDatetime);
		} catch (DAOException e) {
			throw new ReceiverException("Editing order exception " + e.getMessage(), e);
		}
		return true;

	}

	/**
	 * @see by.khmyl.cafe.receiver.OrderReceiver#searchOrder(int)
	 */
	@Override
	public Order searchOrder(int orderId) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		Order order = null;
		try {
			order = dao.findOrder(orderId);
		} catch (DAOException e) {
			throw new ReceiverException("Finding order exception: " + e.getMessage(), e);
		}
		return order;
	}
}
