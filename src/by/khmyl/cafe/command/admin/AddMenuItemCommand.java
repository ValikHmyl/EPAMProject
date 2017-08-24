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
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.AdminReceiver;
import by.khmyl.cafe.receiver.impl.AdminReceiverImpl;

public class AddMenuItemCommand extends AbstractCommand {
	private static final Logger LOGGER = LogManager.getLogger(AddMenuItemCommand.class);
	private static final String NAME = "name";
	private static final String PRICE = "price";
	private static final String CATEGORY = "category";
	private static final String PORTION = "portion";
	private static final String UPLOAD_PATH = "img/menu";

	private AdminReceiver receiver = new AdminReceiverImpl();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router(RouteType.REDIRECT);
		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + UPLOAD_PATH;
		String menuName = request.getParameter(NAME);
		String category = request.getParameter(CATEGORY);
		BigDecimal price = new BigDecimal(request.getParameter(PRICE));
		String portion = request.getParameter(PORTION);

		Collection<Part> parts;
		try {
			parts = request.getParts();
			receiver.addMenu(menuName, category, price, portion, savePath, parts);
			router.setPath(PathConstant.ADMIN_MENU);
		} catch (IOException | ServletException | ReceiverException e) {
			LOGGER.log(Level.ERROR, e);
			router.setPath(PathConstant.ERROR_500);
		}
		return router;
	}

}
