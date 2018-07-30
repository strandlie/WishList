package exceptions;

public class InvalidCreateRequestStateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294313038240145536L;
	
	
	/**
	 * Thrown when a requested field is not yet availiable
	 * @param fieldName
	 */
	public InvalidCreateRequestStateException(String fieldName) {
		super(fieldName + " is requested, but is not availiable " + 
				"as the row is not yet created in the database");
		
	}

}
