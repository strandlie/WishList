package com.strandlie.lambda.giftgroup;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.UpdateAPIHandler;

public class UpdateGiftGroupAPIHandler extends UpdateAPIHandler implements RequestHandler<GiftGroupRequest, GiftGroupResponse> {
	
	private GiftGroupRequest request;
	private GiftGroupResponse response;

	@Override
	public GiftGroupResponse handleRequest(GiftGroupRequest request, Context context) {
		
		this.request = APIRequestIsGiftGroupRequest(request);
		this.response = new GiftGroupResponse();
		setContext(context);
		
		super.handleRequest(this.request, this.response, APIHandler.GIFTGROUPTABLE);
		this.response.setGiftGroupIsUpdated(true);
		
		return this.response;
	}


}
