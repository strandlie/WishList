package com.strandlie.lambda.person;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import exceptions.DatabaseErrorException;

public class GetPersonAPIHandler extends APIHandler {
	
	private PersonRequest request;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		context.getLogger().log("Received retreive request: \n" + request.toString());
		
		this.request = APIRequestIsPersonRequest(request);
		PersonResponse response = new PersonResponse();
		
		Integer id = this.request.getId();
		
		try {
			getConnection();
			String sql = "SELECT * FROM " + APIHandler.PERSONTABLE + " WHERE id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if (! resultSet.next()) {
				connection.close();
			}
			else {
				response.setFirstName(resultSet.getString("firstName"));
				response.setLastName(resultSet.getString("lastName"));
				response.setEmail(resultSet.getString("email"));
				response.setPhoneNr(resultSet.getString("phoneNr"));
				response.setPictureURL(resultSet.getString("pictureURL"));
			}
			
		} catch (SQLException e) {
			context.getLogger().log("Database error: " + e.toString());
			throw new DatabaseErrorException("Could not retrieve person with id: " + Integer.toString(id), e.toString());
		} finally {
			closeDatabaseConnection();
		}
		context.getLogger().log("\nRetrieve request successfully processed\n");
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
