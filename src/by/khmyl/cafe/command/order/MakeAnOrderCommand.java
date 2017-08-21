package by.khmyl.cafe.command.order;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.OrderReceiver;
import by.khmyl.cafe.receiver.impl.OrderReceiverImpl;

/**
 * Command for making user order.
 */
public class MakeAnOrderCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(MakeAnOrderCommand.class);
	private static final String USER = "user";
	private static final String CART = "cart";
	private static final String DATE = "date";
	private static final String TIME = "time";
	private static final String ERROR_MSG = "errorMsg";

	private OrderReceiver receiver = new OrderReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(USER);
		String date = request.getParameter(DATE);
		String time = request.getParameter(TIME);
		Router router = new Router(RouteType.REDIRECT);
		HashMap<MenuItem, Integer> cart = (HashMap<MenuItem, Integer>) session.getAttribute(CART);
		String datetime = date + " " + time;
		if (user == null) {
			router.setPath(PathConstant.SIGN_IN);
		} else {
			try {
				if (receiver.makeAnOrder(user, cart, datetime)) {
					cart.clear();
					router.setPath(PathConstant.MAIN);// change on room
				} else {
					request.setAttribute(ERROR_MSG, true);
					router.setPath(PathConstant.ORDER);
					router.setRouteType(RouteType.FORWARD);
				}
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				router.setPath(PathConstant.ERROR_500);
			}

		}

		return router;
	}

}
