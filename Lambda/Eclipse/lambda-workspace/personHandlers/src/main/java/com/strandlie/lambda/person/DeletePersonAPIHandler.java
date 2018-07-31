package com.strandlie.lambda.person;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import exceptions.DatabaseErrorException;
import exceptions.InvalidUpdateRequestFormatException;

public class DeletePersonAPIHandler extends APIHandler {
	
	private PersonRequest request;

    @Override
    public APIResponse handleRequest(APIRequest request, Context context) {
        context.getLogger().log("Received delete request: \n" + request.toString());
        
        this.request = APIRequestIsPersonRequest(request);
        PersonResponse response = new PersonResponse();
        response.setPersonIsDeleted(false);
        
        Integer id = this.request.getId();
        if (id == null) {
        	throw new InvalidUpdateRequestFormatException("Invalid ID for delete: " + request.getId().toString());
        }
        
        try {
        	getConnection();
        	deleteFromDatabase(APIHandler.PERSONTABLE, APIHandler.IDCOLUMN, id);
        	response.setPersonIsDeleted(true);
        	response.setId(id);
        } catch (SQLException e) {
        	throw new DatabaseErrorException("Could not create connection and delete", e.toString());
        } finally {
        	closeDatabaseConnection();
        }
        context.getLogger().log("\nDelete successfully processed");
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
