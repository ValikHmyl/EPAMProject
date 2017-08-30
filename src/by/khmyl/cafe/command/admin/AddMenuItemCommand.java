package by.khmyl.cafe.command.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;

public class AddMenuItemCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(AddMenuItemCommand.class);

	private AdminReceiver receiver = new AdminReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + Constant.UPLOAD_PATH_MENU;
		String menuName = request.getParameter(Constant.NAME);
		String category = request.getParameter(Constant.CATEGORY);
		String portion = request.getParameter(Constant.PORTION);
		BigDecimal price = new BigDecimal(request.getParameter(Constant.PRICE));
		price.setScale(2, BigDecimal.ROUND_HALF_UP);
		Collection<Part> parts;
		try {
			parts = request.getParts();
			receiver.addMenu(menuName, category, price, portion, savePath, parts);
			router.setPath(PathConstant.ADMIN_ADD_MENU);
			router.setRouteType(RouteType.REDIRECT);

		} catch (IOException | ServletException | ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
			router.setRouteType(RouteType.REDIRECT);

		}
		return router;
	}

}
