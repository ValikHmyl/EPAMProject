package by.khmyl.cafe.filter;

import java.io.IOException;

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
import by.khmyl.cafe.entity.User;

@WebFilter(urlPatterns = { "/jsp/user/*","/jsp/order/*" })
public class PermissionUserFilter implements Filter {
	private static final String USER = "user";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession(true);
		User user = (User) session.getAttribute(USER);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (user.getRole()) {
			resp.sendRedirect(req.getContextPath() + PathConstant.ADMIN);
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
