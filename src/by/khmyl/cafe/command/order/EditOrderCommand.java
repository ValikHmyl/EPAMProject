package by.khmyl.cafe.command.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.CommandType;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.OrderReceiver;
import by.khmyl.cafe.receiver.impl.OrderReceiverImpl;

public class EditOrderCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(EditOrderCommand.class);

	private OrderReceiver receiver = new OrderReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String date = request.getParameter(Constant.DATE);
		String time = request.getParameter(Constant.TIME);
		String newDatetime = date + " " + time;
		String filter = request.getParameter(Constant.FILTER);
		String pageNumber = request.getParameter(Constant.PAGE_NUMBER);
		String command = CommandType.USER_OPEN_ORDERS.name().toLowerCase();
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(Constant.USER);
		if (user != null) {
			int orderId = Integer.parseInt(request.getParameter(Constant.ORDER_ID));
			try {
				if (!receiver.editOrder(orderId, newDatetime)) {
					request.setAttribute(Constant.ERROR_MESSAGE, true);
					request.setAttribute(Constant.ORDER_ID, orderId);
				}
				router.setRouteType(RouteType.FORWARD);
				router.generatePath(command, filter, pageNumber);
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				router.setRouteType(RouteType.REDIRECT);
				router.setPath(PathConstant.ERROR_500);
			}
		} else {
			router.setPath(PathConstant.SIGN_IN);
			router.setRouteType(RouteType.REDIRECT);

		}

		return router;
	}

}
