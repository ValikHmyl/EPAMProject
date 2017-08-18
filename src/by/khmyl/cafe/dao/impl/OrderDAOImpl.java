package by.khmyl.cafe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import by.khmyl.cafe.dao.MenuDAO;
import by.khmyl.cafe.dao.OrderDAO;
import by.khmyl.cafe.dao.util.OrderStatusType;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

public class OrderDAOImpl extends OrderDAO {
	private static final String SQL_ADD_ORDER = "INSERT INTO cafe.order(user_id,confirm_date,order_date,status) VALUES (?,?,?,?)";
	private static final String SQL_FIND_ORDER_ID = "SELECT id  FROM cafe.order WHERE user_id=? AND order_date=?";
	private static final String SQL_ADD_IN_CART = "INSERT INTO cafe.cart (order_id,menu_id,amount) VALUES (?,?,?)";
	private static final String SQL_FIND_USER_ORDERS = "SELECT * FROM cafe.order WHERE user_id=?";
	private static final String SQL_FIND_CART = "SELECT * FROM cafe.cart WHERE order_id=?";

	@Override
	public void addOrder(int userId, HashMap<MenuItem, Integer> cart, String datetime) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			cn.setAutoCommit(false);
			ps = cn.prepareStatement(SQL_ADD_ORDER);
			ps.setInt(1, userId);
			ps.setString(2, datetime);
			String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
			ps.setString(3, currentDate);
			ps.setString(4, OrderStatusType.ACTIVE.name());
			ps.executeUpdate();
			ps = cn.prepareStatement(SQL_FIND_ORDER_ID);
			ps.setInt(1, userId);
			ps.setString(2, currentDate);
			rs = ps.executeQuery();
			int orderId;
			if (rs.next()) {
				orderId = rs.getInt(1);
				for (MenuItem item : cart.keySet()) {
					ps = cn.prepareStatement(SQL_ADD_IN_CART);
					ps.setInt(1, orderId);
					ps.setInt(2, item.getId());
					ps.setInt(3, cart.get(item));
					ps.executeUpdate();
				}
			}
			cn.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			try {
				cn.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Rollback  error", e);
			}
			throw new DAOException("SQL creating order exception", e);
		} finally {
			try {
				cn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DAOException("Can't set autocommit", e);
			}
			close(cn);
			close(ps);
		}

	}

	@Override
	public ArrayList<Order> findUserOrders(int userId) throws DAOException {
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
				currentOrder.setId(orderSet.getInt(1));
				currentOrder.setUserId(userId);
				currentOrder.setConfirmDate(orderSet.getString(3));
				currentOrder.setOrderDate(orderSet.getString(4));
				currentOrder.setStatus(orderSet.getString(5));
				currentOrder.setCart(cart);
				orders.add(currentOrder);
			}

		} catch (SQLException e) {
			throw new DAOException("", e);
		} finally {
			close(cn);
			close(cartStatement);
			close(orderStatement);
		}

		return orders;
	}

}
