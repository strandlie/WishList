package exceptions;

public class InvalidCreateRequestFormatException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4940751137071657728L;

	public InvalidCreateRequestFormatException(String invalidFieldName) {
		super("Invalid Request. No " + invalidFieldName + " supplied, " + 
			 "but new Person is required to have both first and last name when created");
	}

}
