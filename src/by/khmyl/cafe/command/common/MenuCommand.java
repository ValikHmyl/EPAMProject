package by.khmyl.cafe.command.common;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.CommonReceiver;
import by.khmyl.cafe.receiver.impl.CommonReceiverImpl;

/**
 * Command for present menu on page.
 */
public class MenuCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(MenuCommand.class);

	private CommonReceiver receiver = new CommonReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		String category = request.getParameter(Constant.CATEGORY);
		Router router = new Router();
		try {
			ArrayList<MenuItem> menuList = receiver.getMenu(category);
			request.setAttribute(Constant.MENU, menuList);
			router.setPath(PathConstant.MENU);
			router.setRouteType(RouteType.FORWARD);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setRouteType(RouteType.REDIRECT);
			router.setPath(PathConstant.ERROR_500);
		}

		return router;
	}

}
