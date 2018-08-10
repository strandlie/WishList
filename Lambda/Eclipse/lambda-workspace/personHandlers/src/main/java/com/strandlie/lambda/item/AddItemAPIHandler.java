package com.strandlie.lambda.item;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.AddAPIHandler;
import exceptions.DatabaseErrorException;
import exceptions.InvalidCreateItemRequestFormatException;

public class AddItemAPIHandler extends AddAPIHandler {

	private ItemRequest request;
	private ItemResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
        
		this.request = APIRequestIsItemRequest(request);
        this.response = new ItemResponse();
        setContext(context);
        
        // Validates request-object
        if (this.request.getTitle() == null) {
			throw new InvalidCreateItemRequestFormatException("title");
        }
		
        try {
			super.handleRequest(this.request,  this.response, APIHandler.ITEMTABLE);
			response.setItemIsAdded(true);
		}
		catch (SQLException e) {
			throw new DatabaseErrorException("Could not add the item to the database", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		return this.response;
	}
}
