package by.khmyl.cafe.command.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.command.user.UserOpenProfileCommand;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;

public class AdminOpenProfileCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(UserOpenProfileCommand.class);

	private AdminReceiver receiver = new AdminReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		try {
			HashMap<String, Long> generalStatistic = receiver.openProfile();
			request.setAttribute("generalStat", generalStatistic);
			router.setRouteType(RouteType.FORWARD);
			router.setPath(PathConstant.ADMIN);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}
		return router;
	}

}
