package by.khmyl.cafe.command;

import javax.servlet.http.HttpServletRequest;

import by.khmyl.cafe.command.util.Router;

public abstract class AbstractCommand {

	public abstract Router execute(HttpServletRequest request);
}
