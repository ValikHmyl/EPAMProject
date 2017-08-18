package by.khmyl.cafe.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.Router;

public class ChangeLocaleCommand extends AbstractCommand {
	private static final String LOCALE = "locale";
	private static final String SUCCESS = "success";

	@Override
	public Router execute(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		JsonObject jsonObj = new JsonObject();
		Router router = new Router();
		session.setAttribute(LOCALE, request.getParameter(LOCALE));
		jsonObj.addProperty(SUCCESS, true);
		router.setJson(jsonObj.toString());
		return router;
	}

}
