package by.khmyl.cafe.receiver;

import java.util.Collection;

import javax.servlet.http.Part;

import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.util.PaginationHelper;

/**
 * Processes user's requests and validate them and if they are valid sends them
 * for further processing.
 */
public abstract class UserReceiver {

	/**
	 * Processes changing user's password request from client and validates the
	 * input parameter
	 *
	 * @param userId
	 *            if of user
	 * @param newPassword
	 *            new password
	 * @param message
	 *            message with error, if occurs
	 * @return {@code true} if successfully changed password
	 * @throws ReceiverException
	 *             if occurred an error, while processing client's requests
	 */
	public abstract boolean changePassword(int userId, String newPassword, StringBuilder message)
			throws ReceiverException;

	/**
	 * Processes changing user's email request from client and validates the
	 * input parameter
	 * 
	 * @param userId
	 *            id of user
	 * @param newEmail
	 *            new email
	 * @param message
	 *            message with error, if occurs
	 * @return {@code true} if successfully changed email
	 * @throws ReceiverException
	 *             if occurred an error, while processing client's requests
	 */
	public abstract boolean changeEmail(int userId, String newEmail, StringBuilder message) throws ReceiverException;

	/**
	 * Processes changing user's avatar request from client and validates the
	 * input parameter
	 *
	 * @param user
	 *            user
	 * @param savePath
	 *            path for uploading avatars
	 * @param parts
	 *            parts that contain information about file
	 * @throws ReceiverException
	 *             if occurred an error, while processing client's requests
	 */
	public abstract void changeAvatar(User user, String savePath, Collection<Part> parts) throws ReceiverException;

	/**
	 * Processes request from user for present all his orders
	 *
	 * @param userId
	 *            id of user
	 * @return list of {@link Order}s
	 * @throws ReceiverException
	 *             if occurred an error, while processing client's requests
	 */
	public abstract PaginationHelper<Order> openOrders(int userId, int startIndex,String filter) throws ReceiverException;

	public abstract boolean editOrder(int orderId, String newDatetime) throws ReceiverException;

}
