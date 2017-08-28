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
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.CommonReceiver;
import by.khmyl.cafe.receiver.impl.CommonReceiverImpl;

/**
 * Command for Sign Up user in system.
 */
public class SignUpCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

	private CommonReceiver receiver = new CommonReceiverImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String username = request.getParameter(Constant.USERNAME);
		String password = request.getParameter(Constant.PASSWORD);
		String repeatPassword = request.getParameter(Constant.REPEAT_PASSWORD);
		String email = request.getParameter(Constant.EMAIL);

		try {
			ArrayList<String> errorMessages = new ArrayList<>();
			if (receiver.signUp(username, password, repeatPassword, email, errorMessages)) {
				router.setPath(PathConstant.SIGN_IN);
				router.setRouteType(RouteType.REDIRECT);

			} else {
				request.setAttribute(Constant.ERROR_MESSAGES, errorMessages);
				request.setAttribute(Constant.USERNAME, username);
				request.setAttribute(Constant.EMAIL, email);
				router.setRouteType(RouteType.FORWARD);
				router.setPath(PathConstant.SIGN_UP);
			}
		} catch (ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setRouteType(RouteType.REDIRECT);
			router.setPath(PathConstant.ERROR_500);
		}
		return router;
	}

}
