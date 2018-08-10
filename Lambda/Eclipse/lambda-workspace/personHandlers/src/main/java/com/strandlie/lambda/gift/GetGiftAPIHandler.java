package com.strandlie.lambda.gift;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.GetAPIHandler;
import exceptions.DatabaseErrorException;

public class GetGiftAPIHandler extends GetAPIHandler {
	
	private GiftRequest request;
	private GiftResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
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
