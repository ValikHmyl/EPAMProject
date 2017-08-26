package by.khmyl.cafe.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;

public class EditOrderCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(EditOrderCommand.class);
	private static final String ORDER_ID = "orderId";
	private static final String DATE = "date";
	private static final String TIME = "time";
	private static final String ERROR_MSG = "errorMsg";
	private static final String USER = "user";

	private UserReceiver receiver = new UserReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(PathConstant.USER_ALL_ORDERS, RouteType.REDIRECT);
		String date = request.getParameter(DATE);
		String time = request.getParameter(TIME);
		String newDatetime = date + " " + time;
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(USER);
		if (user != null) {
			int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
			try {
				if (receiver.editOrder(orderId, newDatetime)) {
				} else {
					request.setAttribute(ERROR_MSG, true);
					request.setAttribute(ORDER_ID, orderId);
					router.setRouteType(RouteType.FORWARD);
				}
			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				router.setPath(PathConstant.ERROR_500);
			}
		} else {
			router.setPath(PathConstant.SIGN_IN);
		}

		return router;
	}

}