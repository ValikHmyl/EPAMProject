package by.khmyl.cafe.command.order;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.OrderReceiver;
import by.khmyl.cafe.receiver.impl.OrderReceiverImpl;

public class DeleteFromCartCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(DeleteFromCartCommand.class);
	private static final String CART = "cart";
	private static final String SUCCESS = "success";
	private static final String ITEM_ID = "itemId";
	private static final String ERROR = "error";
	private static final String ERROR_MSG = "errorMsg";
	private OrderReceiver receiver = new OrderReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		int itemId = Integer.parseInt(request.getParameter(ITEM_ID));
		HttpSession session = request.getSession();
		JsonObject jsonObj = new JsonObject();
		Router router = new Router();
		try {
			HashMap<MenuItem, Integer> cart = (HashMap<MenuItem, Integer>) session.getAttribute(CART);
			receiver.deleteFromCart(itemId, cart);
			session.setAttribute(CART, cart);
			jsonObj.addProperty(SUCCESS, true);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			jsonObj.addProperty(ERROR, true);
			jsonObj.addProperty(ERROR_MSG, "Something goes wrong! Try again leter.");
		}
		router.setJson(jsonObj.toString());
		return router;
	}

}
