package exceptions;

public class InvalidCreateItemRequestFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2425804164122364672L;
	
	public InvalidCreateItemRequestFormatException(String invalidFieldName) {
		super("Invalid Request. No " + invalidFieldName + " supplied, " + 
			 "but new item is required to have a title when created");
	}

}
