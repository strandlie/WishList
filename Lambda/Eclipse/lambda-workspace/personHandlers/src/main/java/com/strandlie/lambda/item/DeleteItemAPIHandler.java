package com.strandlie.lambda.item;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.DeleteAPIHandler;
import exceptions.DatabaseErrorException;

public class DeleteItemAPIHandler extends DeleteAPIHandler {
	
	private ItemRequest request;
	private ItemResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
		this.request = APIRequestIsItemRequest(request);
		this.response = new ItemResponse();
		setContext(context);
        
        try {
        	super.handleRequest(this.request, this.response, APIHandler.ITEMTABLE, APIHandler.IDCOLUMN);
        	this.response.setItemIsDeleted(true);
        	
        } catch (SQLException e) {
        	throw new DatabaseErrorException("Could not create connection and delete item", e.toString());
        } finally {
        	closeDatabaseConnection();
        }
		return this.response;
	}
	
}
