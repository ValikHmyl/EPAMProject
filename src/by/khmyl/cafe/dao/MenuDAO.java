package by.khmyl.cafe.dao;

import java.util.ArrayList;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.DAOException;

public abstract class MenuDAO extends AbstractDAO{
	public abstract ArrayList<MenuItem> findMenu(String category) throws DAOException;
	public abstract MenuItem findMenuItem(int id) throws DAOException;
}
