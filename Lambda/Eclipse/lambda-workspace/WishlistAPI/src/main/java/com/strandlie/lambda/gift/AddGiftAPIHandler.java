package com.strandlie.lambda.gift;

import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.AddAPIHandler;
import com.strandlie.lambda.exceptions.DatabaseErrorException;
import com.strandlie.lambda.exceptions.InvalidCreateGiftRequestFormatException;

public class AddGiftAPIHandler extends AddAPIHandler implements RequestHandler<GiftRequest, GiftResponse> {
	
	private GiftRequest request;
	private GiftResponse response;

	
	/**
	 * This method is assuming that by the time the request reaches this point
	 * the IDs are already validated client-side
	 */
	@Override
	public GiftResponse handleRequest(GiftRequest request, Context context) {
        
		
		//TODO: Needs to add ability to specify which wish, if any, a gift fulfils
        this.request = APIRequestIsGiftRequest(request);
        this.response = new GiftResponse();
        setContext(context);
        
        // Validate request-object
        if (this.request.getGiverID() == null) {
			throw new InvalidCreateGiftRequestFormatException();
        }
        else if (!(this.request.getItemID() == null) && this.request.getQuantity() == null) {
        	throw new InvalidCreateGiftRequestFormatException("quantity");
        }
        
        try {
        	super.handleRequest(this.request, this.response, APIHandler.GIFTTABLE);
        	response.setGiftIsAdded(true);
        } catch (SQLException e) {
        	throw new DatabaseErrorException("Could not add the gift to the database", e.toString());
        } finally {
        	closeDatabaseConnection();
        }
        return this.response;
	}
}
