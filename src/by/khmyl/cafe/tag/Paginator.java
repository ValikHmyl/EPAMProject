package by.khmyl.cafe.tag;

/**
 * Provides methods for pagination.
 */
class Paginator {
	private String commandName;
	private int limit;
	private String filter;
	private int total = 0;
	private int pageNumber = 1;
	private int countLinks = 3;
	private String className = "pagination";
	private String disabledClass = "disabled";
	private String activeClass = "active";

	/**
	 * Instantiates a new paginator.
	 *
	 * @param limit limit of items on one page
	 * @param total total amount items
	 * @param commandName command name
	 * @param filter the filter
	 */
	public Paginator(int limit, int total, String commandName,String filter) {
		this.limit = limit;
		this.total = total;
		this.commandName = commandName;
		this.filter=filter;
	}

	/**
	 * Sets the page number.
	 *
	 * @param pageNumber the new page number
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * Sets the filter.
	 *
	 * @param filter the new filter
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Sets the count links.
	 *
	 * @param countLinks the new count links
	 */
	public void setCountLinks(int countLinks) {
		this.countLinks = countLinks;
	}

	/**
	 * Sets the class name.
	 *
	 * @param className the new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Sets the disabled class.
	 *
	 * @param disabledClass the new disabled class
	 */
	public void setDisabledClass(String disabledClass) {
		this.disabledClass = disabledClass;
	}

	/**
	 * Sets the active class.
	 *
	 * @param activeClass the new active class
	 */
	public void setActiveClass(String activeClass) {
		this.activeClass = activeClass;
	}

	/**
	 * Generate html code.
	 *
	 * @return the string
	 */
	public String generate() {
		StringBuilder builder = new StringBuilder();

		final int lastPage = total / limit + ((total % limit > 0) ? 1 : 0);
		final int startLink = ((pageNumber - countLinks) > 0) ? pageNumber - countLinks : 1;
		final int endLink = ((pageNumber + countLinks) < lastPage) ? pageNumber + countLinks : lastPage;

		builder.append("<ul class=\"").append(className).append("\">");
		generatePreviousLink(builder);

		if (startLink > 1) {
			builder.append("<li><a href=\"?command=").append(commandName);
			builder.append("&filter=").append(filter);
			builder.append("&pageNumber=1\">1</a></li>");
			builder.append("<li class=\"").append(disabledClass).append("\">");
			builder.append("<span>...</span></li>");
		}

		for (int i = startLink; i <= endLink; i++) {
			boolean isCurrentPage = this.pageNumber == i;
			builder.append("<li");

			if (isCurrentPage) {
				builder.append(" class=\"").append(activeClass).append("\"><a>");
			} else {
				builder.append("><a href=\"?command=").append(commandName);
				builder.append("&filter=").append(filter);
				builder.append("&pageNumber=").append(i).append("\">");
			}

			builder.append(i).append("</a></li>");
		}

		if (endLink < lastPage) {
			builder.append("<li class=\"").append(disabledClass).append("\"><span>...</span></li>");
			builder.append("<li><a href=\"?command=").append(commandName);
			builder.append("&filter=").append(filter);
			builder.append("&pageNumber=").append(lastPage).append("\">");
			builder.append(lastPage).append("</a></li>");
		}

		generateNextLink(builder, lastPage);
		builder.append("</ul>");

		return builder.toString();
	}

	private void generateLink(StringBuilder builder, int defaultPage, int wishedPage, int toPage) {
		boolean isThisCurrentPage = this.pageNumber == wishedPage;
		final int previousPage = isThisCurrentPage ? defaultPage : toPage;

		builder.append("<li");

		if (isThisCurrentPage) {
			builder.append(" class=\"").append(disabledClass);
			builder.append("\"><a>");
		} else {
			builder.append("><a href=\"?command=").append(commandName);
			builder.append("&filter=").append(filter);
			builder.append("&pageNumber=").append(previousPage);
			builder.append("\">");
		}

		builder.append("&").append(wishedPage == 1 ? "l" : "r").append("aquo;</a></li>");
	}

	private void generatePreviousLink(StringBuilder builder) {
		generateLink(builder, 1, 1, pageNumber - 1);
	}

	private void generateNextLink(StringBuilder builder, int lastPage) {
		generateLink(builder, pageNumber, lastPage, pageNumber + 1);
	}

}