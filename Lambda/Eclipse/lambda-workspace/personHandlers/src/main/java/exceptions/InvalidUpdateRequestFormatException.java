package exceptions;

public class InvalidUpdateRequestFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5096723541874364084L;
	
	public InvalidUpdateRequestFormatException(String input) {
		super("Invalid Request. " + input);
	}

}
