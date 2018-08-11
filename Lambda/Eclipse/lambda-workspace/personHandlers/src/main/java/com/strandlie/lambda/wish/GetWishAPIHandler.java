package com.strandlie.lambda.wish;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.GetAPIHandler;
import exceptions.DatabaseErrorException;

public class GetWishAPIHandler extends GetAPIHandler {
	
	private WishRequest request;
	private WishResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
		this.request = APIRequestIsWishRequest(request);
		this.response = new WishResponse();
		setContext(context);
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.WISHTABLE);
			if (! resultSet.next()) {
				this.response.setId(this.request.getId());
				this.response.setErrorMessage("No such object");
			}
			else {
				this.response.setWisherID(resultSet.getInt("wisherID"));
				this.response.setItemID(resultSet.getInt("itemID"));
				this.response.setQuantity(resultSet.getInt("quantity"));
			}
		} catch (SQLException e) { 
			throw new DatabaseErrorException("Could not retrieve wish with id: " + this.request.getId().toString(), e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		logEnd();
		return this.response;
	}


}
