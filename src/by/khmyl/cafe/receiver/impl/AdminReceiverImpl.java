package by.khmyl.cafe.receiver.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.Part;

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

public class AdminReceiverImpl extends AdminReceiver {
	private static final String EMAIL_SUBJECT_BAN = "Ban in the McCafe system";
	private static final String EMAIL_CONTENT_BAN = "<h2>Bad news for you</h2> <p>You were banned in the McCafe system</p> <hr> <p> For more information ask in cafe administration:</p> <p>by email mail@gmail.com</p><p> by phone +375 29 123 34 45</p> ";
	private static final String EMAIL_SUBJECT_ACTIVATE = "Amnesty in McCafe system";
	private static final String EMAIL_CONTENT_ACTIVATE = "<h2>Glad to inform you!</h2> <p>You were amnestied in the McCafe system, next time will be more carefull and responsible.</p> <hr> <p>With respect McCafe administration.</p> <p>email mail@gmail.com</p><p>  phone +375 29 123 34 45</p> ";

	@Override
	public PaginationHelper<User> openUsers(int startIndex, String filter) throws ReceiverException {
		UserDAO dao = new UserDAOImpl();
		PaginationHelper<User> users = new PaginationHelper<>();
		filter = filter.replace("all", "%");
		try {
			users.setItems(dao.findUsers(startIndex, filter));
			users.setAmount(dao.countUsers(filter));

		} catch (DAOException e) {
			throw new ReceiverException("Finding  users exception: " + e.getMessage(), e);
		}
		return users;
	}

	@Override
	public void banUser(int userId, String userEmail) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.banUser(userId);
			MailSender sender = new MailSender(EMAIL_SUBJECT_BAN, EMAIL_CONTENT_BAN, userEmail);
			sender.start();
		} catch (DAOException e) {
			throw new ReceiverException("Ban user exception: " + e.getMessage(), e);
		}
	}

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

	@Override
	public PaginationHelper<Order> openOrders(int startIndex, String filter) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		PaginationHelper<Order> orders = new PaginationHelper<>();
		filter = filter.replace("all", "%");
		try {
			orders.setItems(dao.findOrders(startIndex, filter));
			orders.setAmount(dao.countOrders(filter));
		} catch (DAOException e) {
			throw new ReceiverException("Finding orders exception: " + e.getMessage(), e);
		}

		return orders;
	}

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

	@Override
	public PaginationHelper<MenuItem> openMenu(int startIndex, String filter) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		PaginationHelper<MenuItem> menu = new PaginationHelper<>();
		filter = filter.replace("all", "%");
		try {
			menu.setItems(dao.findFilteredMenu(startIndex, filter));
			menu.setAmount(dao.countMenuItems(filter));
		} catch (DAOException e) {
			throw new ReceiverException("Finding menu exception: " + e.getMessage(), e);
		}
		return menu;
	}

	@Override
	public void removeFromMenu(int menuId) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.removeFromMenu(menuId);
		} catch (DAOException e) {
			throw new ReceiverException("Removing from menu exception: " + e.getMessage(), e);
		}

	}

	@Override
	public void returnToMenu(int menuId) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.returnToMenu(menuId);
		} catch (DAOException e) {
			throw new ReceiverException("Returning to menu exception: " + e.getMessage(), e);
		}
	}

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
