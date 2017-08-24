package by.khmyl.cafe.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import by.khmyl.cafe.dao.MenuDAO;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

/**
 * Creates a connection to database with the help of {@link ConnectionPool}, and
 * realizes a set of requests to database for menu.
 */
public class MenuDAOImpl extends MenuDAO {
	private static final String SQL_SELECT_MENU_BY_CATEGORY = "SELECT menu.id, menu.name, menu.price, menu.category_id,menu.portion, menu.img_name FROM menu JOIN category ON menu.category_id=category.id WHERE category.name LIKE ?";
	private static final String SQL_SELECT_MENU_ITEM = "SELECT * FROM menu WHERE menu.id=?";
	private static final String SQL_ADD_MENU_ITEM = "INSERT INTO menu (name, price, category_id, portion, img_name) VALUES (?, ?, (SELECT id FROM category WHERE name LIKE ?) , ?, ?)";

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.dao.MenuDAO#findMenu(java.lang.String)
	 */
	@Override
	public ArrayList<MenuItem> findMenu(String category) throws DAOException {
		ArrayList<MenuItem> menuList = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_SELECT_MENU_BY_CATEGORY);
			ps.setString(1, category);
			rs = ps.executeQuery();
			while (rs.next()) {
				menuList.add(extractData(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("SQL find menu exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
		}
		return menuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.dao.MenuDAO#findMenuItem(int)
	 */
	@Override
	public MenuItem findMenuItem(int id) throws DAOException {
		MenuItem menuItem = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_SELECT_MENU_ITEM);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				menuItem = extractData(rs);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL find menu item exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
		}
		return menuItem;
	}

	@Override
	public void addMenuItem(String name, String category, BigDecimal price, String portion, String imageName)
			throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_ADD_MENU_ITEM);
			ps.setString(1, name);
			ps.setBigDecimal(2, price);
			ps.setString(3, category);
			ps.setString(4, portion);
			ps.setString(5, imageName);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL add user exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
		}
	}

	private MenuItem extractData(ResultSet rs) throws SQLException {
		MenuItem menuItem = new MenuItem();
		menuItem.setId(rs.getInt(1));
		menuItem.setName(rs.getString(2));
		menuItem.setPrice(rs.getBigDecimal(3));
		menuItem.setCategoryId(rs.getInt(4));
		menuItem.setPortion(rs.getString(5));
		menuItem.setImageName(rs.getString(6));
		return menuItem;
	}
}
