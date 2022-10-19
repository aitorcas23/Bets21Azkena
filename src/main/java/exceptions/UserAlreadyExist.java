package exceptions;

public class UserAlreadyExist extends Exception {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExist() {
		super();
	}

	/**
	 * This exception is triggered if the question already exists
	 * 
	 * @param s String of the exception
	 */
	public UserAlreadyExist(String s) {
		super(s);
	}
}
