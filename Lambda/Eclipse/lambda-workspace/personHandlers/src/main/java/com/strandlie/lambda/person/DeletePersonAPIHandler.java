package com.strandlie.lambda.person;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

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
        	deleteFromDatabase(id);
        	response.setPersonIsDeleted(true);
        } catch (SQLException e) {
        	throw new DatabaseErrorException("Could not create connection and delete", e.toString());
        }
        context.getLogger().log("\nDelete successfully processed");
        return response;
    }
    
    private void deleteFromDatabase(int id) throws SQLException {
    	String sql = "DELETE FROM person WHERE id = ?";
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	setInt(1, id, statement);
    	statement.executeUpdate();
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
