package by.khmyl.cafe.receiver.impl;

import java.util.ArrayList;

import by.khmyl.cafe.constant.Constant;
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
import by.khmyl.cafe.util.MailSender;
import by.khmyl.cafe.util.Validator;

/**
 * Realizes validation of clients requests and if they are valid sends them for
 * further processing.
 */
public class CommonReceiverImpl extends CommonReceiver {
	private static final String EMAIL_SUBJECT = "Sign Up in McCafe";
	private static final String EMAIL_CONTENT = "<h2>Welcome in McCafe system!</h2> <p>Your successfully sign up in system of McCafe.</p>";

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.receiver.CommonReceiver#signIn(java.lang.String,
	 * java.lang.String, java.util.ArrayList)
	 */
	@Override
	public User signIn(String username, String password, ArrayList<String> errorMessages) throws ReceiverException {
		User user = null;
		if (Validator.validateUsername(username) && Validator.validatePassword(password)) {
			UserDAO dao = new UserDAOImpl();
			try {
				user = dao.findUserByName(username);
			} catch (DAOException e) {
				throw new ReceiverException("Finding user exception: " + e.getMessage(), e);
			}
			if (user == null) {
				errorMessages.add(Constant.WRONG_DATA);
			} else {
				if (!EncryptManager.deñrypt(user.getPassword()).equals(password)) {
					errorMessages.add(Constant.WRONG_DATA);
				} else if (!user.getStatus()) {
					errorMessages.add(Constant.BANNED);
				}
			}
		} else {
			errorMessages.add(Constant.WRONG_DATA);
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.receiver.CommonReceiver#signUp(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String,
	 * java.util.ArrayList)
	 */
	@Override
	public boolean signUp(String username, String password, String repeatPassword, String email,
			ArrayList<String> errorMessages) throws ReceiverException {
		boolean isValid = true;
		if (!Validator.validateUsername(username)) {
			isValid = false;
			errorMessages.add(Constant.USERNAME_ERROR);
		}
		if (!Validator.validatePassword(password)) {
			isValid = false;
			errorMessages.add(Constant.PASSWORD_ERROR);
		}
		if (!password.equals(repeatPassword)) {
			isValid = false;
			errorMessages.add(Constant.PASSWORD_EQUALS);
		}
		if (!Validator.validateEmail(email)) {
			isValid = false;
			errorMessages.add(Constant.EMAIL_ERROR);
		}
		UserDAO dao = new UserDAOImpl();
		try {
			if (dao.findUserByName(username) != null) {
				isValid = false;
				errorMessages.add(Constant.USERNAME_EXIST);
			}
			if (!dao.checkEmail(email)) {
				isValid = false;
				errorMessages.add(Constant.EMAIL_EXIST);
			}
			if (isValid) {
				password = EncryptManager.enñrypt(password);
				dao.addUser(username, password, email);
				MailSender sender = new MailSender(EMAIL_SUBJECT, EMAIL_CONTENT, email);
				sender.start();
			}
		} catch (DAOException e) {
			throw new ReceiverException("Finding  user data exception: " + e.getMessage(), e);
		}
		return isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.receiver.CommonReceiver#getMenu(java.lang.String)
	 */
	@Override
	public ArrayList<MenuItem> getMenu(String category) throws ReceiverException {
		MenuDAO dao = new MenuDAOImpl();
		ArrayList<MenuItem> list;
		try {
			list = dao.findMenu(category);
		} catch (DAOException e) {
			throw new ReceiverException("Finding menu exception: " + e.getMessage(), e);
		}
		return list;
	}

}
