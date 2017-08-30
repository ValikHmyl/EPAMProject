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
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;
import by.khmyl.cafe.util.PaginationHelper;

/**
 * Command for opening admin's page with information about users.
 */
public class AdminOpenUsersCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(AdminOpenUsersCommand.class);

	private AdminReceiver receiver = new AdminReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String pageNumber = request.getParameter(Constant.PAGE_NUMBER);
		int startIndex = (pageNumber != null) ? Integer.valueOf(pageNumber) : 1;
		startIndex = (startIndex - 1) * Constant.MAX_ON_PAGE;
		String filter = request.getParameter(Constant.FILTER);
		PaginationHelper<User> users = null;
		try {
			users = receiver.openUsers(startIndex, filter);
			request.setAttribute(Constant.USERS, users.getItems());
			request.setAttribute(Constant.LIMIT,Constant.MAX_ON_PAGE);
			request.setAttribute(Constant.TOTAL_AMOUNT, users.getAmount());
			request.setAttribute(Constant.FILTER, filter);
			request.setAttribute(Constant.PAGE_NUMBER, pageNumber);
				
			router.setPath(PathConstant.ADMIN_USERS);
			router.setRouteType(RouteType.FORWARD);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}

		return router;
	}
}
