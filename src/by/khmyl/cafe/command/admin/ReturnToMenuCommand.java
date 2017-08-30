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
 * Command for making items from menu active back.
 */
public class ReturnToMenuCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ReturnToMenuCommand.class);

	private AdminReceiver receiver = new AdminReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		int menuId = Integer.parseInt(request.getParameter(Constant.ITEM_ID));
		String filter = request.getParameter(Constant.FILTER);
		String pageNumber = request.getParameter(Constant.PAGE_NUMBER);
		String command = CommandType.ADMIN_OPEN_MENU.name().toLowerCase();
		try {
			receiver.returnToMenu(menuId);
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
