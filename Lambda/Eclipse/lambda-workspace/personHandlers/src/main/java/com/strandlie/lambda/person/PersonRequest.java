package com.strandlie.lambda.person;

import java.util.HashMap;
import java.util.Map;

import common.APIRequest;
import exceptions.InvalidCreateRequestStateException;

public class PersonRequest extends APIRequest {
	
	
	public String getFirstName() {
		return this.fields().getOrDefault("firstName", null);
	}
	
	public void setFirstName(String firstName) {
		this.fields().put("firstName", firstName);
	}
	
	public String getLastName() {
		return this.fields().getOrDefault("lastName", null);
	}

	public void setLastName(String lastName) {
		this.fields().put("lastName", lastName);
	}

	public String getEmail() {
		return this.fields().getOrDefault("email", null);
	}

	public void setEmail(String email) {
		this.fields().put("email", email);
	}

	public String getPhoneNr() {
		return this.fields().getOrDefault("phoneNr", null);
	}

	public void setPhoneNr(String phoneNr) {
		this.fields().put("phoneNr", phoneNr);
	}

	public String getPictureURL() {
		return this.fields().getOrDefault("pictureURL", null);
	}
	
	public void setPictureURL(String pictureURL) {
		this.fields().put("pictureURL", pictureURL);
	}

	public Map<String, String> getNotNullFields() {
		return this.fields();
	}
	
}
