package com.strandlie.lambda.item;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.UpdateAPIHandler;

public class UpdateItemAPIHandler extends UpdateAPIHandler implements RequestHandler<ItemRequest, ItemResponse> {
	
	private ItemRequest request;
	private ItemResponse response;

    @Override
    public ItemResponse handleRequest(ItemRequest request, Context context) {
        
        this.request = APIRequestIsItemRequest(request);
        this.response = new ItemResponse();
        setContext(context);
        
        super.handleRequest(this.request, this.response, APIHandler.ITEMTABLE);
        response.setItemIsUpdated(true);
        
        return response;
    }
}
