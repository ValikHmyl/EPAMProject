package by.khmyl.cafe.receiver;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.Part;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.util.PaginationHelper;

public abstract class AdminReceiver {
	public abstract PaginationHelper<User> openUsers(int startIndex, String filter) throws ReceiverException;

	public abstract void banUser(int userId, String userEmail) throws ReceiverException;

	public abstract void activateUser(int userId, String userEmail) throws ReceiverException;

	public abstract PaginationHelper<Order> openOrders(int startIndex, String filter) throws ReceiverException;

	public abstract PaginationHelper<MenuItem> openMenu(int startIndex, String filter) throws ReceiverException;

	public abstract boolean addMenu(String name, String category, BigDecimal price, String portion, String savePath,
			Collection<Part> parts) throws ReceiverException;

	public abstract void removeFromMenu(int menuId) throws ReceiverException;

	public abstract void returnToMenu(int menuId) throws ReceiverException;
	
	public abstract Order searchOrder(int orderId) throws ReceiverException;

}
