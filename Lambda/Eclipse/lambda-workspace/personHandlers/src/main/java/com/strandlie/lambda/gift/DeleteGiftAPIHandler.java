package com.strandlie.lambda.gift;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.DeleteAPIHandler;
import exceptions.DatabaseErrorException;

public class DeleteGiftAPIHandler extends DeleteAPIHandler {
	
	private GiftRequest request;
	private GiftResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
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
