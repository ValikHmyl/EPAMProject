package by.khmyl.cafe.command.common;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.CommonReceiver;
import by.khmyl.cafe.receiver.impl.CommonReceiverImpl;

/**
 * Command for Sign In system.
 */
public class SignInCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String USER = "user";
	private static final String ERROR_MESSAGES = "errorMessages";
	private CommonReceiver receiver = new CommonReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(RouteType.REDIRECT);
		String username = request.getParameter(USERNAME);
		String password = request.getParameter(PASSWORD);
		ArrayList<String> errorMessages = new ArrayList<>();
		try {
			User user = receiver.signIn(username, password, errorMessages);
			if (errorMessages.isEmpty()) {
				HttpSession session = request.getSession(true);
				session.setAttribute(USER, user);
				router.setPath(PathConstant.MAIN);
			} else {
				request.setAttribute(ERROR_MESSAGES, errorMessages);
				router.setPath(PathConstant.SIGN_IN);
				router.setRouteType(RouteType.FORWARD);
				
			}
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.FORWARD);
		}
		return router;

	}
}
