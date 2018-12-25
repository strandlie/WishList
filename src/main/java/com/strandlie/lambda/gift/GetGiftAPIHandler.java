package com.strandlie.lambda.gift;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.GetAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class GetGiftAPIHandler extends GetAPIHandler implements RequestHandler<GiftRequest, GiftResponse> {
	
	private GiftRequest request;
	private GiftResponse response;

	@Override
	public GiftResponse handleRequest(GiftRequest request, Context context) {
		
		this.request = APIRequestIsGiftRequest(request);
		this.response = new GiftResponse();
		setContext(context);
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.GIFTTABLE);
			if (! resultSet.next()) {
				this.response.setId(this.request.getId());
				this.response.setErrorMessage("No such object");
			}
			else {
				this.response.setGiverID(resultSet.getInt("giverID"));
				this.response.setRecepientID(resultSet.getInt("recepientID"));
				this.response.setItemID(resultSet.getInt("itemID"));
				this.response.setQuantity(resultSet.getInt("quantity"));
			}
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not retrieve gift with id: " + this.request.getId().toString(), e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		logEnd();
		return this.response;
		
	}


}
