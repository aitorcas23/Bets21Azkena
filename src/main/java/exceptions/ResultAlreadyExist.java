package exceptions;

public class ResultAlreadyExist extends Exception {
	private static final long serialVersionUID = 1L;

	public ResultAlreadyExist() {
		super();
	}

	/**
	 * This exception is triggered if the event already exists
	 * 
	 * @param s String of the exception
	 */
	public ResultAlreadyExist(String s) {
		super(s);
	}
}