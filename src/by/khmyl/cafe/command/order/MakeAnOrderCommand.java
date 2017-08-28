package by.khmyl.cafe.command.order;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;
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

	private OrderReceiver receiver = new OrderReceiverImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(Constant.USER);
		String date = request.getParameter(Constant.DATE);
		String time = request.getParameter(Constant.TIME);
		Router router = new Router();
		HashMap<MenuItem, Integer> cart = (HashMap<MenuItem, Integer>) session.getAttribute(Constant.CART);
		String datetime = date + " " + time;
		if (user == null) {
			router.setPath(PathConstant.SIGN_IN);
		}
		if (cart.isEmpty()) {
			router.setPath(PathConstant.CART);
		} else {
			try {
				if (receiver.makeAnOrder(user, cart, datetime)) {
					cart.clear();
					router.setPath(PathConstant.USER_ALL_ORDERS);
					router.setRouteType(RouteType.REDIRECT);
				} else {
					request.setAttribute(Constant.ERROR_MESSAGE, true);
					router.setPath(PathConstant.ORDER);
					router.setRouteType(RouteType.FORWARD);

				}
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				router.setPath(PathConstant.ERROR_500);
				router.setRouteType(RouteType.REDIRECT);

			}

		}

		return router;
	}

}
