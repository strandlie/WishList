package com.strandlie.lambda.giftgroup;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.DeleteAPIHandler;
import exceptions.DatabaseErrorException;

public class DeleteGiftGroupAPIHandler extends DeleteAPIHandler {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
		this.request = APIRequestIsGiftGroupRequest(request);
		this.response = new GiftGroupResponse();
		setContext(context);
		
		// super.handleRequest() validates the input-object
		try {
			super.handleRequest(this.request, this.response, APIHandler.GIFTGROUPTABLE, APIHandler.IDCOLUMN);
			this.response.setGiftGroupIsDeleted(true);
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not delete the giftGroup from the database", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		return this.response;
	}


}
