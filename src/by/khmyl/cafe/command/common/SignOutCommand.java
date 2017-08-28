package by.khmyl.cafe.command.common;

import javax.servlet.http.HttpServletRequest;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.Router;
import by.khmyl.cafe.command.Router.RouteType;
import by.khmyl.cafe.constant.Constant;
import by.khmyl.cafe.constant.PathConstant;

/**
 * Command for Sign Out of system.
 */
public class SignOutCommand extends AbstractCommand {

	/* (non-Javadoc)
	 * @see by.khmyl.cafe.command.AbstractCommand#execute(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		request.getSession().removeAttribute(Constant.USER);
		return new Router(PathConstant.MAIN, RouteType.REDIRECT);
	}

}
