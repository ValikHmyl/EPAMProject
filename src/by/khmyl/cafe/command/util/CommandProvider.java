package by.khmyl.cafe.command.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.CommandType;

public class CommandProvider {
	
	private static final Logger LOGGER = LogManager.getLogger(CommandProvider.class);
	
	/**
	 * Define command from request and return concrete one
	 *
	 * @param request parameter, which client sends to the server by HTTP protocol 
	 * and waits for processing it.
	 * @return concrete command that appropriate to given parameter 
	 */
	public AbstractCommand defineCommand(HttpServletRequest request) {
		AbstractCommand current = null;
		String commandName = request.getParameter("command");
		if (commandName != null) {
			try {
				CommandType currentEnum = CommandType.valueOf(commandName.toUpperCase());
				current = currentEnum.getCommand();
			} catch (IllegalArgumentException e) {
				current=CommandType.ERROR.getCommand();
				LOGGER.log(Level.ERROR, "Can't define command."+e.getMessage());
			}
		} else {
			current=CommandType.ERROR.getCommand();
		}
		return current;
	}
}
