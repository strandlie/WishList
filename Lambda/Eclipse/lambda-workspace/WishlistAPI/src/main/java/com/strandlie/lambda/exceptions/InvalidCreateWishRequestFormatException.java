package com.strandlie.lambda.exceptions;

public class InvalidCreateWishRequestFormatException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3816299256062308915L;

	public InvalidCreateWishRequestFormatException(String invalidFieldName) {
		super("Invalid Request. No " + invalidFieldName + " supplied, " + 
			 "but new wish is required to have wisherID, itemID and quantity when created");
		
	}
	
	
	

}
