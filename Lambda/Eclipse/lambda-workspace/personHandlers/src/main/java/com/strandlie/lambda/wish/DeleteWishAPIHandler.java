package com.strandlie.lambda.wish;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.DeleteAPIHandler;
import exceptions.DatabaseErrorException;

public class DeleteWishAPIHandler extends DeleteAPIHandler {
	
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
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.WISHTABLE, APIHandler.IDCOLUMN);
			this.response.setWishIsDeleted(true);
		} catch (SQLException e){
			throw new DatabaseErrorException("Could not create connection and delete", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		return this.response;
	}

}
