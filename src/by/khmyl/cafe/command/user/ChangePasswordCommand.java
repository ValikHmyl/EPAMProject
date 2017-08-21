package by.khmyl.cafe.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;

/**
 * Command for changing user's password.
 */
public class ChangePasswordCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);
	private static final String NEW_PASSWORD = "newPassword";
	private static final String USER = "user";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final String ERROR_MSG = "errorMsg";

	private UserReceiver receiver = new UserReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String newPassword = request.getParameter(NEW_PASSWORD);
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(USER);
		JsonObject jsonObj = new JsonObject();
		if (user != null) {
			StringBuilder message = new StringBuilder();
			try {
				int userId = user.getId();
				if (receiver.changePassword(userId, newPassword, message)) {
					jsonObj.addProperty(SUCCESS, true);
				} else {
					jsonObj.addProperty(ERROR_MSG, message.toString());
				}

			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				jsonObj.addProperty(ERROR_MSG, message.toString());
			}
		} else {
			jsonObj.addProperty(ERROR, true);
		}
		router.setJson(jsonObj.toString());
		return router;
	}

}
