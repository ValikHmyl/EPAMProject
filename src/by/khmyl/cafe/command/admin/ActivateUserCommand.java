package by.khmyl.cafe.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;

public class ActivateUserCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ActivateUserCommand.class);
	private static final String USER_ID = "userId";

	private AdminReceiver receiver = new AdminReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		int userId = Integer.parseInt(request.getParameter(USER_ID));
		try {
			receiver.activateUser(userId);
			router.setPath(PathConstant.ADMIN_ALL_USERS);
			router.setRouteType(RouteType.REDIRECT);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}
		return router;
	}

}
