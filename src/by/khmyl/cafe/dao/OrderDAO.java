package by.khmyl.cafe.dao;

import java.util.ArrayList;
import java.util.HashMap;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;

public abstract class OrderDAO extends AbstractDAO {
	public abstract void addOrder(int userId, HashMap<MenuItem, Integer> cart, String datetime) throws DAOException;
	public abstract ArrayList<Order> findUserOrders(int userId) throws DAOException;
}
