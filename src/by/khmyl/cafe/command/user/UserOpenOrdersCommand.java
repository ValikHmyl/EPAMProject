package by.khmyl.cafe.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;
import by.khmyl.cafe.util.PaginationHelper;

/**
 * Command for present user's orders.
 */
public class UserOpenOrdersCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);
	private static final String USER = "user";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final int MAX_ON_PAGE = 10;
	private static final String ORDERS = "orders";
	private static final String LIMIT = "limit";
	private static final String FILTER = "filter";
	private static final String TOTAL_AMOUNT = "total";

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
		User user = (User) session.getAttribute(USER);
		String pageNumber = request.getParameter(PAGE_NUMBER);
		int startIndex = (pageNumber != null) ? Integer.valueOf(pageNumber) : 1;
		startIndex = (startIndex - 1) * MAX_ON_PAGE;
		String filter = request.getParameter(FILTER);
		try {
			PaginationHelper<Order> orders = null;
			orders = receiver.openOrders(user.getId(), startIndex, filter);
			request.setAttribute(ORDERS, orders.getItems());
			request.setAttribute(LIMIT, MAX_ON_PAGE);
			request.setAttribute(TOTAL_AMOUNT, orders.getAmount());
			request.setAttribute(FILTER, filter);

			router.setPath(PathConstant.USER_ORDERS);
			router.setRouteType(RouteType.FORWARD);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}
		return router;
	}

}
