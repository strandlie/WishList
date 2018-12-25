package com.strandlie.lambda.exceptions;

public class InvalidRetrieveRequestFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6738676807194652798L;
	
	
	public InvalidRetrieveRequestFormatException(String input) {
		super("Invalid Request:" + input + "\nNo ID present.\n");
	}

}
