package com.strandlie.lambda.gift;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.UpdateAPIHandler;

public class UpdateGiftAPIHandler extends UpdateAPIHandler {
	
	private GiftRequest request;
	private GiftResponse response;

	
	/**
	 * This method is assuming that by the time the request reaches this point
	 * the IDs are already validated client-side
	 */
	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
		this.request = APIRequestIsGiftRequest(request);
		this.response = new GiftResponse();
		setContext(context);
		
		super.handleRequest(this.request, this.response, APIHandler.GIFTTABLE);
		response.setGiftIsUpdated(true);
		
		return this.response;
	}


}
