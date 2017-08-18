package by.khmyl.cafe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import by.khmyl.cafe.dao.MenuDAO;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

public class MenuDAOImpl extends MenuDAO {
	private static final String SQL_SELECT_MENU_BY_CATEGORY = "SELECT menu.id, menu.name, menu.price, menu.category_id,menu.portion, menu.img_name FROM menu JOIN category ON menu.category_id=category.id WHERE category.name LIKE ?";
	private static final String SQL_SELECT_MENU_ITEM = "SELECT * FROM menu WHERE menu.id=?";

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
				menuList.add(new MenuItem(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4),
						rs.getString(5),rs.getString(6)));
			}
		} catch (SQLException e) {
			throw new DAOException("SQL add user exception", e);
		} finally {
			close(cn);
			close(ps);
		}
		return menuList;
	}

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
				menuItem = new MenuItem(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4),
						rs.getString(5),rs.getString(6));
			}
		} catch (SQLException e) {
			throw new DAOException("SQL add user exception", e);
		} finally {
			close(cn);
			close(ps);
		}
		return menuItem;
	}

}
