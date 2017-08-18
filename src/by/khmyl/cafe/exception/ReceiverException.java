package by.khmyl.cafe.exception;

public class ReceiverException extends Exception {
	private static final long serialVersionUID = 1L;

	public ReceiverException() {
		super();
	}

	public ReceiverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReceiverException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReceiverException(String message) {
		super(message);
	}

	public ReceiverException(Throwable cause) {
		super(cause);
	}

}
