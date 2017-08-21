package by.khmyl.cafe.receiver.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.Part;

import by.khmyl.cafe.dao.OrderDAO;
import by.khmyl.cafe.dao.UserDAO;
import by.khmyl.cafe.dao.impl.OrderDAOImpl;
import by.khmyl.cafe.dao.impl.UserDAOImpl;
import by.khmyl.cafe.entity.Order;
import by.khmyl.cafe.entity.User;
import by.khmyl.cafe.exception.DAOException;
import by.khmyl.cafe.exception.ReceiverException;
import by.khmyl.cafe.receiver.UserReceiver;
import by.khmyl.cafe.util.EncryptManager;
import by.khmyl.cafe.util.UploadHelper;
import by.khmyl.cafe.util.Validator;

public class UserReceiverImpl extends UserReceiver {
	private static final String DEFAULT_AVATAR = "default_avatar.png";

	@Override
	public boolean changePassword(int userId, String newPassword, StringBuilder message) throws ReceiverException {
		boolean isValid = true;
		if (Validator.validatePassword(newPassword)) {
			UserDAO dao = new UserDAOImpl();
			try {
				newPassword = EncryptManager.enñrypt(newPassword);
				dao.changePassword(userId, newPassword);
			} catch (DAOException e) {
				message.append("Something goes wrong! Try again leter.");
				throw new ReceiverException("Changing password exception", e);
			}
		} else {
			message.append("Can't change password, wrong format of new password!");
			isValid = false;
		}
		return isValid;
	}

	@Override
	public boolean changeEmail(int userId, String newEmail, StringBuilder message) throws ReceiverException {
		boolean isValid = true;
		if (Validator.validateEmail(newEmail)) {
			UserDAO dao = new UserDAOImpl();
			try {
				if (dao.checkEmail(newEmail)) {
					dao.changeEmail(userId, newEmail);
				} else {
					message.append("This email already used, choose another.");
					isValid = false;
				}
			} catch (DAOException e) {
				message.append("Something goes wrong! Try again leter.");
				throw new ReceiverException("Changing email exception", e);
			}
		} else {
			message.append("Can't change email, wrong email format.");
			isValid = false;
		}
		return isValid;
	}

	@Override
	public void changeAvatar(User user, String savePath, Collection<Part> parts) throws ReceiverException {
		UserDAO dao = new UserDAOImpl();
		UploadHelper helper = new UploadHelper(savePath);
		try {
			File fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
			String imageName = null;
			for (Part part : parts) {
				imageName = helper.extractFileName(part);
				if (imageName != null) {
					imageName = helper.checkImageName(imageName);
					File currentAvatar = new File(savePath + File.separator + user.getAvatarImg());
					File defaultAvatar = new File(savePath + File.separator + DEFAULT_AVATAR);
					if (!currentAvatar.equals(defaultAvatar)) {
						currentAvatar.delete();
					}
					part.write(savePath + File.separator + imageName);
				}
			}
			dao.changeAvatar(user.getId(), imageName);
			user.setAvatarImg(imageName);
		} catch (DAOException e) {
			throw new ReceiverException("Changing avatar exception", e);
		} catch (IOException e) {

		}

	}

	@Override
	public ArrayList<Order> getOrders(int userId) throws ReceiverException {
		OrderDAO dao = new OrderDAOImpl();
		ArrayList<Order> orders = null;
		try {
			orders = dao.findUserOrders(userId);
		} catch (DAOException e) {
			throw new ReceiverException("Finding  orders exception", e);
		}
		return orders;
	}

}
