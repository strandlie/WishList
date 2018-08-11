package com.strandlie.lambda.wish;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.AddAPIHandler;
import exceptions.DatabaseErrorException;
import exceptions.InvalidCreateWishRequestFormatException;

public class AddWishAPIHandler extends AddAPIHandler {
	
	private WishRequest request;
	private WishResponse response;

	/**
	 * This method is assuming that by the time the request reaches this point
	 * the IDs are already validated client-side
	 */
	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		this.request = APIRequestIsWishRequest(request);
		this.response = new WishResponse();
		setContext(context);
		
		// Validates the request-object here
		if (this.request.getWisherID() == null) {
			throw new InvalidCreateWishRequestFormatException("wisherID");
		}
		if (this.request.getItemID() == null) {
			throw new InvalidCreateWishRequestFormatException("itemID");
		}
		if (this.request.getQuantity() == null) {
			throw new InvalidCreateWishRequestFormatException("quantity");
		}
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.WISHTABLE);
			this.response.setWishIsAdded(true);
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not add the gift to the database", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		return this.response;
	}


}
