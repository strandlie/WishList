package com.strandlie.lambda.person;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.GetAPIHandler;
import exceptions.DatabaseErrorException;

public class GetPersonAPIHandler extends GetAPIHandler {
	
	private PersonRequest request;
	private PersonResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
		this.request = APIRequestIsPersonRequest(request);
		this.response = new PersonResponse();
		setContext(context);
		
		Integer id = this.request.getId();
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.PERSONTABLE);
			if (! resultSet.next()) {
				this.response.setId(this.request.getId());
				this.response.setErrorMessage("No such object");
			}
			else {
				response.setFirstName(resultSet.getString("firstName"));
				response.setLastName(resultSet.getString("lastName"));
				response.setEmail(resultSet.getString("email"));
				response.setPhoneNr(resultSet.getString("phoneNr"));
				response.setPictureURL(resultSet.getString("pictureURL"));
			}
			
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not retrieve person with id: " + Integer.toString(id), e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		logEnd();
		return response;
	}
}
