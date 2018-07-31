package com.strandlie.lambda.item;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import exceptions.DatabaseErrorException;

public class GetItemAPIHandler extends APIHandler {
	
	private ItemRequest request;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		context.getLogger().log("Received retreive request: \n" + request.toString());
		
		this.request = APIRequestIsItemRequest(request);
		ItemResponse response = new ItemResponse();
		
		Integer id = this.request.getId();
		
		try {
			getConnection();
			String sql = "SELECT * FROM " + APIHandler.ITEMTABLE + " WHERE ID = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if (! resultSet.next()) {
				connection.close();
			}
			else {
				response.setTitle(resultSet.getString("title"));
				response.setDescription(resultSet.getString("description"));
				response.setWebsiteURL(resultSet.getString("websiteURL"));
				response.setPictureURL(resultSet.getString("pictureURL"));
				response.setPrice(resultSet.getDouble("price"));
			}
		} catch (SQLException e) {
			context.getLogger().log("Database error: " + e.toString());
			throw new DatabaseErrorException("Could not retrieve item with id: " + Integer.toString(id), e.toString());
			
		} finally {
			closeDatabaseConnection();
		}
		
		context.getLogger().log("\nRetrieve request successfully processed\n");
		return response;
	}


	private ItemRequest APIRequestIsItemRequest(APIRequest request) {
		try {
			ItemRequest r = (ItemRequest) request;
			return r;
		}
		catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" an ItemRequest.");
		}
	}
}
