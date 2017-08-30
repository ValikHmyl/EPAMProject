package by.khmyl.cafe.receiver.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.Part;

import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.dao.AdminDAO;
import by.khmyl.cafe.dao.MenuDAO;
import by.khmyl.cafe.dao.OrderDAO;
import by.khmyl.cafe.dao.UserDAO;
import by.khmyl.cafe.dao.impl.AdminDAOImpl;
import by.khmyl.cafe.dao.impl.MenuDAOImpl;
import by.khmyl.cafe.dao.impl.OrderDAOImpl;
import by.khmyl.cafe.dao.impl.UserDAOImpl;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.util.MailSender;
import by.khmyl.cafe.util.PaginationHelper;
import by.khmyl.cafe.util.UploadHelper;
import by.khmyl.cafe.util.Validator;

/**
 * Realizes validation of admin's requests and if they are valid sends them for
 * further processing.
 */
public class AdminReceiverImpl extends AdminReceiver {
	private static final String EMAIL_SUBJECT_BAN = "Ban in the McCafe system";
	private static final String EMAIL_CONTENT_BAN = "<h2>Bad news for you</h2> <p>You were banned in the McCafe system</p> <hr> <p> For more information ask in cafe administration:</p> <p>by email mail@gmail.com</p><p> by phone +375 29 123 34 45</p> ";
	private static final String EMAIL_SUBJECT_ACTIVATE = "Amnesty in McCafe system";
	private static final String EMAIL_CONTENT_ACTIVATE = "<h2>Glad to inform you!</h2> <p>You were amnestied in the McCafe system, next time will be more carefull and responsible.</p> <hr> <p>With respect McCafe administration.</p> <p>email mail@gmail.com</p><p>  phone +375 29 123 34 45</p> ";

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#openUsers(int, java.lang.String)
	 */
	@Override
	public PaginationHelper<User> openUsers(int startIndex, String filter) throws ReceiverException {
		UserDAO dao = new UserDAOImpl();
		PaginationHelper<User> users = new PaginationHelper<>();
		filter = filter.replace(Constant.FILTER_ALL, "%");
		try {
			users.setItems(dao.findUsers(startIndex, filter));
			users.setAmount(dao.countUsers(filter));

		} catch (DAOException e) {
			throw new ReceiverException("Finding  users exception: " + e.getMessage(), e);
		}
		return users;
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#banUser(int, java.lang.String)
	 */
	@Override
	public void banUser(int userId, String userEmail) throws ReceiverException {
		AdminDAO adminDAO = new AdminDAOImpl();
		UserDAO userDAO = new UserDAOImpl();
		try {
			User user = userDAO.findUser(userId);
			user.setDiscount(Constant.RESET_DISCOUNT);
			adminDAO.banUser(user);
			MailSender sender = new MailSender(EMAIL_SUBJECT_BAN, EMAIL_CONTENT_BAN, userEmail);
			sender.start();
		} catch (DAOException e) {
			throw new ReceiverException("Ban user exception: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#activateUser(int, java.lang.String)
	 */
	@Override
	public void activateUser(int userId, String userEmail) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.activateUser(userId);
			MailSender sender = new MailSender(EMAIL_SUBJECT_ACTIVATE, EMAIL_CONTENT_ACTIVATE, userEmail);
			sender.start();
		} catch (DAOException e) {
			throw new ReceiverException("Activate user exception: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#openOrders(int, java.lang.String)
	 */
	@Override
	public PaginationHelper<Order> openOrders(int startIndex, String filter) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		PaginationHelper<Order> orders = new PaginationHelper<>();

		filter = filter.replace(Constant.FILTER_ALL, "%");
		try {
			int amount = dao.countOrders("%");
			ArrayList<Order> orderList = dao.findOrders(0, amount, Constant.ACTIVE);
			for (Order order : orderList) {
				if (!Validator.validateDatetime(order.getConfirmDate(), -1)) {
					dao.changeStatus(order.getId(), Constant.OVERDUE);
					UserDAO userDAO = new UserDAOImpl();
					userDAO.changeDiscount(order.getUserId(), Constant.RESET_DISCOUNT);
				}
			}

			orders.setItems(dao.findOrders(startIndex, Constant.MAX_ON_PAGE, filter));
			orders.setAmount(dao.countOrders(filter));
		} catch (DAOException e) {
			throw new ReceiverException("Finding orders exception: " + e.getMessage(), e);
		}

		return orders;
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#addMenu(java.lang.String, java.lang.String, java.math.BigDecimal, java.lang.String, java.lang.String, java.util.Collection)
	 */
	@Override
	public boolean addMenu(String name, String category, BigDecimal price, String portion, String savePath,
			Collection<Part> parts) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		UploadHelper helper = new UploadHelper(savePath);

		try {
			String imageName = null;
			for (Part part : parts) {
				imageName = helper.extractFileName(part);
				if (imageName != null) {
					imageName = helper.checkImageName(imageName);
					part.write(savePath + File.separator + imageName);
				}
			}
			dao.addMenuItem(name, category, price, portion, imageName);

		} catch (DAOException | IOException e) {
			throw new ReceiverException("Add menu exception:" + e.getMessage(), e);
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#openMenu(int, java.lang.String)
	 */
	@Override
	public PaginationHelper<MenuItem> openMenu(int startIndex, String filter) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		PaginationHelper<MenuItem> menu = new PaginationHelper<>();
		filter = filter.replace(Constant.FILTER_ALL, "%");
		try {
			menu.setItems(dao.findFilteredMenu(startIndex, Constant.MAX_ON_PAGE, filter));
			menu.setAmount(dao.countMenuItems(filter));
		} catch (DAOException e) {
			throw new ReceiverException("Finding menu exception: " + e.getMessage(), e);
		}
		return menu;
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#removeFromMenu(int)
	 */
	@Override
	public void removeFromMenu(int menuId) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.removeFromMenu(menuId);
		} catch (DAOException e) {
			throw new ReceiverException("Removing from menu exception: " + e.getMessage(), e);
		}

	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#returnToMenu(int)
	 */
	@Override
	public void returnToMenu(int menuId) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.returnToMenu(menuId);
		} catch (DAOException e) {
			throw new ReceiverException("Returning to menu exception: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#confirmPayment(int, int)
	 */
	@Override
	public void confirmPayment(int orderId, int userId) throws ReceiverException {
		AdminDAO adminDAO = new AdminDAOImpl();
		UserDAO userDAO = new UserDAOImpl();
		try {
			User user = userDAO.findUser(userId);
			BigDecimal currentDiscount = user.getDiscount();
			if (currentDiscount.compareTo(Constant.MAX_DISCOUNT) == 1) {
				user.setDiscount(currentDiscount.subtract(Constant.INCREASE_DISCOUNT));
			}
			adminDAO.confirmPayment(orderId, user);
		} catch (DAOException e) {
			throw new ReceiverException("Confirming payment exception: " + e.getMessage(), e);
		}

	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.receiver.AdminReceiver#openProfile()
	 */
	@Override
	public HashMap<String, Long> openProfile() throws ReceiverException {
		HashMap<String, Long> generalStatistic = new HashMap<>();
		OrderDAO orderDAO = new OrderDAOImpl();
		UserDAO userDAO = new UserDAOImpl();
		MenuDAO menuDAO = new MenuDAOImpl();
		try {
			ArrayList<Order> orders = orderDAO.findOrders(0, orderDAO.countOrders("%"), "%");
			generalStatistic.put(Constant.TOTAL_ORDERS, (long) orders.size());
			generalStatistic.put(Constant.ACTIVE,
					orders.stream().filter(order -> Constant.ACTIVE.equalsIgnoreCase(order.getStatus())).count());
			generalStatistic.put(Constant.OVERDUE,
					orders.stream().filter(order -> Constant.OVERDUE.equalsIgnoreCase(order.getStatus())).count());
			generalStatistic.put(Constant.TAKEN,
					orders.stream().filter(order -> Constant.TAKEN.equalsIgnoreCase(order.getStatus())).count());

			ArrayList<User> users = userDAO.findUsers(0, "%");
			generalStatistic.put(Constant.TOTAL_USERS, (long) users.size());
			long banned = users.stream().filter(user -> !user.getStatus()).count();
			generalStatistic.put(Constant.BANNED, banned);
			generalStatistic.put(Constant.ACTIVE_USER, users.size() - banned);

			ArrayList<MenuItem> menu = menuDAO.findFilteredMenu(0, menuDAO.countMenuItems("%"), "%");
			generalStatistic.put(Constant.TOTAL_AMOUNT, (long) menu.size());
			long oldMenu = menu.stream().filter(item -> !item.getStatus()).count();
			generalStatistic.put(Constant.OLD_MENU, oldMenu);
			generalStatistic.put(Constant.ACTIVE_MENU, menu.size() - oldMenu);
		} catch (DAOException e) {
			throw new ReceiverException("Opening profile exception: " + e.getMessage(), e);

		}
		return generalStatistic;
	}

}
