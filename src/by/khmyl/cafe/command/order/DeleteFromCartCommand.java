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
 * Command for deleting menu item from cart.
 */
public class DeleteFromCartCommand extends AbstractCommand {

	private static final Logger LOGGER = LogManager.getLogger(DeleteFromCartCommand.class);
	private OrderReceiver receiver = new OrderReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		int itemId = Integer.parseInt(request.getParameter(Constant.ITEM_ID));
		HttpSession session = request.getSession();
		JsonObject jsonObj = new JsonObject();
		Router router = new Router();
		try {
			HashMap<MenuItem, Integer> cart = (HashMap<MenuItem, Integer>) session.getAttribute(Constant.CART);
			receiver.deleteFromCart(itemId, cart);
			session.removeAttribute(Constant.CART);
			session.setAttribute(Constant.CART, cart);
			jsonObj.addProperty(Constant.SUCCESS, true);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			jsonObj.addProperty(Constant.ERROR, true);
			jsonObj.addProperty(Constant.ERROR_MESSAGE, "Something goes wrong! Try again leter.");
		}
		router.setJson(jsonObj.toString());
		return router;
	}

}
