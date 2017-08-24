package by.khmyl.cafe.dao;

import by.khmyl.cafe.exception.DAOException;

public abstract class AdminDAO extends AbstractDAO {
	public abstract void banUser(int userId) throws DAOException;

	public abstract void activateUser(int userId) throws DAOException;

}
