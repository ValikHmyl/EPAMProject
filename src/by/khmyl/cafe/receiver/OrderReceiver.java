package by.khmyl.cafe.receiver;

import java.util.HashMap;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;

public abstract class OrderReceiver {
	public abstract MenuItem addToCart(int itemId, int amount, HashMap<MenuItem, Integer> cart)
			throws ReceiverException;

	public abstract MenuItem deleteFromCart(int itemId, HashMap<MenuItem, Integer> cart) throws ReceiverException;

	public abstract boolean makeAnOrder(User user, HashMap<MenuItem, Integer> cart, String datetime)
			throws ReceiverException;
}
