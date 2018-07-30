package com.strandlie.lambda.person;

import java.sql.PreparedStatement;
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
    		throw new InvalidUpdateRequestFormatException("Invalid ID for update: " + request.getId().toString());
    	}
    	Map<String, String> notNullFieldNames = this.request.getNotNullFields();
    	
    	try {
    		getConnection();
			connection.setAutoCommit(false);
			
			for (String fieldName : notNullFieldNames.keySet()) {
				// Takes the returned PreparedStatement from the updateInDatabase method and adds to the list
				String newValue = notNullFieldNames.get(fieldName);
				switch (fieldName) {
					case "id":
						break;
					case "firstName":
						updateFirstNameInDatabase(id, newValue);
						break;
					case "lastName":
						updateLastNameInDatabase(id, newValue);
						break;
					case "email":
						updateEmailInDatabase(id, newValue);
						break;
					case "phoneNr":
						updatePhoneNrInDatabase(id, newValue);
						break;
					case "pictureURL":
						updatePictureURLInDatabase(id, newValue);
						break;
					default:
						throw new InvalidUpdateRequestFormatException("No updateable column named: " + fieldName);
				}
			}
			connection.commit();
			connection.close();
			response.setPersonIsUpdated(true);
			response.setId(id);
    	}
    	catch (SQLException e) {
    		response.setErrorMessage(e.toString());
    		throw new DatabaseErrorException("Could not create connection and update.", e.toString());
    		
    	}
    	context.getLogger().log("\nUpdate successfully processed\n");
    	return response;
    }
    
    private void updateFirstNameInDatabase(int id, String newValue) throws SQLException {
    	String sql = "UPDATE person SET firstName = ? WHERE id = ?";
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	setStringOrNull(1, newValue, statement);
    	setInt(2, id, statement);
    	statement.executeUpdate();
    	
    }
    
    private void updateLastNameInDatabase(int id, String newValue) throws SQLException {
    	String sql = "UPDATE person SET lastName = ? WHERE id = ?";
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	setStringOrNull(1, newValue, statement);
    	setInt(2, id, statement);
    	statement.executeUpdate();    	
    }
    
    private void updateEmailInDatabase(int id, String newValue) throws SQLException {
    	String sql = "UPDATE person SET email = ? WHERE id = ?";
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	setStringOrNull(1, newValue, statement);
    	setInt(2, id, statement);
    	statement.executeUpdate();
    }
    
    private void updatePhoneNrInDatabase(int id, String newValue) throws SQLException {
    	String sql = "UPDATE person SET phoneNr = ? WHERE id = ?";
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	setStringOrNull(1, newValue, statement);
    	setInt(2, id, statement);
    	statement.executeUpdate();    	
    }
    
    private void updatePictureURLInDatabase(int id, String newValue) throws SQLException {
    	String sql = "UPDATE person SET pictureURL = ? WHERE id = ?";
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	setStringOrNull(1, newValue, statement);
    	setInt(2, id, statement);
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
