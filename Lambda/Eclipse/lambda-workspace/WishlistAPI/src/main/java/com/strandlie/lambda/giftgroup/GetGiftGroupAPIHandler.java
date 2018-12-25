package com.strandlie.lambda.giftgroup;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.GetAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class GetGiftGroupAPIHandler extends GetAPIHandler implements RequestHandler<GiftGroupRequest, GiftGroupResponse> {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

	@Override
	public GiftGroupResponse handleRequest(GiftGroupRequest request, Context context) {
		
		this.request = APIRequestIsGiftGroupRequest(request);
		this.response = new GiftGroupResponse();
		setContext(context);
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.GIFTGROUPTABLE);
			if (! resultSet.next()) {
				this.response.setId(this.request.getId());
				this.response.setErrorMessage("No such object");
			} else {
				this.response.setName(resultSet.getString("name"));
				this.response.setDescription(resultSet.getString("description"));
				this.response.setPictureURL(resultSet.getString("pictureURL"));
			}
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not retrieve giftGroup with id: " + this.request.getId(), e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		logEnd();
		return this.response;
	}


}
