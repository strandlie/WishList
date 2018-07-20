package com.strandlie.lambda.addperson;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdatePersonAPIHandler implements RequestHandler<PersonRequest, PersonResponse> {

    @Override
    public PersonResponse handleRequest(PersonRequest request, Context context) {
        //context.getLogger().log("Input: " + input);
        
    	PersonResponse response = new PersonResponse(false);
    	Map<String, String> newFields = new HashMap<String, String>();
    	
    	
    	return response;
    }

}
