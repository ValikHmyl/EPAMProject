package by.khmyl.cafe.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.khmyl.cafe.command.AbstractCommand;
import by.khmyl.cafe.command.util.CommandProvider;
import by.khmyl.cafe.command.util.Router;

@WebServlet("/controller")
@MultipartConfig(fileSizeThreshold=1024*1024*5, maxFileSize=1024*1024*50, maxRequestSize=1024*1024*50)

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CommandProvider provider = new CommandProvider();
		AbstractCommand command = provider.defineCommand(request);
		Router router = command.execute(request);
		switch (router.getRouteType()) {
		case FORWARD: {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPath());
			dispatcher.forward(request, response);
			break;
		}
		case REDIRECT: {
			response.sendRedirect(request.getContextPath() + router.getPath());
			break;
		}
		default: {
			response.sendError(500);
		}
		}
	}
}
