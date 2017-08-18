package by.khmyl.cafe.command.common;

import javax.servlet.http.HttpServletRequest;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.PathConstant;
import by.khmyl.cafe.command.util.Router;
import by.khmyl.cafe.command.util.Router.RouteType;

public class ErrorCommand extends AbstractCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		return new Router(PathConstant.ERROR_404,RouteType.REDIRECT);
	}

}
