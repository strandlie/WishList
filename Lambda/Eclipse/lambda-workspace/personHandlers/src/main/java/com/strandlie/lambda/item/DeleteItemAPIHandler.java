package com.strandlie.lambda.item;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import exceptions.DatabaseErrorException;
import exceptions.InvalidUpdateRequestFormatException;

public class DeleteItemAPIHandler extends APIHandler {
	
	private ItemRequest request;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		context.getLogger().log("Received delete request: \n" + request.toString());
		
		this.request = APIRequestIsItemRequest(request);
		ItemResponse response = new ItemResponse();
		response.setItemIsDeleted(false);
		
		Integer id = this.request.getId();
        if (id == null) {
        	throw new InvalidUpdateRequestFormatException("Invalid ID for delete: " + request.getId().toString());
        }
        
        try {
        	getConnection();
        	deleteFromDatabase(id);
        	response.setItemIsDeleted(true);
        	response.setId(id);
        	
        } catch (SQLException e) {
        	throw new DatabaseErrorException("Could not create connection and delete", e.toString());
        } finally {
        	closeDatabaseConnection();
        }
		context.getLogger().log("\nDelete successfully processed");
		return response;
	}

	
	private ItemRequest APIRequestIsItemRequest(APIRequest request) {
		try {
			ItemRequest r = (ItemRequest) request;
			return r;
		}
		catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" an ItemRequest.");
		}
	}
}
