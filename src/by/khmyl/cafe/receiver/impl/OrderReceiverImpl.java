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

public class OrderReceiverImpl extends OrderReceiver {

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
			throw new ReceiverException("Finding menu item exception", e);
		}
		return menuItem;
	}

	@Override
	public MenuItem deleteFromCart(int itemId, HashMap<MenuItem, Integer> cart) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		MenuItem menuItem = null;
		try {
			menuItem = dao.findMenuItem(itemId);
			cart.remove(menuItem);
		} catch (DAOException e) {
			throw new ReceiverException("Finding menu item exception", e);
		}
		return menuItem;

	}

	@Override
	public boolean makeAnOrder(User user, HashMap<MenuItem, Integer> cart, String datetime) throws ReceiverException {
		if (!Validator.validateDatetime(datetime)) {
			return false;
		}
		OrderDAO dao = new OrderDAOImpl();
		try {
			dao.addOrder(user, cart, datetime);
		} catch (DAOException e) {
			throw new ReceiverException("Making order exception", e);
		}
		return true;
	}

	@Override
	public boolean cancelOrder(int orderId, StringBuilder message) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		Order order = null;
		try {
			order = dao.findOrder(orderId);
			if (Validator.validateDatetime(order.getConfirmDate())) {

				dao.cancelOrder(orderId);
			} else {
				message.append("You can't cancel this order already! Order can't be when there is less then an hour before choosen datetime");
				return false;
			}
		} catch (DAOException e) {
			message.append("Something goes wrong! Try again leter.");
			throw new ReceiverException("Cancel order exception " + e, e);
		}

		return true;
	}

}
