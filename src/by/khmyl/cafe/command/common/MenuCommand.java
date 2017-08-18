package by.khmyl.cafe.command.common;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;
import by.khmyl.cafe.entity.MenuItem;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.CommonReceiver;
import by.khmyl.cafe.receiver.impl.CommonReceiverImpl;

public class MenuCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(MenuCommand.class);
	private static final String CATEGORY = "category";
	private static final String MENU = "menu";

	private CommonReceiver receiver = new CommonReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		String category = request.getParameter(CATEGORY);
		Router router = new Router(PathConstant.MENU, RouteType.FORWARD);
		try {// mb if null add msg
			ArrayList<MenuItem> menuList = receiver.getMenu(category);
			request.setAttribute(MENU, menuList);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
		}

		return router;
	}

}
