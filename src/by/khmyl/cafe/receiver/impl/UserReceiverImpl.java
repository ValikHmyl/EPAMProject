package by.khmyl.cafe.receiver.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.Part;

import by.khmyl.cafe.dao.OrderDAO;
import by.khmyl.cafe.dao.UserDAO;
import by.khmyl.cafe.dao.impl.OrderDAOImpl;
import by.khmyl.cafe.dao.impl.UserDAOImpl;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.util.EncryptManager;
import by.khmyl.cafe.util.PaginationHelper;
import by.khmyl.cafe.util.UploadHelper;
import by.khmyl.cafe.util.Validator;

/**
 * Realizes validation of user requests and if they are valid sends them for
 * further processing.
 */
public class UserReceiverImpl extends UserReceiver {
	private static final String DEFAULT_AVATAR = "default_avatar.png";

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.receiver.UserReceiver#changePassword(int,
	 * java.lang.String, java.lang.StringBuilder)
	 */
	@Override
	public boolean changePassword(int userId, String newPassword, StringBuilder message) throws ReceiverException {
		boolean isValid = true;
		if (Validator.validatePassword(newPassword)) {
			UserDAO dao = new UserDAOImpl();
			try {
				newPassword = EncryptManager.enñrypt(newPassword);
				dao.changePassword(userId, newPassword);
			} catch (DAOException e) {
				message.append("Something goes wrong! Try again leter.");
				throw new ReceiverException("Changing password exception: " + e.getMessage(), e);
			}
		} else {
			message.append("Can't change password, wrong format of new password!");
			isValid = false;
		}
		return isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.receiver.UserReceiver#changeEmail(int,
	 * java.lang.String, java.lang.StringBuilder)
	 */
	@Override
	public boolean changeEmail(int userId, String newEmail, StringBuilder message) throws ReceiverException {
		boolean isValid = true;
		if (Validator.validateEmail(newEmail)) {
			UserDAO dao = new UserDAOImpl();
			try {
				if (dao.checkEmail(newEmail)) {
					dao.changeEmail(userId, newEmail);
				} else {
					message.append("This email already used, choose another.");
					isValid = false;
				}
			} catch (DAOException e) {
				message.append("Something goes wrong! Try again leter.");
				throw new ReceiverException("Changing email exception: " + e.getMessage(), e);
			}
		} else {
			message.append("Can't change email, wrong email format.");
			isValid = false;
		}
		return isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * by.khmyl.cafe.receiver.UserReceiver#changeAvatar(by.khmyl.cafe.entity.
	 * User, java.lang.String, java.util.Collection)
	 */
	@Override
	public void changeAvatar(User user, String savePath, Collection<Part> parts) throws ReceiverException {
		UserDAO dao = new UserDAOImpl();
		UploadHelper helper = new UploadHelper(savePath);
		try {
			String imageName = null;
			for (Part part : parts) {
				imageName = helper.extractFileName(part);
				if (imageName != null) {
					imageName = helper.checkImageName(imageName);
					File currentAvatar = new File(savePath + File.separator + user.getAvatarImg());
					File defaultAvatar = new File(savePath + File.separator + DEFAULT_AVATAR);
					if (!currentAvatar.equals(defaultAvatar)) {
						currentAvatar.delete();
					}
					part.write(savePath + File.separator + imageName);
				}
			}
			dao.changeAvatar(user.getId(), imageName);
			user.setAvatarImg(imageName);
		} catch (DAOException | IOException e) {
			throw new ReceiverException("Changing avatar exception:" + e.getMessage(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.receiver.UserReceiver#getOrders(int)
	 */
	@Override
	public PaginationHelper<Order> openOrders(int userId, int startIndex, String filter) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		PaginationHelper<Order> orders = new PaginationHelper<>();
		filter=filter.replace("all", "%");
		try {
			orders.setItems(dao.findUserOrders(userId, startIndex, filter));
			orders.setAmount(dao.countUserOrders(userId, filter));
		} catch (DAOException e) {
			throw new ReceiverException("Finding  orders exception: " + e.getMessage(), e);
		}
		return orders;
	}

	@Override
	public boolean editOrder(int orderId, String newDatetime) throws ReceiverException {
		if (!Validator.validateDatetime(newDatetime)) {
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
}
