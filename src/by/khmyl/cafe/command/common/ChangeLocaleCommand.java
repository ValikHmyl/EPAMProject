package by.khmyl.cafe.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.constant.Constant;

/**
 * Command for changing language on pages.
 */
public class ChangeLocaleCommand extends AbstractCommand {

	/**
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		JsonObject jsonObj = new JsonObject();
		Router router = new Router();
		session.setAttribute(Constant.LOCALE, request.getParameter(Constant.LOCALE));
		jsonObj.addProperty(Constant.SUCCESS, true);
		router.setJson(jsonObj.toString());
		return router;
	}

}
