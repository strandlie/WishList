package com.strandlie.lambda.giftgroup;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.UpdateAPIHandler;

public class UpdateGiftGroupAPIHandler extends UpdateAPIHandler {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

	@Override
	public APIResponse handleRequest(APIRequest request, Context context) {
		
		this.request = APIRequestIsGiftGroupRequest(request);
		this.response = new GiftGroupResponse();
		setContext(context);
		
		super.handleRequest(this.request, this.response, APIHandler.GIFTGROUPTABLE);
		this.response.setGiftGroupIsUpdated(true);
		
		return this.response;
	}


}
