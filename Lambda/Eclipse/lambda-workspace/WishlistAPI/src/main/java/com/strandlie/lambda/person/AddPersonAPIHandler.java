package com.strandlie.lambda.person;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.AddAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;
import com.strandlie.lambda.exceptions.InvalidCreatePersonRequestFormatException;

import java.sql.SQLException;


public class AddPersonAPIHandler extends AddAPIHandler implements RequestHandler<PersonRequest, PersonResponse> {
	
	private PersonRequest request;
	private PersonResponse response;
	

    public PersonResponse handleRequest(PersonRequest request, Context context) {
        
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
