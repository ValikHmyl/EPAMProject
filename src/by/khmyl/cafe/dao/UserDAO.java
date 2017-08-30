package by.khmyl.cafe.dao;

import java.math.BigDecimal;
import java.util.ArrayList;

import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;

/**
 * Provides a set of available requests to database for user.
 */
public abstract class UserDAO extends AbstractDAO {

	/**
	 * Find user by username.
	 *
	 * @param username
	 *            username
	 * @return Object, that encapsulated information about user
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract User findUserByName(String username) throws DAOException;

	/**
	 * Find user by id.
	 *
	 * @param userId
	 *            the user id
	 * @return Object, that encapsulated information about user
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 * 
	 */
	public abstract User findUser(int userId) throws DAOException;

	/**
	 * Find users.
	 *
	 * @param startIndex
	 *            the start index
	 * @param filter
	 *            the filter
	 * @return list of users which are match the filter
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 * 
	 */
	public abstract ArrayList<User> findUsers(int startIndex, String filter) throws DAOException;

	/**
	 * Count users.
	 *
	 * @param filter
	 *            the filter
	 * @return amount of users which are match the filter
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 * 
	 */
	public abstract int countUsers(String filter) throws DAOException;

	/**
	 * Change password.
	 *
	 * @param userId
	 *            id of user
	 * @param newPassword
	 *            new password
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void changePassword(int userId, String newPassword) throws DAOException;

	/**
	 * Change email.
	 *
	 * @param userId
	 *            id of user
	 * @param newEmail
	 *            new email
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void changeEmail(int userId, String newEmail) throws DAOException;

	/**
	 * Change avatar.
	 *
	 * @param userId
	 *            id of user
	 * @param newAvatarImgName
	 *            new avatar image name
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void changeAvatar(int userId, String newAvatarImgName) throws DAOException;

	/**
	 * Check email for existing.
	 *
	 * @param email
	 *            email
	 * @return {@code true} if email is free, {@code false} otherwise
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract boolean checkEmail(String email) throws DAOException;

	/**
	 * Add user in database after sign up.
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param email
	 *            email
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract void addUser(String username, String password, String email) throws DAOException;

	/**
	 * Change discount.
	 *
	 * @param userId
	 *            the user id
	 * @param discount
	 *            new discount
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 * 
	 */
	public abstract void changeDiscount(int userId, BigDecimal discount) throws DAOException;

}
