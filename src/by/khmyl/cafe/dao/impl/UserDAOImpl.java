package by.khmyl.cafe.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private static final String SQL_SELECT_USER = "SELECT * FROM user WHERE username=?";
	private static final String SQL_SELECT_EMAIL = "SELECT email FROM user WHERE email=?";
	private static final String SQL_ADD_USER = "INSERT INTO user (username,password,email) VALUES(?,?,?)";
	private static final String SQL_CHANGE_PASSWORD = "UPDATE user SET password=? WHERE id=?";
	private static final String SQL_CHANGE_EMAIL = "UPDATE user SET email=? WHERE id=?";
	private static final String SQL_CHANGE_AVATAR = "UPDATE user SET avatar_img_name=? WHERE id=?";

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.dao.UserDAO#findUser(java.lang.String)
	 */
	@Override
	public User findUser(String username) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProxyConnection cn = null;
		User user = null;
		try {
			cn = ConnectionPool.getInstance().takeConnection();
			ps = cn.prepareStatement(SQL_SELECT_USER);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5),
						rs.getBoolean(6), rs.getBigDecimal(7), rs.getString(8));
			}
		} catch (SQLException e) {
			throw new DAOException("SQL finding user exception", e);
		} finally {
			close(cn);
			close(ps);
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.dao.UserDAO#addUser(java.lang.String, java.lang.String, java.lang.String)
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
			throw new DAOException("SQL add user exception", e);
		} finally {
			close(cn);
			close(ps);
		}
	}

	/* (non-Javadoc)
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
			ps = cn.prepareStatement(SQL_SELECT_EMAIL);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				isFree = false;
			}
		} catch (SQLException e) {
			throw new DAOException("SQL add user exception", e);
		} finally {
			close(cn);
			close(ps);
		}
		return isFree;
	}

	/* (non-Javadoc)
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
			throw new DAOException("SQL changing password exception", e);
		} finally {
			close(cn);
			close(ps);
		}
	}

	/* (non-Javadoc)
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
			throw new DAOException("SQL changing email exception", e);
		} finally {
			close(cn);
			close(ps);
		}
	}

	/* (non-Javadoc)
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
			throw new DAOException("SQL changing avatar exception", e);
		} finally {
			close(cn);
			close(ps);
		}
	}
}
