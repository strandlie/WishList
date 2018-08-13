package com.strandlie.lambda.person;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.common.APIHandler;
import com.strandlie.lambda.common.UpdateAPIHandler;

public class UpdatePersonAPIHandler extends UpdateAPIHandler implements RequestHandler<PersonRequest, PersonResponse> {
	
	private PersonRequest request;
	private PersonResponse response;
	

    @Override
    public PersonResponse handleRequest(PersonRequest request, Context context) {
        
        this.request = APIRequestIsPersonRequest(request);
    	this.response = new PersonResponse();
    	setContext(context);
    	
		super.handleRequest(this.request, this.response, APIHandler.PERSONTABLE);
		response.setPersonIsUpdated(true);
		
    	return this.response;
    }
}
