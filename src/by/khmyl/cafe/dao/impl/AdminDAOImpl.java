package by.khmyl.cafe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.dao.AdminDAO;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

public class AdminDAOImpl extends AdminDAO {
	private static final Logger LOGGER = LogManager.getLogger(AdminDAOImpl.class);
	private static final String SQL_BAN_USER = "UPDATE `cafe`.`user` SET `status`=false WHERE `id`=?";
	private static final String SQL_ACTIVATE_USER = "UPDATE `cafe`.`user` SET `status`=true WHERE `id`=?";
	private static final String SQL_REMOVE_FROM_MENU = "UPDATE `cafe`.`menu` SET `status`=false WHERE `id`=?";
	private static final String SQL_RETURN_TO_MENU = "UPDATE `cafe`.`menu` SET `status`=true WHERE `id`=?";
	private static final String SQL_CHANGE_ORDER_STATUS = "UPDATE `cafe`.`order` SET `status`=? WHERE `id`=?";
	private static final String SQL_CHANGE_USER_DISCOUNT = "UPDATE `cafe`.`user` SET `discount`=? WHERE `id`=?";

	@Override
	public void banUser(User user) throws DAOException {
		PreparedStatement banStatement = null;
		PreparedStatement discountStatement = null;

		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			cn.setAutoCommit(false);
			banStatement = cn.prepareStatement(SQL_BAN_USER);
			banStatement.setInt(1, user.getId());
			banStatement.executeUpdate();
			discountStatement = cn.prepareStatement(SQL_CHANGE_USER_DISCOUNT);
			discountStatement.setBigDecimal(1, user.getDiscount());
			discountStatement.setInt(2, user.getId());
			discountStatement.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e1) {
				LOGGER.log(Level.ERROR, "Rollback  error", e);
			}
			throw new DAOException("SQL ban user  exception - " + e.getMessage(), e);
		} finally {
			close(banStatement);
			close(discountStatement);
			try {
				cn.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, "Can't set autocommit - " + e.getMessage(), e);
			}
			close(cn);
		}
	}

	@Override
	public void activateUser(int userId) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_ACTIVATE_USER);
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL activate user exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
		}
	}

	@Override
	public void removeFromMenu(int menuItemId) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_REMOVE_FROM_MENU);
			ps.setInt(1, menuItemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL remove menu exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
	}

	@Override
	public void returnToMenu(int menuItemId) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_RETURN_TO_MENU);
			ps.setInt(1, menuItemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL return menu exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
	}

	@Override
	public void confirmPayment(int orderId, User user) throws DAOException {
		ProxyConnection cn = null;
		PreparedStatement statusStatement = null;
		PreparedStatement discountStatement = null;

		try {
			cn = ConnectionPool.getInstance().takeConnection();
			cn.setAutoCommit(false);
			statusStatement = cn.prepareStatement(SQL_CHANGE_ORDER_STATUS);
			statusStatement.setString(1, Constant.TAKEN);
			statusStatement.setInt(2, orderId);
			statusStatement.executeUpdate();
			discountStatement = cn.prepareStatement(SQL_CHANGE_USER_DISCOUNT);
			discountStatement.setBigDecimal(1, user.getDiscount());
			discountStatement.setInt(2, user.getId());
			discountStatement.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e1) {
				LOGGER.log(Level.ERROR, "Rollback  error", e);
			}
			throw new DAOException("SQL change order's status exception - " + e.getMessage(), e);
		} finally {
			close(statusStatement);
			close(discountStatement);
			try {
				cn.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.log(Level.ERROR, "Can't set autocommit - " + e.getMessage(), e);
			}
			close(cn);
		}
	}
}
