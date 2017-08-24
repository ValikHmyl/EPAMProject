package by.khmyl.cafe.receiver;

import java.util.ArrayList;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;

/**
 * Processes client's requests and validate them and if they are valid sends them 
 * for further processing.
 */
public abstract class CommonReceiver {
	
	/**
	 *  Processes the sign in request from client and validates the input
	 *  parameters
	 *
	 * @param username username
	 * @param password password
	 * @param errorMessages list of error messages, which will fills up if occurs some errors
	 * @return Object, that encapsulate information about user 
	 * @throws ReceiverException if occurred an error, while processing client's requests
	 */
	public abstract User signIn(String username, String password,ArrayList<String> errorMessages) throws ReceiverException;

	/**
	 *  Processes the sign up to database request from client and validates the input
	 *  parameters
	 *
	 * @param username username
	 * @param password password
	 * @param repeatPassword repeat password
	 * @param email  email
	 * @param errorMessages list of error messages, which will fills up if occurs some errors 
	 * @return {@code true} if successfully sign up user in database
	 * @throws ReceiverException if occurred an error, while processing client's requests
	 */
	public abstract boolean signUp(String username, String password, String repeatPassword, String email,ArrayList<String> errorMessages)
			throws ReceiverException;
	
	/**
	 * Processes request from client for present menu
	 *
	 * @param category category of menu
	 * @return list of {@link MenuItem}s
	* @throws ReceiverException if occurred an error, while processing client's requests
	 */
	public abstract ArrayList<MenuItem> getMenu(String category) throws ReceiverException;
}
