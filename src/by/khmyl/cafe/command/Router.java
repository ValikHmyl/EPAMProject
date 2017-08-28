package by.khmyl.cafe.command;

/**
 * Class for keeping information about page path and route type.
 */
public class Router {

	/**
	 * Types of redirecting on another page.
	 */
	public enum RouteType {
		FORWARD, REDIRECT
	}

	private String path;
	private RouteType routeType;
	private String json;

	/**
	 * Instantiates a new router.
	 */
	public Router() {
	}

	/**
	 * Instantiates a new router.
	 *
	 * @param path
	 *            the path
	 * @param routeType
	 *            the route type
	 */
	public Router(String path, RouteType routeType) {
		this.path = path;
		this.routeType = routeType;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the route type.
	 *
	 * @return the route type
	 */
	public RouteType getRouteType() {
		return routeType;
	}

	/**
	 * Sets the route type.
	 *
	 * @param routeType
	 *            the new route type
	 */
	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	/**
	 * Gets the json for ajax.
	 *
	 * @return the json
	 */
	public String getJson() {
		return json;
	}

	/**
	 * Sets the json for ajax.
	 *
	 * @param json
	 *            the new json
	 */
	public void setJson(String json) {
		this.json = json;
	}

	public void generatePath(String cmd, String filter, String pageNumber) {
		path = "/controller?command=" + cmd + "&filter=" + filter + "&pageNumber=" + pageNumber;
	}

}
