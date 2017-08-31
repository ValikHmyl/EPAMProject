package by.khmyl.cafe.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.dao.UserDAO;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.pool.ConnectionPool;
import by.khmyl.cafe.pool.ProxyConnection;

/**
 * Creates a connection to database with the help of {@link ConnectionPool}, and
 * realizes a set of requests to database for user.
 */
public class UserDAOImpl extends UserDAO {
	private static final String SQL_FIND_USER_BY_NAME = "SELECT `id`, `username`, `password`, `email`, `role`, `status`, `discount`, `avatar_img_name` FROM `cafe`.`user` WHERE `username`=?";
	private static final String SQL_FIND_USER = "SELECT `id`, `username`, `password`, `email`, `role`, `status`, `discount`, `avatar_img_name` FROM `cafe`.`user` WHERE `id`=?";
	private static final String SQL_FIND_EMAIL = "SELECT `email` FROM `cafe`.`user` WHERE `email`=?";
	private static final String SQL_ADD_USER = "INSERT INTO `cafe`.`user` (`username`, `password`, `email`) VALUES(?,?,?)";
	private static final String SQL_CHANGE_PASSWORD = "UPDATE `cafe`.`user` SET `password`=? WHERE `id`=?";
	private static final String SQL_CHANGE_EMAIL = "UPDATE `cafe`.`user` SET `email`=? WHERE `id`=?";
	private static final String SQL_CHANGE_AVATAR = "UPDATE `cafe`.`user` SET `avatar_img_name`=? WHERE `id`=?";
	private static final String SQL_COUNT_USERS = "SELECT sum(`counts`) FROM (SELECT count(`id`) AS `counts`  FROM `cafe`.`user` GROUP BY `status`, `role` HAVING `status` LIKE ? AND `role`=false) AS `result`";
	private static final String SQL_FIND_USERS = "SELECT `id`, `username`, `password`, `email`, `role`, `status`, `discount`, `avatar_img_name` FROM `user` WHERE `status` LIKE ? AND `role`=false ORDER BY `id` LIMIT ?, ?";
	private static final String SQL_CHANGE_DISCOUNT = "UPDATE `cafe`.`user` SET `discount`=? WHERE `id`=?";

	/**
	 * @see by.khmyl.cafe.dao.UserDAO#findUserByName(java.lang.String)
	 */
	@Override
	public User findUserByName(String username) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		User user = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_FIND_USER_BY_NAME);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = extractData(rs);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL finding user exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
		return user;
	}

	/**
	 * @see by.khmyl.cafe.dao.UserDAO#findUser(int)
	 */
	@Override
	public User findUser(int userId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		User user = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_FIND_USER);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = extractData(rs);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL finding user exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
		return user;
	}

	/** 
	 * @see by.khmyl.cafe.dao.UserDAO#addUser(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void addUser(String username, String password, String email) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_ADD_USER);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL add user exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
	}

	/** 
	 * @see by.khmyl.cafe.dao.UserDAO#checkEmail(java.lang.String)
	 */
	@Override
	public boolean checkEmail(String email) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		boolean isFree = true;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_FIND_EMAIL);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				isFree = false;
			}
		} catch (SQLException e) {
			throw new DAOException("SQL add user exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
		return isFree;
	}

	/**
	 * Change password.
	 *
	 * @param userId the user id
	 * @param newPassword the new password
	 * @throws DAOException the DAO exception
	 * @see by.khmyl.cafe.dao.UserDAO#changePassword(int, java.lang.String)
	 */
	@Override
	public void changePassword(int userId, String newPassword) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_CHANGE_PASSWORD);
			ps.setString(1, newPassword);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL changing password exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
	}

	/** 
	 * @see by.khmyl.cafe.dao.UserDAO#changeEmail(int, java.lang.String)
	 */
	@Override
	public void changeEmail(int userId, String newEmail) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_CHANGE_EMAIL);
			ps.setString(1, newEmail);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL changing email exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
	}

	/**
	 * @see by.khmyl.cafe.dao.UserDAO#changeAvatar(int, java.lang.String)
	 */
	@Override
	public void changeAvatar(int userId, String newAvatarImgName) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_CHANGE_AVATAR);
			ps.setString(1, newAvatarImgName);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL changing avatar exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
	}

	/**
	 * @see by.khmyl.cafe.dao.UserDAO#findUsers(int, java.lang.String)
	 */
	@Override
	public ArrayList<User> findUsers(int startIndex, String filter) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		ResultSet rs = null;
		ArrayList<User> users = new ArrayList<>();
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_FIND_USERS);
			ps.setString(1, filter);
			ps.setInt(2, startIndex);
			ps.setInt(3, Constant.MAX_ON_PAGE);
			rs = ps.executeQuery();
			while (rs.next()) {
				users.add(extractData(rs));
			}
		} catch (SQLException e) {
			throw new DAOException("SQL finding users exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
		return users;
	}

	/**
	 * @see by.khmyl.cafe.dao.UserDAO#countUsers(java.lang.String)
	 */
	@Override
	public int countUsers(String filter) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		int amount = 0;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_COUNT_USERS);
			ps.setString(1, filter);
			rs = ps.executeQuery();
			if (rs.next()) {
				amount = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException("SQL counting users exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}
		return amount;
	}

	/**
	 * @see by.khmyl.cafe.dao.UserDAO#changeDiscount(int, java.math.BigDecimal)
	 */
	@Override
	public void changeDiscount(int userId, BigDecimal discount) throws DAOException {
		PreparedStatement ps = null;
		ProxyConnection cn = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_CHANGE_DISCOUNT);
			ps.setBigDecimal(1, discount);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("SQL changing discount exception - " + e.getMessage(), e);
		} finally {
			close(ps);
			close(cn);
		}

	}

	private User extractData(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setUsername(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setEmail(rs.getString(4));
		user.setRole(rs.getBoolean(5));
		user.setStatus(rs.getBoolean(6));
		user.setDiscount(rs.getBigDecimal(7));
		user.setAvatarImg(rs.getString(8));

		return user;
	}
}
