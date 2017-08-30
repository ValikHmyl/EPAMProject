package by.khmyl.cafe.receiver.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.Part;

import by.khmyl.cafe.constant.Constant;
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
					File defaultAvatar = new File(savePath + File.separator + Constant.DEFAULT_AVATAR);
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
		filter = filter.replace("all", "%");
		try {
			orders.setItems(dao.findUserOrders(userId, startIndex, Constant.MAX_ON_PAGE, filter));
			orders.setAmount(dao.countUserOrders(userId, filter));
		} catch (DAOException e) {
			throw new ReceiverException("Finding  orders exception: " + e.getMessage(), e);
		}
		return orders;
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.UserReceiver#openProfile(int)
	 */
	@Override
	public HashMap<String, Long> openProfile(int userId) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		HashMap<String, Long> orderStatistic = new HashMap<>();
		try {
			ArrayList<Order> orders = dao.findUserOrders(userId, 0, dao.countUserOrders(userId, "%"), "%");
			orderStatistic.put(Constant.ACTIVE,
					orders.stream().filter(x -> Constant.ACTIVE.equalsIgnoreCase(x.getStatus())).count());
			orderStatistic.put(Constant.OVERDUE,
					orders.stream().filter(x -> Constant.OVERDUE.equalsIgnoreCase(x.getStatus())).count());
			orderStatistic.put(Constant.TAKEN,
					orders.stream().filter(x -> Constant.TAKEN.equalsIgnoreCase(x.getStatus())).count());
			orderStatistic.put(Constant.TOTAL_AMOUNT, (long) orders.size());

		} catch (DAOException e) {
			throw new ReceiverException("Opening  profile exception: " + e.getMessage(), e);
		}
		return orderStatistic;
	}

}
