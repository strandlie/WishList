package com.strandlie.lambda.exceptions;

public class InvalidCreateGiftGroupRequestFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8747088599454996030L;
	
	public InvalidCreateGiftGroupRequestFormatException() {
		super("Invalid Request. No giftGroupName is supplied, " +
				" but new giftGroup is required to have name");
	}

}
