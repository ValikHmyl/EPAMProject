package by.khmyl.cafe.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Base abstract class for all commands.
 */
public abstract class AbstractCommand {

	/**
	 * Execute.
	 *
	 * @param request parameter, which client sends to the server by HTTP protocol 
	 * and waits for processing it.
	 * @return {@link by.khmyl.cafe.command.Router}
	 */
	public abstract Router execute(HttpServletRequest request);
}
