package com.strandlie.lambda.person;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.AddAPIHandler;
import exceptions.DatabaseErrorException;
import exceptions.InvalidCreatePersonRequestFormatException;

import java.sql.SQLException;


public class AddPersonAPIHandler extends AddAPIHandler {
	
	private PersonRequest request;
	private PersonResponse response;
	

    @Override
    public APIResponse handleRequest(APIRequest request, Context context) {
        
        this.request = APIRequestIsPersonRequest(request);
		this.response = new PersonResponse();
		setContext(context);
        
		// Validates the request-object here
		if (this.request.getFirstName() == null) {
			throw new InvalidCreatePersonRequestFormatException("firstName");
		}
		else if (this.request.getLastName() == null) { 
			throw new InvalidCreatePersonRequestFormatException("lastName");
		}
		
		try {
			// super.handleRequest() adds the newly created ID to the response-object
			super.handleRequest(this.request, this.response, APIHandler.PERSONTABLE);
			response.setPersonIsAdded(true);
		}
		catch (SQLException e) {
			throw new DatabaseErrorException("Could not add the person to the database", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		return this.response;
    }
	
}
