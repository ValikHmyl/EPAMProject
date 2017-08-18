package by.khmyl.cafe.command.util;

public class Router {
	public enum RouteType {
		FORWARD, REDIRECT;
	}

	private String path;
	private RouteType routeType;
	private String json;
 
	public Router() {
	}

	public Router(RouteType routeType) {
		this.routeType = routeType;
	}

	public Router(String path, RouteType routeType) {
		this.path = path;
		this.routeType = routeType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
