package by.khmyl.cafe.command;

import by.khmyl.cafe.command.admin.ActivateUserCommand;
import by.khmyl.cafe.command.admin.AddMenuItemCommand;
import by.khmyl.cafe.command.admin.AdminOpenMenuCommand;
import by.khmyl.cafe.command.admin.AdminOpenOrdersCommand;
import by.khmyl.cafe.command.admin.AdminOpenProfileCommand;
import by.khmyl.cafe.command.admin.AdminOpenUsersCommand;
import by.khmyl.cafe.command.admin.BanUserCommand;
import by.khmyl.cafe.command.admin.ConfirmPaymentCommand;
import by.khmyl.cafe.command.admin.RemoveFromMenuCommand;
import by.khmyl.cafe.command.admin.ReturnToMenuCommand;
import by.khmyl.cafe.command.common.ChangeLocaleCommand;
import by.khmyl.cafe.command.common.ErrorCommand;
import by.khmyl.cafe.command.common.MenuCommand;
import by.khmyl.cafe.command.common.SignInCommand;
import by.khmyl.cafe.command.common.SignOutCommand;
import by.khmyl.cafe.command.common.SignUpCommand;
import by.khmyl.cafe.command.order.AddToCartCommand;
import by.khmyl.cafe.command.order.CancelOrderCommand;
import by.khmyl.cafe.command.order.DeleteFromCartCommand;
import by.khmyl.cafe.command.order.EditOrderCommand;
import by.khmyl.cafe.command.order.MakeAnOrderCommand;
import by.khmyl.cafe.command.order.SearchOrderCommand;
import by.khmyl.cafe.command.user.ChangeAvatarCommand;
import by.khmyl.cafe.command.user.ChangeEmailCommand;
import by.khmyl.cafe.command.user.ChangePasswordCommand;
import by.khmyl.cafe.command.user.UserOpenProfileCommand;
import by.khmyl.cafe.command.user.UserOpenOrdersCommand;

public enum CommandType {
	SIGN_IN(new SignInCommand()),
	SIGN_OUT(new SignOutCommand()),
	SIGN_UP(new SignUpCommand()),
	CHANGE_LOCALE(new ChangeLocaleCommand()),
	MENU(new MenuCommand()),
	ADD_TO_CART(new AddToCartCommand()),
	ERROR(new ErrorCommand()),
	DELETE_FROM_CART(new DeleteFromCartCommand()),
	ORDER(new MakeAnOrderCommand()),
	USER_OPEN_PROFILE(new UserOpenProfileCommand()),
	CHANGE_PASSWORD(new ChangePasswordCommand()),
	CHANGE_EMAIL(new ChangeEmailCommand()), 
	CHANGE_AVATAR(new ChangeAvatarCommand()),
	USER_OPEN_ORDERS(new UserOpenOrdersCommand()),
	CANCEL_ORDER(new CancelOrderCommand()),
	EDIT_ORDER(new EditOrderCommand()),
	ADMIN_OPEN_USERS(new AdminOpenUsersCommand()),
	ADMIN_OPEN_ORDERS(new AdminOpenOrdersCommand()),
	ADMIN_OPEN_MENU(new AdminOpenMenuCommand()),
	ADMIN_OPEN_PROFILE(new AdminOpenProfileCommand()),
	BAN_USER(new BanUserCommand()),
	ACTIVATE_USER(new ActivateUserCommand()),
	ADD_MENU(new AddMenuItemCommand()),
	REMOVE_FROM_MENU(new RemoveFromMenuCommand()),
	RETURN_TO_MENU(new ReturnToMenuCommand()),
	SEARCH_ORDER(new SearchOrderCommand()),
	CONFIRM_PAYMENT(new ConfirmPaymentCommand());
	private AbstractCommand command;

	CommandType(AbstractCommand command) {
		this.command = command;
	}

	public AbstractCommand getCommand() {
		return command;
	}
}
