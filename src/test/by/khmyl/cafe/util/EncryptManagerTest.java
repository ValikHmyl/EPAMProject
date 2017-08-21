package test.by.khmyl.cafe.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import by.khmyl.cafe.util.EncryptManager;

public class EncryptManagerTest {
	private static String password;
	private static String encrypted;

	@BeforeClass
	public static void init() throws Exception {
		password = "Pwd123";
		encrypted = "UHdkMTIz";
	}

	@Test
	public void encryptTest() {
		String actual = EncryptManager.enñrypt(password);
		String expected = encrypted;

		assertEquals(expected, actual);

	}

	@Test
	public void decryptTest() {
		String actual = EncryptManager.deñrypt(encrypted);
		String expected = password;

		assertEquals(expected, actual);

	}

}
