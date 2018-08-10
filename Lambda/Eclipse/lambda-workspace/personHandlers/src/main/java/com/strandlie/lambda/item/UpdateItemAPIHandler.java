package com.strandlie.lambda.item;


import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.UpdateAPIHandler;

public class UpdateItemAPIHandler extends UpdateAPIHandler {
	
	private ItemRequest request;
	private ItemResponse response;

    @Override
    public APIResponse handleRequest(APIRequest request, Context context) {
        
        this.request = APIRequestIsItemRequest(request);
        this.response = new ItemResponse();
        setContext(context);
        
        super.handleRequest(this.request, this.response, APIHandler.ITEMTABLE);
        response.setItemIsUpdated(true);
        
        return response;
    }
}
