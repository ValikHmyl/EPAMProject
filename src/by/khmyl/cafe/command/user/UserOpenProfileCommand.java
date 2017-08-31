package by.khmyl.cafe.command.user;

import java.util.HashMap;

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
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;

/**
 *  Command for opening user's profile page with some kind of statistic and general information.
 */
public class UserOpenProfileCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(UserOpenProfileCommand.class);

	private UserReceiver receiver = new UserReceiverImpl();

	/**
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		int userId = Integer.parseInt(request.getParameter(Constant.USER_ID));
		try {
			HashMap<String, Long> orderStatistic = receiver.openProfile(userId);
			request.setAttribute("orderStat", orderStatistic);
			router.setRouteType(RouteType.FORWARD);
			router.setPath(PathConstant.PROFILE);
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}
		return router;

	}

}
