package by.khmyl.cafe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.khmyl.cafe.dao.AdminDAO;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

public class AdminDAOImpl extends AdminDAO {
	private static final String SQL_BAN_USER = "UPDATE `cafe`.`user` SET `status`=false WHERE `id`=?";
	private static final String SQL_ACTIVATE_USER = "UPDATE `cafe`.`user` SET `status`=true WHERE `id`=?";
	private static final String SQL_REMOVE_FROM_MENU = "UPDATE `cafe`.`menu` SET `status`=false WHERE `id`=?";
	private static final String SQL_RETURN_TO_MENU = "UPDATE `cafe`.`menu` SET `status`=true WHERE `id`=?";

	@Override
	public void banUser(int userId) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_BAN_USER);
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL ban user exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
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
			close(cn);
			close(ps);
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
			close(cn);
			close(ps);
		}
		
	}

}
