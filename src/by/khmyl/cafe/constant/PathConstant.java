package by.khmyl.cafe.constant;

public class PathConstant {

	public static final String MAIN = "/index.jsp";
	public static final String SIGN_IN = "/jsp/common/sign_in.jsp";
	public static final String SIGN_UP = "/jsp/common/sign_up.jsp";
	public static final String ERROR_500 = "/jsp/error/500.jsp";
	public static final String ERROR_404 = "/jsp/error/404.jsp";
	public static final String MENU = "/jsp/common/menu.jsp";
	public static final String CART = "/jsp/common/cart.jsp";
	public static final String ORDER = "/jsp/order/order.jsp";
	public static final String PROFILE = "/jsp/user/profile.jsp";
	public static final String USER_ORDERS = "/jsp/user/user_orders.jsp";
	public static final String USER_ALL_ORDERS = "/controller?command=user_open_orders&filter=all&pageNumber=1";
	public static final String ADMIN = "/jsp/admin/admin.jsp";
	public static final String ADMIN_USERS = "/jsp/admin/users.jsp";
	public static final String ADMIN_ORDERS = "/jsp/admin/orders.jsp";
	public static final String ADMIN_ALL_ORDERS = "/controller?command=admin_open_orders&filter=all&pageNumber=1";
	public static final String ADMIN_ALL_USERS = "/controller?command=admin_open_users&filter=all&pageNumber=1";
	public static final String ADMIN_MENU = "/jsp/admin/menu.jsp";
	public static final String ADMIN_ALL_MENU = "/controller?command=admin_open_menu&filter=all&pageNumber=1";
	public static final String CONFIRM_PAYMENT = "/jsp/admin/confirm_payment.jsp";
	

	private PathConstant() {
	}
}
