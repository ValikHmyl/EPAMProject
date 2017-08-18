package by.khmyl.cafe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Validator {
	private static final Logger LOGGER = LogManager.getLogger(Validator.class);
	private static final String USERNAME_REGEX = "[A-zÀ-ÿ](\\w|[À-ÿ]){3,}";
	private static final String PASSWORD_REGEX = "^(?=.*[a-zà-ÿ])(?=.*[0-9])(?=.*[A-ZÀ-ß]).{6,}$";
	private static final String EMAIL_REGEX = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";
	private static final long ONE_HOUR = 3_600_000;

	public static boolean validateUsername(String username) {
		if (username == null) {
			return false;
		}
		return match(USERNAME_REGEX, username);
	}

	public static boolean validatePassword(String password) {
		if (password == null) {
			return false;
		}
		return match(PASSWORD_REGEX, password);
	}

	public static boolean validateEmail(String email) {
		if (email == null) {
			return false;
		}
		return match(EMAIL_REGEX, email);
	}

	public static boolean validateDatetime(String datetime) {
		Date currentDate = new Date();
		currentDate = new Date(currentDate.getTime() + ONE_HOUR);
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
