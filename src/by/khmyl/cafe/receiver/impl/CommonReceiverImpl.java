package by.khmyl.cafe.receiver.impl;

import java.util.ArrayList;

import by.khmyl.cafe.dao.MenuDAO;
import by.khmyl.cafe.dao.UserDAO;
import by.khmyl.cafe.dao.impl.MenuDAOImpl;
import by.khmyl.cafe.dao.impl.UserDAOImpl;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.CommonReceiver;
import by.khmyl.cafe.util.EncryptManager;
import by.khmyl.cafe.util.Validator;

public class CommonReceiverImpl extends CommonReceiver {
	private static final String WRONG_DATA = "wrongData";
	private static final String BANNED = "banned";
	private static final String USERNAME_ERROR = "usernameError";
	private static final String PASWWORD_ERROR = "passwordError";
	private static final String PASWWORD_EQUALS = "passwordEqualsError";
	private static final String EMAIL_ERROR = "emailError";
	private static final String EMAIL_EXIST = "emailExist";
	private static final String USERNAME_EXSIST = "usernameExist";

	@Override
	public User signIn(String username, String password, ArrayList<String> errorMessages) throws ReceiverException {
		User user = null;
		if (Validator.validateUsername(username) && Validator.validatePassword(password)) {
			UserDAO dao = new UserDAOImpl();
			try {
				user = dao.findUser(username);
			} catch (DAOException e) {
				throw new ReceiverException("Finding user exception", e);
			}
			if (user == null) {
				errorMessages.add(WRONG_DATA);
			} else {
				if (!EncryptManager.deñrypt(user.getPassword()).equals(password)) {
					errorMessages.add(WRONG_DATA);
				} else if (!user.getStatus()) {
					errorMessages.add(BANNED);
				}
			}
		} else {
			errorMessages.add(WRONG_DATA);
		}

		return user;
	}

	@Override
	public boolean signUp(String username, String password, String repeatPassword, String email,
			ArrayList<String> errorMessages) throws ReceiverException {
		boolean isValid = true;
		if (!Validator.validateUsername(username)) {
			isValid = false;
			errorMessages.add(USERNAME_ERROR);
		}
		if (!Validator.validatePassword(password)) {
			isValid = false;
			errorMessages.add(PASWWORD_ERROR);
		}
		if (!password.equals(repeatPassword)) {
			isValid = false;
			errorMessages.add(PASWWORD_EQUALS);
		}
		if (!Validator.validateEmail(email)) {
			isValid = false;
			errorMessages.add(EMAIL_ERROR);
		}
		UserDAO dao = new UserDAOImpl();
		try {
			if (dao.findUser(username) != null) {
				isValid = false;
				errorMessages.add(USERNAME_EXSIST);
			}
			if (!dao.checkEmail(email)) {
				isValid = false;
				errorMessages.add(EMAIL_EXIST);
			}
			if (isValid) {
				password = EncryptManager.enñrypt(password);
				dao.addUser(username, password, email);
			}
		} catch (DAOException e) {
			throw new ReceiverException("Finding  user data exception", e);
		}
		return isValid;
	}

	@Override
	public ArrayList<MenuItem> getMenu(String category) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		ArrayList<MenuItem> list;
		try {
			list = dao.findMenu(category);
		} catch (DAOException e) {
			throw new ReceiverException("Finding menu exception", e);
		}
		return list;
	}

}
