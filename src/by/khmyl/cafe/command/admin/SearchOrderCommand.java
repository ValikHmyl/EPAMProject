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

public class SearchOrderCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(AdminOpenMenuCommand.class);
	private static final String ORDER_ID = "orderId";
	private static final String ORDER = "order";
	private static final String WRONG_ID = "wrongId";

	private AdminReceiver receiver = new AdminReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(PathConstant.ADMIN_ALL_ORDERS, RouteType.FORWARD);
		try {
			int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
			Order order = null;
			try {
				order = receiver.searchOrder(orderId);
				request.setAttribute(ORDER, order);
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				router.setPath(PathConstant.ERROR_500);
				router.setRouteType(RouteType.REDIRECT);
			}
		} catch (NumberFormatException e) {
			request.setAttribute(WRONG_ID, true);
		}
		return router;
	}

}
