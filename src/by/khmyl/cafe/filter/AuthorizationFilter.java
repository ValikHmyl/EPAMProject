package by.khmyl.cafe.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.khmyl.cafe.constant.PathConstant;

@WebFilter(dispatcherTypes = { DispatcherType.FORWARD, DispatcherType.REQUEST }, urlPatterns = { "/jsp/admin/*",
		"/jsp/order/*", "/jsp/user/*" })
public class AuthorizationFilter implements Filter {

	private static final String USER = "user";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession(true);

		if (session.getAttribute(USER) == null) {
			((HttpServletResponse) response)
					.sendRedirect(((HttpServletRequest) request).getContextPath() + PathConstant.SIGN_IN);
		} else {

			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
