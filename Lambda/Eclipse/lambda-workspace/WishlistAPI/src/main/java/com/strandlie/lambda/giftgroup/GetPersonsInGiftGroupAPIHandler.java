package com.strandlie.lambda.giftgroup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.GetAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;
import com.strandlie.lambda.exceptions.InvalidRetrieveRequestFormatException;

public class GetPersonsInGiftGroupAPIHandler extends APIHandler implements RequestHandler<GiftGroupRequest, GiftGroupResponse> {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

    @Override
    public GiftGroupResponse handleRequest(GiftGroupRequest request, Context context) {
    	
    	setContext(context);
    	getContext().getLogger().log("Received retrieve request for persons in group with id: " + request.getId());
    	
    	this.request = APIRequestIsGiftGroupRequest(request);
    	this.response = new GiftGroupResponse();
    	
    	Integer id = this.request.getId();
    	if (id == null) {
    		throw new InvalidRetrieveRequestFormatException(request.toString());
    	}
    	
    	try {
			getConnection();
			String sql = GetAPIHandler.prePrepareRetrieveStatement(APIHandler.PERSONINGIFTGROUPTABLE, APIHandler.GROUPIDCOLUMN);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			List<Integer> personsInGiftGroup = new ArrayList<>();
			
			while (resultSet.next()) {
				personsInGiftGroup.add(resultSet.getInt(APIHandler.PERSONIDCOLUMN));
			}
			
			this.response.setId(id);
			this.response.setPersonsInGiftGroup(personsInGiftGroup);
    	} catch (SQLException e) {
    		throw new DatabaseErrorException("Could not retrieve persons in giftGroup with id: " + this.request.getId(), e.toString());
    	} finally {
    		closeDatabaseConnection();
    	}
    	
		getContext().getLogger().log("\nRetrieve request successfully processed\n");
		getContext().getLogger().log("\n################\n");
		
		return this.response;
    	
    }
}
