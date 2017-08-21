package by.khmyl.cafe.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EncryptManager {
	private static final Logger LOGGER = LogManager.getLogger(EncryptManager.class);

	private EncryptManager() {
	}

	/**
	 * Encrypt password before fill it in database.
	 *
	 * @param password user password
	 * @return encrypted password
	 */
	public static String enñrypt(String password) {
		String str = null;
		try {
			Base64.Encoder encoder = Base64.getEncoder();
			byte[] encodedBytes = encoder.encode(password.getBytes("utf8"));
			str = new String(encodedBytes);
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.ERROR, "Wrong charset name" + e);
		}
		return str;
	}

	/**
	 * Deñrypt password from database.
	 *
	 * @param password encrypted password
	 * @return decrypted password
	 */
	public static String deñrypt(String password) {
		String str = null;
		try {
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedBytes = decoder.decode(password);
			str = new String(decodedBytes, "utf8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.ERROR, "Wrong charset name", e);
		}
		return str;
	}
}
