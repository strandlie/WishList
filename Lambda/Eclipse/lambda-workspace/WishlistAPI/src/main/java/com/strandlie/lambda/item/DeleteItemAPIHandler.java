package com.strandlie.lambda.item;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.DeleteAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class DeleteItemAPIHandler extends DeleteAPIHandler implements RequestHandler<ItemRequest, ItemResponse> {
	
	private ItemRequest request;
	private ItemResponse response;

	@Override
	public ItemResponse handleRequest(ItemRequest request, Context context) {
		
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
