package test.by.khmyl.cafe.util;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import by.khmyl.cafe.util.Validator;

public class ValidatorTest {
	private static String username;
	private static String password;
	private static String email;
	private static String pastDatetime;
	private static String futureDatetime;

	@BeforeClass
	public static void init() throws Exception {
		username = "UserName123";
		password = "Pwd123";
		email = "someEmail123@mail.ru";
		pastDatetime = "2017-01-01 00:00";
		futureDatetime = "2017-12-01 00:00";
	}

	@Test
	public void validateUsernameTest() {
		assertTrue(Validator.validateUsername(username));

	}

	@Test
	public void validatePasswordTest() {
		assertTrue(Validator.validatePassword(password));

	}

	@Test
	public void validateEmailTest() {
		assertTrue(Validator.validateEmail(email));

	}

	@Test
	public void validatePastDatetimeTest() {
		assertFalse(Validator.validateDatetime(pastDatetime));

	}

	@Test
	public void validateFutureDatetimeTest() {
		assertTrue(Validator.validateDatetime(futureDatetime));

	}
}
