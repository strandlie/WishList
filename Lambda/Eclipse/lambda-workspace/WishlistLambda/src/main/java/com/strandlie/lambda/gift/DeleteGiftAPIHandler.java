package com.strandlie.lambda.gift;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.DeleteAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class DeleteGiftAPIHandler extends DeleteAPIHandler implements RequestHandler<GiftRequest, GiftResponse> {
	
	private GiftRequest request;
	private GiftResponse response;

	/**
	 * This method is assuming that by the time the request reaches this point
	 * the IDs are already validated client-side
	 */
	@Override
	public GiftResponse handleRequest(GiftRequest request, Context context) {
		
		this.request = APIRequestIsGiftRequest(request);
		this.response = new GiftResponse();
		setContext(context);
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.GIFTTABLE, APIHandler.IDCOLUMN);
			response.setGiftIsDeleted(true);
			
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not create connection and delete gift", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		return response;
		
		
	}


}
