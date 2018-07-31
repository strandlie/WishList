package exceptions;

public class InvalidCreatePersonRequestFormatException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4940751137071657728L;

	public InvalidCreatePersonRequestFormatException(String invalidFieldName) {
		super("Invalid Request. No " + invalidFieldName + " supplied, " + 
			 "but new Person is required to have both first and last name when created");
	}

}
