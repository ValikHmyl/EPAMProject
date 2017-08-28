package by.khmyl.cafe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.constant.Constant;

/**
 * Class for validating parameters from request 
 */
public class Validator {
	private static final Logger LOGGER = LogManager.getLogger(Validator.class);
	private static final String USERNAME_REGEX = "[A-zÀ-ÿ](\\w|[À-ÿ]){3,}";
	private static final String PASSWORD_REGEX = "^(?=.*[a-zà-ÿ])(?=.*[0-9])(?=.*[A-ZÀ-ß]).{6,}$";
	private static final String EMAIL_REGEX = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";

	private Validator() {
	}

	/**
	 * Validate username format.
	 *
	 * @param username the username
	 * @return {@code true} if correct format,{@code false} otherwise. 
	 */
	public static boolean validateUsername(String username) {
		if (username == null) {
			return false;
		}
		return match(USERNAME_REGEX, username);
	}

	/**
	 * Validate password format.
	 *
	 * @param password the password
	 * @return {@code true} if correct format,{@code false} otherwise.
	 */
	public static boolean validatePassword(String password) {
		if (password == null) {
			return false;
		}
		return match(PASSWORD_REGEX, password);
	}

	/**
	 * Validate email format.
	 *
	 * @param email the email
	 * @return {@code true} if correct format,{@code false} otherwise.
	 */
	public static boolean validateEmail(String email) {
		if (email == null) {
			return false;
		}
		return match(EMAIL_REGEX, email);
	}

	/**
	 * Validate datetime for order.
	 *
	 * @param datetime the datetime
	 * @return {@code true} if correct date format and not past date, {@code false} otherwise.
	 */
	public static boolean validateDatetime(String datetime, int sign) {
		Date currentDate = new Date();
		currentDate = new Date(currentDate.getTime() + sign*Constant.ONE_HOUR);
		try {
			SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date orderDate = formatedDate.parse(datetime);
			if (orderDate.after(currentDate)) {
				return true;
			}
		} catch (ParseException e) {
			LOGGER.log(Level.WARN, "Parse error");
		}
		return false;
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
