package com.strandlie.lambda.person;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.GetAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class GetPersonAPIHandler extends GetAPIHandler implements RequestHandler<PersonRequest, PersonResponse> {
	
	private PersonRequest request;
	private PersonResponse response;

	@Override
	public PersonResponse handleRequest(PersonRequest request, Context context) {
		
		this.request = request;
		this.response = new PersonResponse();
		setContext(context);
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.PERSONTABLE);
			if (! resultSet.next()) {
				this.response.setId(this.request.getId());
				this.response.setErrorMessage("No such object");
			}
			else {
				this.response.setFirstName(resultSet.getString("firstName"));
				this.response.setLastName(resultSet.getString("lastName"));
				this.response.setEmail(resultSet.getString("email"));
				this.response.setPhoneNr(resultSet.getString("phoneNr"));
				this.response.setPictureURL(resultSet.getString("pictureURL"));
			}
			
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not retrieve person with id: " + this.request.getId().toString(), e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		logEnd();
		return this.response;
	}
}
