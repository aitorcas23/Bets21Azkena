package exceptions;

public class PronosticAlreadyExist extends Exception {
	private static final long serialVersionUID = 1L;

	public PronosticAlreadyExist() {
		super();
	}

	/**
	 * This exception is triggered if the question already exists
	 * 
	 * @param s String of the exception
	 */
	public PronosticAlreadyExist(String s) {
		super(s);
	}
}
