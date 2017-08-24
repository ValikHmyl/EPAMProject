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
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.util.PaginationHelper;
import by.khmyl.cafe.util.UploadHelper;

public class AdminReceiverImpl extends AdminReceiver {

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
	public void banUser(int userId) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.banUser(userId);
		} catch (DAOException e) {
			throw new ReceiverException("Ban user exception: " + e.getMessage(), e);
		}
	}

	@Override
	public void activateUser(int userId) throws ReceiverException {
		AdminDAO dao = new AdminDAOImpl();
		try {
			dao.activateUser(userId);
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
		MenuDAO dao=new MenuDAOImpl();
		UploadHelper helper = new UploadHelper(savePath);
		
		//TODO validate
		try{
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

}
