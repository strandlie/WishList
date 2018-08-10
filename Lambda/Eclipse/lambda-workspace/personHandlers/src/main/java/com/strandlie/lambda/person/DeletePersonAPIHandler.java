package com.strandlie.lambda.person;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.DeleteAPIHandler;
import exceptions.DatabaseErrorException;

public class DeletePersonAPIHandler extends DeleteAPIHandler {
	
	private PersonRequest request;
	private PersonResponse response;

    @Override
    public APIResponse handleRequest(APIRequest request, Context context) {
        
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
