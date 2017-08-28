package by.khmyl.cafe.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.khmyl.cafe.constant.Constant;

public class MailSender extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(MailSender.class);
	private Session session;
	private String subject;
	private String content;
	private String toEmail;

	public MailSender(String subject, String content, String toEmail) {
		init();
		this.subject = subject;
		this.content = content;
		this.toEmail = toEmail;
	}

	public void run() {
		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setContent(content, "text/html");
			Transport.send(message);
		} catch (MessagingException e) {
			LOGGER.log(Level.ERROR, "Can't send message on email " + toEmail + ": ", e);
		}
	}

	private void init() {
		ResourceBundle resource = ResourceBundle.getBundle("resources.mail");
		String username = resource.getString(Constant.USERNAME);
		String password = resource.getString(Constant.PASSWORD);
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.ssl.trust", resource.getString(Constant.HOST));
		props.put("mail.smtp.host", resource.getString(Constant.HOST));
		props.put("mail.smtp.port", resource.getString(Constant.PORT));

		session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
}
