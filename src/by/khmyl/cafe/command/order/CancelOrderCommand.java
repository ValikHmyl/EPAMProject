package by.khmyl.cafe.command.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.OrderReceiver;
import by.khmyl.cafe.receiver.impl.OrderReceiverImpl;

/**
 * Command for canceling users order if it possible.
 */
public class CancelOrderCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(CancelOrderCommand.class);
	private static final String ORDER_ID = "orderId";
	private static final String USER = "user";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final String ERROR_MSG = "errorMsg";

	private OrderReceiver receiver = new OrderReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(USER);
		JsonObject jsonObj = new JsonObject();
		if (user != null) {
			StringBuilder message = new StringBuilder();
			try {
				if (receiver.cancelOrder(orderId, message)) {
					jsonObj.addProperty(SUCCESS, true);
				} else {
					jsonObj.addProperty(ERROR_MSG, message.toString());
				}
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				jsonObj.addProperty(ERROR_MSG, message.toString());
			}
		} else {
			jsonObj.addProperty(ERROR, true);
		}
		router.setJson(jsonObj.toString());
		return router;
	}

}
