package com.strandlie.lambda.person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.GetAPIHandler;
import com.strandlie.lambda.exceptions.InvalidRetrieveRequestFormatException;

public class GetGiftGroupsForPersonAPIHandler extends GetAPIHandler implements RequestHandler<PersonRequest, PersonResponse> {
	
	private PersonRequest request;
	private PersonResponse response;
	
	@Override
	public PersonResponse handleRequest(PersonRequest request, Context context) {
		
		setContext(context);
		getContext().getLogger().log("Recieved retrieve request for GiftGroups for person with id: " + this.request.getId());
		
		this.request = request;
		this.response = new PersonResponse();
		
		Integer id = this.request.getId();
		if (id == null) {
			throw new InvalidRetrieveRequestFormatException(request.toString());
		}
		
		try {
			getConnection();
			String sql = GetAPIHandler.prePrepareRetrieveStatement(APIHandler.PERSONINGIFTGROUPTABLE, APIHandler.PERSONIDCOLUMN);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			List<Integer> giftGroupsForPerson = new ArrayList<>();
			
			while (resultSet.next()) {
				giftGroupsForPerson.add(resultSet.getInt(APIHandler.GROUPIDCOLUMN));
			}
			
			this.response.setId(id);
			this.response.setGiftGroupsForPerson(giftGroupsForPerson);
			
		} catch (SQLException e) {
			
		} finally {
			closeDatabaseConnection();
		}
		
		getContext().getLogger().log("\nRetrieve request successfully processed\n");
		getContext().getLogger().log("\n################\n");
		
		return this.response;
	}

}
