package by.khmyl.cafe.command.order;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.OrderReceiver;
import by.khmyl.cafe.receiver.impl.OrderReceiverImpl;

/**
 * Command for adding menu item in cart.
 */
public class AddToCartCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(AddToCartCommand.class);

	private OrderReceiver receiver = new OrderReceiverImpl();

	/**
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		int itemId = Integer.parseInt(request.getParameter(Constant.ITEM_ID));
		int amount;
		HttpSession session = request.getSession();
		JsonObject jsonObj = new JsonObject();
		Router router = new Router();
		try {
			amount = Integer.parseInt(request.getParameter(Constant.AMOUNT));
			try {
				HashMap<MenuItem, Integer> cart = (HashMap<MenuItem, Integer>) session.getAttribute(Constant.CART);
				if (cart == null) {
					cart = new HashMap<>();
				}
				receiver.addToCart(itemId, amount, cart);
				session.setAttribute(Constant.CART, cart);
				jsonObj.addProperty(Constant.SUCCESS, true);
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				jsonObj.addProperty(Constant.ERROR, true);
				jsonObj.addProperty(Constant.ERROR_MESSAGE, "Something goes wrong! Try again leter.");
			}
		} catch (NumberFormatException e) {
			jsonObj.addProperty(Constant.ERROR, true);
			jsonObj.addProperty(Constant.ERROR_MESSAGE, "Wrong number, try again.");
		}
		router.setJson(jsonObj.toString());
		return router;
	}

}
