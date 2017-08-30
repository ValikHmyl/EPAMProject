package by.khmyl.cafe.command.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;

/**
 * Command for changing user's email in profile.
 */
public class ChangeEmailCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);

	private UserReceiver receiver = new UserReceiverImpl();

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String newEmail = request.getParameter(Constant.NEW_EMAIL);
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(Constant.USER);
		JsonObject jsonObj = new JsonObject();
		if (user != null) {
			StringBuilder message = new StringBuilder();

			try {
				int userId = user.getId();
				if (receiver.changeEmail(userId, newEmail, message)) {
					jsonObj.addProperty(Constant.SUCCESS, true);
					user.setEmail(newEmail);
					session.setAttribute(Constant.USER, user);
				} else {
					jsonObj.addProperty(Constant.ERROR_MESSAGE, message.toString());
				}

			} catch (ReceiverException e) {
				LOGGER.log(Level.ERROR, e);
				jsonObj.addProperty(Constant.ERROR_MESSAGE, message.toString());
			}
		} else {
			jsonObj.addProperty(Constant.ERROR, true);
		}
		router.setJson(jsonObj.toString());

		return router;
	}
}
