package com.strandlie.lambda.item;

import java.sql.SQLException;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import exceptions.DatabaseErrorException;
import exceptions.InvalidUpdateRequestFormatException;

public class UpdateItemAPIHandler extends APIHandler {
	
	private ItemRequest request;

    @Override
    public APIResponse handleRequest(APIRequest request, Context context) {
        context.getLogger().log("Received update request: " + request.toString() + "\n");
        
        this.request = APIRequestIsItemRequest(request);
        ItemResponse response = new ItemResponse();
        response.setItemIsUpdated(false);
        
        Integer id = this.request.getId();
        if (id == null) {
        	throw new InvalidUpdateRequestFormatException("Invalid ID for update: " + this.request.getId().toString());
        }
        Map<String, String> notNullFieldNames = this.request.getNotNullFields();
        
        try {
        	getConnection();
        	
        	// Allowing all updates to be added before the commit takes place. 
        	connection.setAutoCommit(false);
        	
        	for (String fieldName : notNullFieldNames.keySet()) {
        		String newValue = notNullFieldNames.get(fieldName);
        		
        		// Is really switching based on types here. Price and id are the only non-String columns
        		switch(fieldName) {
        			case "id":
        				break;
        			case "price":
        				Double newDoubleValue = Double.parseDouble(newValue);
        				updateInDatabase(APIHandler.ITEMTABLE, fieldName, newDoubleValue, APIHandler.IDCOLUMN, id);
        				break;
        			default:
        				updateInDatabase(APIHandler.ITEMTABLE, fieldName, newValue, APIHandler.IDCOLUMN, id);
        				break;
        		}
        	}
        	connection.commit();
        	response.setItemIsUpdated(true);
        	response.setId(id);
        }
        catch (SQLException e) {
        	throw new DatabaseErrorException("Could not create connection and update.", e.toString());
        } finally {
        	closeDatabaseConnection();
        }
        context.getLogger().log("\nUpdate successfully processed\n");
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
