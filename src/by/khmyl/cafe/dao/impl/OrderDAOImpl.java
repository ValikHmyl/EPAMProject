package by.khmyl.cafe.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import by.khmyl.cafe.dao.MenuDAO;
import by.khmyl.cafe.dao.OrderDAO;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

/**
 * Creates a connection to database with the help of {@link ConnectionPool}, and
 * realizes a set of requests to database for order.
 */
public class OrderDAOImpl extends OrderDAO {
	private static final int MAX_ON_PAGE = 10;
	private static final String ACTIVE = "active";
	private static final String SQL_CREATE_ORDER = "INSERT INTO `cafe`.`order` (`user_id`, `confirm_date`, `order_date`, `status`) VALUES (?,?,?,?)";
	private static final String SQL_FIND_ORDER_ID = "SELECT `id`  FROM `cafe`.`order` WHERE `user_id`=? AND `order_date`=? ORDER BY `id` DESC";
	private static final String SQL_FIND_ORDER = "SELECT `id`, `user_id`, `confirm_date`, `order_date`, `status`, `total_price`  FROM `cafe`.`order` WHERE `id`=?";
	private static final String SQL_ADD_PRICE = "UPDATE `cafe`.`order` SET `total_price`=? WHERE `id`=?";
	private static final String SQL_ADD_IN_CART = "INSERT INTO `cafe`.`cart` (`order_id`, `menu_id`, `amount`) VALUES (?,?,?)";
	private static final String SQL_FIND_CART = "SELECT `order_id`, `menu_id`, `amount` FROM `cafe`.`cart` WHERE `order_id`=?";
	private static final String SQL_DELETE_ORDER = "DELETE FROM `cafe`.`order` WHERE `id`=?";
	private static final String SQL_DELETE_CART = "DELETE FROM `cafe`.`cart` WHERE `order_id`=?";
	private static final String SQL_COUNT_USER_ORDERS = "SELECT sum(`counts`) FROM (SELECT count(`id`) AS `counts`  FROM `cafe`.`order` GROUP BY `status`, `user_id` HAVING `user_id`=? AND `status` LIKE ?) AS `result`";
	private static final String SQL_FIND_USER_ORDERS = "SELECT `id`, `user_id`, `confirm_date`, `order_date`, `status`, `total_price` FROM `cafe`.`order` WHERE `user_id`=? AND `status` LIKE ? ORDER BY `id` DESC LIMIT ?, ?";
	private static final String SQL_FIND_ORDERS = "SELECT `id`, `user_id`, `confirm_date`, `order_date`, `status`, `total_price` FROM `cafe`.`order` WHERE `status` LIKE ? ORDER BY `id` DESC LIMIT ?, ?";
	private static final String SQL_COUNT_ORDERS = "SELECT sum(`counts`) FROM (SELECT count(`id`) AS `counts`  FROM `cafe`.`order` GROUP BY `status` HAVING `status` LIKE ?) AS `result`";
	private static final String SQL_EDIT_ORDER = "UPDATE `cafe`.`order` SET `confirm_date`=? WHERE `id`=?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.dao.OrderDAO#addOrder(by.khmyl.cafe.entity.User,
	 * java.util.HashMap, java.lang.String)
	 */
	@Override
	public void addOrder(User user, HashMap<MenuItem, Integer> cart, String datetime) throws DAOException {
		PreparedStatement createOrderStatement = null;
		PreparedStatement findOrderStatement = null;
		PreparedStatement cartStatement = null;
		PreparedStatement priceStatement = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			cn.setAutoCommit(false);
			createOrderStatement = cn.prepareStatement(SQL_CREATE_ORDER);
			createOrderStatement.setInt(1, user.getId());
			createOrderStatement.setString(2, datetime);
			String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
			createOrderStatement.setString(3, currentDate);
			createOrderStatement.setString(4, ACTIVE);
			createOrderStatement.executeUpdate();
			findOrderStatement = cn.prepareStatement(SQL_FIND_ORDER_ID);
			findOrderStatement.setInt(1, user.getId());
			findOrderStatement.setString(2, currentDate);
			rs = findOrderStatement.executeQuery();
			int orderId;
			if (rs.next()) {
				orderId = rs.getInt(1);
				BigDecimal totalPrice = new BigDecimal(0);
				for (MenuItem item : cart.keySet()) {
					cartStatement = cn.prepareStatement(SQL_ADD_IN_CART);
					cartStatement.setInt(1, orderId);
					cartStatement.setInt(2, item.getId());
					cartStatement.setInt(3, cart.get(item));
					cartStatement.executeUpdate();
					totalPrice = totalPrice
							.add(item.getPrice().multiply(user.getDiscount().multiply(new BigDecimal(cart.get(item)))));
				}
				priceStatement = cn.prepareStatement(SQL_ADD_PRICE);
				priceStatement.setBigDecimal(1, totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
				priceStatement.setInt(2, orderId);
				priceStatement.executeUpdate();
			}
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Rollback  error", e);
			}
			throw new DAOException("SQL creating order exception - " + e.getMessage(), e);
		} finally {
			try {
				cn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DAOException("Can't set autocommit - " + e.getMessage(), e);
			}
			close(cn);

			close(createOrderStatement);
			close(findOrderStatement);
			close(cartStatement);
			close(priceStatement);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.dao.OrderDAO#findUserOrders(int)
	 */
	@Override
	public ArrayList<Order> findUserOrders(int userId, int startIndex, String filter) throws DAOException {
		PreparedStatement orderStatement = null;
		ResultSet orderSet = null;
		PreparedStatement cartStatement = null;
		ResultSet cartSet = null;
		ProxyConnection cn = null;
		ArrayList<Order> orders = new ArrayList<>();
		try {
			cn = ConnectionPool.getInstance().takeConnection();

			orderStatement = cn.prepareStatement(SQL_FIND_USER_ORDERS);

			orderStatement.setInt(1, userId);
			orderStatement.setString(2, filter);
			orderStatement.setInt(3, startIndex);
			orderStatement.setInt(4, MAX_ON_PAGE);

			orderSet = orderStatement.executeQuery();
			MenuDAO menuDAO = new MenuDAOImpl();
			while (orderSet.next()) {
				Order currentOrder = new Order();
				HashMap<MenuItem, Integer> cart = new HashMap<>();
				int orderId = orderSet.getInt(1);
				cartStatement = cn.prepareStatement(SQL_FIND_CART);
				cartStatement.setInt(1, orderId);
				cartSet = cartStatement.executeQuery();
				while (cartSet.next()) {
					cart.put(menuDAO.findMenuItem(cartSet.getInt(2)), cartSet.getInt(3));
				}
				currentOrder = extractData(orderSet, cn);
				currentOrder.setCart(cart);
				orders.add(currentOrder);
			}

		} catch (SQLException e) {
			throw new DAOException("SQL finding user orders exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(cartStatement);
			close(orderStatement);
		}
		return orders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.dao.OrderDAO#findOrder(int)
	 */
	@Override
	public Order findOrder(int orderId) throws DAOException {
		Order order = new Order();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;

		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_FIND_ORDER);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			if (rs.next()) {
				order = extractData(rs, cn);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL add user exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
		}
		return order;
	}

	@Override
	public ArrayList<Order> findOrders(int startIndex, String filter) throws DAOException {
		PreparedStatement orderStatement = null;
		ResultSet orderSet = null;
		PreparedStatement cartStatement = null;
		ProxyConnection cn = null;
		ArrayList<Order> orders = new ArrayList<>();
		try {
			cn = ConnectionPool.getInstance().takeConnection();

			orderStatement = cn.prepareStatement(SQL_FIND_ORDERS);
			orderStatement.setString(1, filter);
			orderStatement.setInt(2, startIndex);
			orderStatement.setInt(3, MAX_ON_PAGE);
			orderSet = orderStatement.executeQuery();
			while (orderSet.next()) {
				Order currentOrder = new Order();

				currentOrder = extractData(orderSet, cn);
				orders.add(currentOrder);
			}

		} catch (SQLException e) {
			throw new DAOException("SQL finding orders exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(cartStatement);
			close(orderStatement);
		}
		return orders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.dao.OrderDAO#cancelOrder(int)
	 */
	@Override
	public void cancelOrder(int orderId) throws DAOException {
		PreparedStatement orderStatement = null;
		PreparedStatement cartStatement = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			cn.setAutoCommit(false);
			cartStatement = cn.prepareStatement(SQL_DELETE_CART);
			cartStatement.setInt(1, orderId);
			cartStatement.executeUpdate();
			orderStatement = cn.prepareStatement(SQL_DELETE_ORDER);
			orderStatement.setInt(1, orderId);
			orderStatement.executeUpdate();

		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Rollback  error", e);
			}
			throw new DAOException("SQL cancel order exception - " + e.getMessage(), e);
		} finally {
			try {
				cn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DAOException("Can't set autocommit - " + e.getMessage(), e);
			}
			close(cn);

			close(orderStatement);
			close(cartStatement);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.dao.OrderDAO#countOrders(int)
	 */
	@Override
	public int countUserOrders(int userId, String filter) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		int amount = 0;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_COUNT_USER_ORDERS);
			ps.setInt(1, userId);
			ps.setString(2, filter);
			rs = ps.executeQuery();
			if (rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL counting orders exception - " + e.getMessage(), e);

		} finally {
			close(cn);
			close(ps);
		}

		return amount;
	}

	@Override
	public int countOrders(String filter) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		int amount = 0;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_COUNT_ORDERS);
			ps.setString(1, filter);
			rs = ps.executeQuery();
			if (rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL counting orders exception - " + e.getMessage(), e);

		} finally {
			close(cn);
			close(ps);
		}

		return amount;
	}

	@Override
	public void editOrder(int orderId, String newDatetime) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_EDIT_ORDER);
			ps.setString(1, newDatetime);
			ps.setInt(2, orderId);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("SQL edit order exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
		}

	}

	private Order extractData(ResultSet rs, ProxyConnection cn) throws SQLException, DAOException {
		Order order = new Order();
		MenuDAO menuDAO = new MenuDAOImpl();
		HashMap<MenuItem, Integer> cart = new HashMap<>();
		int orderId = rs.getInt(1);
		order.setId(orderId);
		order.setUserId(rs.getInt(2));
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		order.setConfirmDate(dateFormate.format(rs.getTimestamp(3)));
		order.setOrderDate(dateFormate.format(rs.getTimestamp(4)));
		order.setStatus(rs.getString(5).toUpperCase());
		order.setTotalPrice(rs.getBigDecimal(6));
		PreparedStatement cartStatement = cn.prepareStatement(SQL_FIND_CART);
		cartStatement.setInt(1, orderId);
		ResultSet cartSet = cartStatement.executeQuery();
		while (cartSet.next()) {
			cart.put(menuDAO.findMenuItem(cartSet.getInt(2)), cartSet.getInt(3));
		}
		order.setCart(cart);
		return order;

	}

}
