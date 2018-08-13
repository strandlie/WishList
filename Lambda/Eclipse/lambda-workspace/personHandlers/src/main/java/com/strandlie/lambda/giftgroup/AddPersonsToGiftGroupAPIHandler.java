package com.strandlie.lambda.giftgroup;

import java.sql.SQLException;
import java.util.Arrays;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.AddAPIHandler;
import exceptions.DatabaseErrorException;
import exceptions.InvalidRetrieveRequestFormatException;

public class AddPersonsToGiftGroupAPIHandler extends APIHandler {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		// Assumes the client doesn't try to add a person to a group
		// the person is already a memeber of
		
		setContext(context);
		getContext().getLogger().log("Received request to add following persons to " +
									"gift group:" + "\n" + 
									request.toString());
		
		this.request = APIRequestIsGiftGroupRequest(request);
		this.response = new GiftGroupResponse();
		
		Integer groupID = this.request.getId();
		if (groupID == null) {
    		throw new InvalidRetrieveRequestFormatException(request.toString());
		}
		
		try {
			getConnection();
			connection.setAutoCommit(false);
			for (Integer personID : this.request.getPersonsInGiftGroup()) {
				String sql = AddAPIHandler.prePrepareCreateStatement(
						APIHandler.PERSONINGIFTGROUPTABLE, 
						Arrays.asList(APIHandler.PERSONIDCOLUMN, APIHandler.GROUPIDCOLUMN)
						 );
				statement = connection.prepareStatement(sql);
				setIntOrNull(1, personID);
				setIntOrNull(2, groupID);
				statement.executeUpdate();
			}
			
			connection.commit();
			this.response.setPersonsAreAddedToGiftGroup(true);
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException re) {
				throw new DatabaseErrorException("Could not create connection and add persons to gift group, and could also not rollback transaction after aborting", e.toString() + "\n#######\n" + re.toString());
			}
			
			throw new DatabaseErrorException("Could not create connection and add persons to gift group.", e.toString());
		} finally {
			closeDatabaseConnection();
		}
		
		getContext().getLogger().log("\nRetrieve request successfully processed\n");
		getContext().getLogger().log("\n################\n");
		
		return this.response;
	}
}
