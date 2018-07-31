package com.strandlie.lambda.person;

import java.sql.SQLException;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import exceptions.DatabaseErrorException;
import exceptions.InvalidUpdateRequestFormatException;

public class UpdatePersonAPIHandler extends APIHandler {
	
	private PersonRequest request;
	

    @Override
    public APIResponse handleRequest(APIRequest request, Context context) {
        context.getLogger().log("Received update request with new values: \n" + request.toString() + "\n");
        
        this.request = APIRequestIsPersonRequest(request);
    	PersonResponse response = new PersonResponse();
    	response.setPersonIsUpdated(false);
    	
    	Integer id = this.request.getId();
    	if (id == null) {
    		throw new InvalidUpdateRequestFormatException("Invalid ID for update: " + this.request.getId().toString());
    	}
    	Map<String, String> notNullFieldNames = this.request.getNotNullFields();
    	
    	try {
    		getConnection();
			connection.setAutoCommit(false);
			
			for (String fieldName : notNullFieldNames.keySet()) {
				String newValue = notNullFieldNames.get(fieldName);
				if (!(fieldName.equals("id"))) {
					updateInDatabase(APIHandler.PERSONTABLE, fieldName, newValue, APIHandler.IDCOLUMN, id);
				}
			}
			connection.commit();
			connection.close();
			response.setPersonIsUpdated(true);
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
	
    private PersonRequest APIRequestIsPersonRequest(APIRequest request) {
		try {
			PersonRequest r = (PersonRequest) request;
			return r;
		}
		catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" a PersonRequest.");
		}
	}

}
