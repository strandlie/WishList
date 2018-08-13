package com.strandlie.lambda.person;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.DeleteAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class DeletePersonAPIHandler extends DeleteAPIHandler implements RequestHandler<PersonRequest, PersonResponse> {
	
	private PersonRequest request;
	private PersonResponse response;

    @Override
    public PersonResponse handleRequest(PersonRequest request, Context context) {
        
        this.request = APIRequestIsPersonRequest(request);
        this.response = new PersonResponse();
        setContext(context);
        
        try {
        	super.handleRequest(this.request, this.response, APIHandler.PERSONTABLE, APIHandler.IDCOLUMN);
        	response.setPersonIsDeleted(true);
        } catch (SQLException e) {
        	throw new DatabaseErrorException("Could not create connection and delete", e.toString());
        } finally {
        	closeDatabaseConnection();
        }
        return this.response;
    }
}
