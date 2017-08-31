package by.khmyl.cafe.command.common;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import by.khmyl.cafe.receiver.CommonReceiver;
import by.khmyl.cafe.receiver.impl.CommonReceiverImpl;

/**
 * Command for Sign In system.
 */
public class SignInCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);

	private CommonReceiver receiver = new CommonReceiverImpl();

	/** 
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String username = request.getParameter(Constant.USERNAME);
		String password = request.getParameter(Constant.PASSWORD);
		ArrayList<String> errorMessages = new ArrayList<>();
		try {
			User user = receiver.signIn(username, password, errorMessages);
			if (errorMessages.isEmpty()) {
				HttpSession session = request.getSession(true);
				session.setAttribute(Constant.USER, user);
				if (user.getRole()) {
					router.setPath(PathConstant.OPEN_ADMIN);
				} else {
					router.setPath(PathConstant.OPEN_USER+user.getId());
				}
				router.setRouteType(RouteType.REDIRECT);
			} else {
				request.setAttribute(Constant.ERROR_MESSAGES, errorMessages);
				router.setPath(PathConstant.SIGN_IN);
				router.setRouteType(RouteType.FORWARD);
				request.setAttribute(Constant.USERNAME, username);

			}
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setRouteType(RouteType.REDIRECT);
			router.setPath(PathConstant.ERROR_500);
		}
		return router;

	}
}
