package com.strandlie.lambda.item;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.GetAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;

public class GetItemAPIHandler extends GetAPIHandler implements RequestHandler<ItemRequest, ItemResponse> {
	
	private ItemRequest request;
	private ItemResponse response;

	@Override
	public ItemResponse handleRequest(ItemRequest request, Context context) {
		
		this.request = APIRequestIsItemRequest(request);
		this.response = new ItemResponse();
		setContext(context);
		
		
		try {
			super.handleRequest(this.request, this.response, APIHandler.ITEMTABLE);
			if (! resultSet.next()) {
				this.response.setId(this.request.getId());
				this.response.setErrorMessage("No such object");
			}
			else {
				response.setTitle(resultSet.getString("title"));
				response.setDescription(resultSet.getString("description"));
				response.setWebsiteURL(resultSet.getString("websiteURL"));
				response.setPictureURL(resultSet.getString("pictureURL"));
				response.setPrice(resultSet.getDouble("price"));
			}
		} catch (SQLException e) {
			throw new DatabaseErrorException("Could not retrieve item with id: " + this.request.getId().toString(), e.toString());
			
		} finally {
			closeDatabaseConnection();
		}
		
		logEnd();
		return this.response;
	}
}
