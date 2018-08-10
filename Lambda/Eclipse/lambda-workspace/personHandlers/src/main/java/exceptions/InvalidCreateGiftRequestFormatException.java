package exceptions;

public class InvalidCreateGiftRequestFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6732792234474124543L;

	public InvalidCreateGiftRequestFormatException() {
		super("Invalid Request. No giverID supplied, " + 
			 "but new gift is required to have a giverID when created");
	}
	
	public InvalidCreateGiftRequestFormatException(String invalidFieldName) {
		super("Invalid Request. No " + invalidFieldName + " supplied, " + 
			 "but new gift is required to have a " + invalidFieldName + 
			 " when created with ItemID");
	}
	
}
