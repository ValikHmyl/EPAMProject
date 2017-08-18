package by.khmyl.cafe.dao;

import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;

public abstract class UserDAO extends AbstractDAO {
	public abstract User findUser(String username) throws DAOException;
	public abstract void changePassword(int userId,String newPassword) throws DAOException;
	public abstract void changeEmail(int userId,String newEmail) throws DAOException;
	public abstract void changeAvatar(int userId,String newAvatarImgName) throws DAOException;
	public abstract boolean checkEmail(String email) throws DAOException;
	public abstract void addUser(String username, String password, String email) throws DAOException;
	
}
