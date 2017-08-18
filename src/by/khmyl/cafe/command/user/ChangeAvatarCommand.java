package by.khmyl.cafe.command.user;

import java.io.File;
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
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.receiver.impl.UserReceiverImpl;

public class ChangeAvatarCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(ChangeAvatarCommand.class);
	private static final String UPLOAD_PATH = "avatars";
	private static final String UPLOADS = "uploads";
	private static final String USER = "user";

	private UserReceiver receiver = new UserReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(RouteType.REDIRECT);
		String appPath = request.getServletContext().getInitParameter(UPLOADS);
		String savePath = appPath + File.separator + UPLOAD_PATH;
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(USER);
		Collection<Part> parts;
		try {
			parts = request.getParts();
			receiver.changeAvatar(user, savePath, parts);
			session.setAttribute(USER, user);
			router.setPath(PathConstant.PROFILE);
		} catch (IOException | ServletException | ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);
		}
		return router;
	}

}