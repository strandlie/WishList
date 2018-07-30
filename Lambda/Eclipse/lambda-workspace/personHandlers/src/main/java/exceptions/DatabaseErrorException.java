package exceptions;

public class DatabaseErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8460699872735061686L;
	
	public DatabaseErrorException(String input, String errorMessage) {
		super("DatabaseError: " + input + " \nErrorMessage: " + errorMessage);
	}

}
