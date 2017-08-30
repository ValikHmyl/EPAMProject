package by.khmyl.cafe.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.CommandType;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;

/**
 * Command for ban user in system.
 */
public class BanUserCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(BanUserCommand.class);

	private AdminReceiver receiver = new AdminReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		int userId = Integer.parseInt(request.getParameter(Constant.USER_ID));
		String userEmail = request.getParameter(Constant.USER_EMAIL);
		String filter = request.getParameter(Constant.FILTER);
		String pageNumber = request.getParameter(Constant.PAGE_NUMBER);
		String command = CommandType.ADMIN_OPEN_USERS.name().toLowerCase();

		try {
			receiver.banUser(userId, userEmail);
			router.generatePath(command, filter, pageNumber);
			router.setRouteType(RouteType.FORWARD);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}
		return router;
	}

}
