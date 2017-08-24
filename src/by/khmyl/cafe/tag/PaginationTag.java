package by.khmyl.cafe.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class PaginationTag extends TagSupport {
	private static final String PAGE_NUMBER = "pageNumber";

	private String command;

	private int total;

	private int limit;

	private String filter;

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public int doStartTag() throws JspException {
		if (limit >= total) {
			return SKIP_BODY;
		}

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Paginator paginator = new Paginator(limit, total, command, filter);

		String pageNumber = request.getParameter(PAGE_NUMBER);

		try {
			if (pageNumber != null) {
				int page = Integer.valueOf(pageNumber);
				paginator.setPageNumber(page);
			}

			JspWriter out = pageContext.getOut();
			out.write(paginator.generate());

		} catch (IOException e) {
			throw new JspException("Write output error", e);
		}

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}