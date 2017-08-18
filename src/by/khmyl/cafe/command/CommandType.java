package by.khmyl.cafe.command;

import by.khmyl.cafe.command.common.ChangeLocaleCommand;
import by.khmyl.cafe.command.common.ErrorCommand;
import by.khmyl.cafe.command.common.MenuCommand;
import by.khmyl.cafe.command.common.SignInCommand;
import by.khmyl.cafe.command.common.SignOutCommand;
import by.khmyl.cafe.command.common.SignUpCommand;
import by.khmyl.cafe.command.order.AddToCartCommand;
import by.khmyl.cafe.command.order.DeleteFromCartCommand;
import by.khmyl.cafe.command.order.MakeAnOrderCommand;
import by.khmyl.cafe.command.user.ChangeAvatarCommand;
import by.khmyl.cafe.command.user.ChangeEmailCommand;
import by.khmyl.cafe.command.user.ChangePasswordCommand;
import by.khmyl.cafe.command.user.GetOrdersCommand;

public enum CommandType {
	SIGN_IN(new SignInCommand()), SIGN_OUT(new SignOutCommand()), SIGN_UP(new SignUpCommand()), CHANGE_LOCALE(
			new ChangeLocaleCommand()), MENU(new MenuCommand()), ADD_TO_CART(new AddToCartCommand()), ERROR(
					new ErrorCommand()), DELETE_FROM_CART(
							new DeleteFromCartCommand()), ORDER(new MakeAnOrderCommand()), CHANGE_PASSWORD(
									new ChangePasswordCommand()), CHANGE_EMAIL(new ChangeEmailCommand()), CHANGE_AVATAR(
											new ChangeAvatarCommand()), ORDERS(new GetOrdersCommand());

	private AbstractCommand command;

	CommandType(AbstractCommand command) {
		this.command = command;
	}

	public AbstractCommand getCommand() {
		return command;
	}
}
