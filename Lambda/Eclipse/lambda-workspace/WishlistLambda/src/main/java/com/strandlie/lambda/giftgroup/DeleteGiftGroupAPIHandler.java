package com.strandlie.lambda.giftgroup;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.DeleteAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class DeleteGiftGroupAPIHandler extends DeleteAPIHandler implements RequestHandler<GiftGroupRequest, GiftGroupResponse> {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

	@Override
	public GiftGroupResponse handleRequest(GiftGroupRequest request, Context context) {
		
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
