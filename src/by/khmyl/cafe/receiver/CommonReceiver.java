package by.khmyl.cafe.receiver;

import java.util.ArrayList;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;

public abstract class CommonReceiver {
	public abstract User signIn(String username, String password,ArrayList<String> errorMessages) throws ReceiverException;

	public abstract boolean signUp(String username, String password, String repeatPassword, String email,ArrayList<String> errorMessages)
			throws ReceiverException;
	public abstract ArrayList<MenuItem> getMenu(String category) throws ReceiverException;
}
