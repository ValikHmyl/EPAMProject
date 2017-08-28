package by.khmyl.cafe.command.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.command.admin.AdminOpenMenuCommand;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.OrderReceiver;
import by.khmyl.cafe.receiver.impl.OrderReceiverImpl;

public class SearchOrderCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(AdminOpenMenuCommand.class);

	private OrderReceiver receiver = new OrderReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		try {
			int orderId = Integer.parseInt(request.getParameter(Constant.ORDER_ID));
			Order order = null;
			try {
				order = receiver.searchOrder(orderId);
				request.setAttribute(Constant.ORDER, order);
				router.setPath(PathConstant.CONFIRM_PAYMENT);
				router.setRouteType(RouteType.FORWARD);
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				router.setPath(PathConstant.ERROR_500);
				router.setRouteType(RouteType.REDIRECT);
			}
		} catch (NumberFormatException e) {
			router.setPath(PathConstant.ADMIN_ALL_ORDERS);
			router.setRouteType(RouteType.FORWARD);
			request.setAttribute(Constant.WRONG_DATA, true);
		}
		return router;
	}

}
