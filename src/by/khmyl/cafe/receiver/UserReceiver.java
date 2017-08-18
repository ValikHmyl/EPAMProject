package by.khmyl.cafe.receiver;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.Part;

import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;

public abstract class UserReceiver {
	public abstract boolean changePassword(int userId, String newPassword, StringBuilder message)
			throws ReceiverException;

	public abstract boolean changeEmail(int userId, String newEmail, StringBuilder message) throws ReceiverException;

	public abstract void changeAvatar(User user, String savePath, Collection<Part> parts) throws ReceiverException;

	public abstract ArrayList<Order> getOrders(int userId) throws ReceiverException;
}
