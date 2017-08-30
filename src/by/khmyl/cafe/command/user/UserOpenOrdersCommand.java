package by.khmyl.cafe.command.user;


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
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;
import by.khmyl.cafe.util.PaginationHelper;

/**
 * Command for opening page with user's orders information.
 */
public class UserOpenOrdersCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);

	private UserReceiver receiver = new UserReceiverImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(Constant.USER);
		String pageNumber = request.getParameter(Constant.PAGE_NUMBER);
		int startIndex = (pageNumber != null) ? Integer.valueOf(pageNumber) : 1;
		startIndex = (startIndex - 1) * Constant.MAX_ON_PAGE;
		String filter = request.getParameter(Constant.FILTER);
		if (user != null) {
			try {
				PaginationHelper<Order> orders = null;
				orders = receiver.openOrders(user.getId(), startIndex, filter);
				request.setAttribute(Constant.ORDERS, orders.getItems());
				request.setAttribute(Constant.LIMIT, Constant.MAX_ON_PAGE);
				request.setAttribute(Constant.TOTAL_AMOUNT, orders.getAmount());
				request.setAttribute(Constant.FILTER, filter);
				request.setAttribute(Constant.PAGE_NUMBER, pageNumber);
				router.setPath(PathConstant.USER_ORDERS);
				router.setRouteType(RouteType.FORWARD);
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				router.setPath(PathConstant.ERROR_500);
				router.setRouteType(RouteType.REDIRECT);
			}
		} else {
			router.setPath(PathConstant.SIGN_IN);
			router.setRouteType(RouteType.REDIRECT);
		}
		return router;
	}

}
