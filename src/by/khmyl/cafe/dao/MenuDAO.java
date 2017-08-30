package by.khmyl.cafe.dao;

import java.math.BigDecimal;
import java.util.ArrayList;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.DAOException;

/**
 * Provides a set of available requests to database for menu.
 */
public abstract class MenuDAO extends AbstractDAO {

	/**
	 * Method for finding menu items from concrete category.
	 *
	 * @param category            category of menu
	 * @return list of {@link MenuItem}s from chosen category
	 * @throws DAOException             In case if occurred an error with SQL query or connection.
	 */
	public abstract ArrayList<MenuItem> findMenu(String category) throws DAOException;

	/**
	 * Find menu item.
	 *
	 * @param id
	 *            id of menu
	 * @return Object, that encapsulated information about {@link MenuItem}
	 * @throws DAOException
	 *             In case if occurred an error with SQL query or connection.
	 */
	public abstract MenuItem findMenuItem(int id) throws DAOException;

	/**
	 * Adds the menu item.
	 *
	 * @param name the name 
	 * @param category the category
	 * @param price the price
	 * @param portion the portion
	 * @param imageName the image name
	 * @throws DAOException  In case if occurred an error with SQL query or connection.
	 */
	public abstract void addMenuItem(String name, String category, BigDecimal price, String portion, String imageName)
			throws DAOException;

	/**
	 * Find filtered menu.
	 *
	 * @param startIndex the start index
	 * @param lastIndex the last index
	 * @param filter the filter
	 * @return list of items which are match the filter
	 * @throws DAOException  In case if occurred an error with SQL query or connection.
	 */
	public abstract ArrayList<MenuItem> findFilteredMenu(int startIndex,int lastIndex, String filter) throws DAOException;

	/**
	 * Count menu items.
	 *
	 * @param filter the filter
	 * @return amount of items which are match the filter
	 * @throws DAOException  In case if occurred an error with SQL query or connection.
	 */
	public abstract int countMenuItems(String filter) throws DAOException;
}
