package by.khmyl.cafe.command.user;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;

/**
 * Command for changing user's avatar in profile.
 */
public class ChangeAvatarCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ChangeAvatarCommand.class);

	private UserReceiver receiver = new UserReceiverImpl();

	/** 
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + Constant.UPLOAD_PATH_AVATARS;
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(Constant.USER);
		Collection<Part> parts;
		try {
			parts = request.getParts();
			receiver.changeAvatar(user, savePath, parts);
			session.setAttribute(Constant.USER, user);
			router.setPath(PathConstant.PROFILE);
			router.setRouteType(RouteType.REDIRECT);
		} catch (IOException | ServletException | ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);

		}
		return router;
	}

}