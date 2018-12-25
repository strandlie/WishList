package com.strandlie.lambda.giftgroup;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.AddAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;
import com.strandlie.lambda.exceptions.InvalidCreateGiftGroupRequestFormatException;

public class AddGiftGroupAPIHandler extends AddAPIHandler implements RequestHandler<GiftGroupRequest, GiftGroupResponse> {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

	@Override
	public GiftGroupResponse handleRequest(GiftGroupRequest request, Context context) {
		
		this.request = APIRequestIsGiftGroupRequest(request);
		this.response = new GiftGroupResponse();
		setContext(context);
		
		// Validates the request object here. 
		if (this.request.getName() == null) {
			throw new InvalidCreateGiftGroupRequestFormatException();
		}
		
		try {
			// super.handleRequest() adds the newly created ID to the response-object
			super.handleRequest(this.request, this.response, APIHandler.GIFTGROUPTABLE);
			this.response.setGiftGroupIsAdded(true);
			
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not add the giftGroup to the database", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		return this.response;
	}
}
