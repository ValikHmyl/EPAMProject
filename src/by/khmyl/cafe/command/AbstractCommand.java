package by.khmyl.cafe.command;

import javax.servlet.http.HttpServletRequest;

import by.khmyl.cafe.command.util.Router;

/**
 * Base abstract class for all commands.
 */
public abstract class AbstractCommand {

	/**
	 * Execute.
	 *
	 * @param request parameter, which client sends to the server by HTTP protocol 
	 * and waits for processing it.
	 * @return {@link by.khmyl.cafe.command.util.Router}
	 */
	public abstract Router execute(HttpServletRequest request);
}
