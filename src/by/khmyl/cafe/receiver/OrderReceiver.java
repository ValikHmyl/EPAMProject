package by.khmyl.cafe.receiver;

import java.util.HashMap;

import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
/**
* Processes order's requests and validate them and if they are valid sends them 
* for further processing.
*/
public abstract class OrderReceiver {
	
	/**
	 * Find {@link MenuItem} by id and add it into the cart 
	 *
	 * @param itemId id of menu item
	 * @param amount amount
	 * @param cart cart
	 * @return {@link MenuItem} that was added into the cart
	 * @throws ReceiverException if occurred an error, while processing client's requests
	 */
	public abstract MenuItem addToCart(int itemId, int amount, HashMap<MenuItem, Integer> cart)
			throws ReceiverException;

	/**
	 * Find {@link MenuItem} by id and delete it from the cart 
	 *
	 * @param itemId id of menu item
	 * @param cart cart
	 * @return {@link MenuItem} that was deleted from the cart
	 * @throws ReceiverException if occurred an error, while processing client's requests
	 */
	public abstract MenuItem deleteFromCart(int itemId, HashMap<MenuItem, Integer> cart) throws ReceiverException;

	/**
	 * Processes request for making an order from user and validates the input
	 * parameters 
	 *
	 * @param user user
	 * @param cart cart
	 * @param datetime date that user chosen for pick up his order 
	 * @return {@code true} if successfully made an order
	 * @throws ReceiverException if occurred an error, while processing client's requests
	 */
	public abstract boolean makeAnOrder(User user, HashMap<MenuItem, Integer> cart, String datetime)
			throws ReceiverException;
	
	/**
	 * Cancel order.
	 *
	 * @param orderId id of order
	 * @param message error message if occurs error
	 * @return {@code true} if successfully canceled  an order
	 * @throws ReceiverException if occurred an error, while processing client's requests
	 */
	public abstract boolean cancelOrder(int orderId,StringBuilder message) throws ReceiverException;
}
