package com.strandlie.lambda.wish;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.DeleteAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class DeleteWishAPIHandler extends DeleteAPIHandler implements RequestHandler<WishRequest, WishResponse> {
	
	private WishRequest request;
	private WishResponse response;

	/**
	 * This method is assuming that by the time the request reaches this point
	 * the IDs are already validated client-side
	 */
	@Override
	public WishResponse handleRequest(WishRequest request, Context context) {
		
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
