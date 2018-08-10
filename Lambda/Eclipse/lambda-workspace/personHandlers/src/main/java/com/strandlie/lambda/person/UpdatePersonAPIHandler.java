package com.strandlie.lambda.person;


import com.amazonaws.services.lambda.runtime.Context;

import common.APIHandler;
import common.APIRequest;
import common.APIResponse;
import common.UpdateAPIHandler;

public class UpdatePersonAPIHandler extends UpdateAPIHandler {
	
	private PersonRequest request;
	private PersonResponse response;
	

    @Override
    public APIResponse handleRequest(APIRequest request, Context context) {
        
        this.request = APIRequestIsPersonRequest(request);
    	this.response = new PersonResponse();
    	setContext(context);
    	
		super.handleRequest(this.request, this.response, APIHandler.PERSONTABLE);
		response.setPersonIsUpdated(true);
		
    	return this.response;
    }
}
