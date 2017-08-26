package by.khmyl.cafe.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;
import by.khmyl.cafe.util.PaginationHelper;

public class AdminOpenOrdersCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(AdminOpenUsersCommand.class);
	private static final String PAGE_NUMBER = "pageNumber";
	private static final int MAX_ON_PAGE = 10;
	private static final String ORDERS = "orders";
	private static final String LIMIT = "limit";
	private static final String FILTER = "filter";
	private static final String TOTAL_AMOUNT = "total";
	private AdminReceiver receiver= new AdminReceiverImpl();
	@Override
	public Router execute(HttpServletRequest request) {
	Router router=new Router();
	String pageNumber = request.getParameter(PAGE_NUMBER);
	int startIndex = (pageNumber != null) ? Integer.valueOf(pageNumber) : 1;
	startIndex = (startIndex - 1) * MAX_ON_PAGE;
	String filter = request.getParameter(FILTER);
	PaginationHelper<Order> orders = null;
	try {
		orders=receiver.openOrders(startIndex, filter);
		request.setAttribute(ORDERS, orders.getItems());
		request.setAttribute(LIMIT, MAX_ON_PAGE);
		request.setAttribute(TOTAL_AMOUNT, orders.getAmount());
		request.setAttribute(FILTER, filter);

		router.setPath(PathConstant.ADMIN_ORDERS);
		router.setRouteType(RouteType.FORWARD);
	} catch (ReceiverException e) {
		LOGGER.log(Level.ERROR, e);
		router.setPath(PathConstant.ERROR_500);
		router.setRouteType(RouteType.REDIRECT);
	}
	
	
	return router;
	}

}
