package by.khmyl.cafe.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;

public class ConfirmPaymentCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ConfirmPaymentCommand.class);
	private AdminReceiver receiver = new AdminReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		int orderId = Integer.parseInt(request.getParameter(Constant.ORDER_ID));
		int userId = Integer.parseInt(request.getParameter(Constant.USER_ID));

		try {
			receiver.confirmOrder(orderId, userId);
			router.setPath(PathConstant.ADMIN_ALL_ORDERS);
			router.setRouteType(RouteType.REDIRECT);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}

		return router;

	}

}
