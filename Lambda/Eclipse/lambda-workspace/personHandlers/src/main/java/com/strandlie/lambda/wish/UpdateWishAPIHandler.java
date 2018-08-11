package com.strandlie.lambda.wish;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.UpdateAPIHandler;

public class UpdateWishAPIHandler extends UpdateAPIHandler {
	
	private WishRequest request;
	private WishResponse response;

	/**
	 * This method is assuming that by the time the request reaches this point
	 * the IDs are already validated client-side
	 */
	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
		this.request = APIRequestIsWishRequest(request);
		this.response = new WishResponse();
		setContext(context);
		
		super.handleRequest(this.request, this.response, APIHandler.WISHTABLE);
		this.response.setWishIsUpdated(true);
		
		return this.response;
	}


}
