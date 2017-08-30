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
	private static final String SQL_SELECT_MENU_ITEM = "SELECT `id`, `name`, `price`, `category_id`, `portion`, `img_name`, `menu`.`status` FROM `cafe`.`menu` WHERE `menu`.`id`=?";
	private static final String SQL_ADD_MENU_ITEM = "INSERT INTO `cafe`.`menu` (`name`, `price`, `category_id`, `portion`, `img_name`) VALUES (?, ?, (SELECT `id` FROM `cafe`.`category` WHERE `name` LIKE ?) , ?, ?)";
	private static final String SQL_COUNT_FILTERED_MENU_ITEMS = "SELECT sum(`counts`) FROM (SELECT count(`id`) AS `counts`  FROM `cafe`.`menu` GROUP BY `category_id`  HAVING `category_id`=(SELECT `id` FROM `cafe`.`category` WHERE `name` LIKE ?)) AS `result`";
	private static final String SQL_COUNT_ALL_MENU_ITEMS = "SELECT count(`id`) FROM `cafe`.`menu`";
	private static final String SQL_FIND_MENU_BY_CATEGORY = "SELECT `menu`.`id`, `menu`.`name`, `menu`.`price`, `menu`.`category_id`, `menu`.`portion`, `menu`.`img_name`, `menu`.`status` FROM `cafe`.`menu` JOIN `cafe`.`category` ON `menu`.`category_id`=`category`.`id` WHERE `category`.`name` LIKE ? AND `menu`.`status`=true";
	private static final String SQL_FIND_FILTERED_MENU = "SELECT `menu`.`id`, `menu`.`name`, `menu`.`price`, `menu`.`category_id`, `menu`.`portion`, `menu`.`img_name`, `menu`.`status` FROM `cafe`.`menu` WHERE `category_id`= (SELECT `id` FROM `cafe`.`category` WHERE `name` LIKE ?) ORDER BY `id` LIMIT ?, ?";
	private static final String SQL_FIND_ALL_MENU = "SELECT `menu`.`id`, `menu`.`name`, `menu`.`price`, `menu`.`category_id`, `menu`.`portion`, `menu`.`img_name`, `menu`.`status` FROM `cafe`.`menu` ORDER BY `id` LIMIT ?, ?";

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
			ps = cn.prepareStatement(SQL_FIND_MENU_BY_CATEGORY);
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

	@Override
	public ArrayList<MenuItem> findFilteredMenu(int startIndex,int lastIndex, String filter) throws DAOException {
		ArrayList<MenuItem> menuList = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			if (filter.equals("%")) {
				ps = cn.prepareStatement(SQL_FIND_ALL_MENU);
				ps.setInt(1, startIndex);
				ps.setInt(2, lastIndex);
			} else {
				ps = cn.prepareStatement(SQL_FIND_FILTERED_MENU);
				ps.setString(1, filter);
				ps.setInt(2, startIndex);
				ps.setInt(3, lastIndex);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				menuList.add(extractData(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("SQL find filtered menu exception - " + e.getMessage(), e);
		} finally {
			close(cn);
			close(ps);
		}
		return menuList;
	}

	@Override
	public int countMenuItems(String filter) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		int amount = 0;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			if (filter.equals("%")) {
				ps = cn.prepareStatement(SQL_COUNT_ALL_MENU_ITEMS);
			} else {
				ps = cn.prepareStatement(SQL_COUNT_FILTERED_MENU_ITEMS);
				ps.setString(1, filter);

			}
			rs = ps.executeQuery();
			if (rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL counting menu items exception - " + e.getMessage(), e);

		} finally {
			close(cn);
			close(ps);
		}

		return amount;
	}

	private MenuItem extractData(ResultSet rs) throws SQLException {
		MenuItem menuItem = new MenuItem();
		menuItem.setId(rs.getInt(1));
		menuItem.setName(rs.getString(2));
		menuItem.setPrice(rs.getBigDecimal(3));
		menuItem.setCategoryId(rs.getInt(4));
		menuItem.setPortion(rs.getString(5));
		menuItem.setImageName(rs.getString(6));
		menuItem.setStatus(rs.getBoolean(7));
		return menuItem;
	}
}
