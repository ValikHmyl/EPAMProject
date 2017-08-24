package by.khmyl.cafe.receiver;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.Part;

import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.util.PaginationHelper;

public abstract class AdminReceiver {
	public abstract PaginationHelper<User> openUsers(int startIndex, String filter) throws ReceiverException;

	public abstract void banUser(int userId) throws ReceiverException;

	public abstract void activateUser(int userId) throws ReceiverException;

	public abstract PaginationHelper<Order> openOrders(int startIndex, String filter) throws ReceiverException;
	
	public abstract boolean addMenu(String name, String category,BigDecimal price, String portion,String savePath, Collection<Part> parts) throws ReceiverException;
}
