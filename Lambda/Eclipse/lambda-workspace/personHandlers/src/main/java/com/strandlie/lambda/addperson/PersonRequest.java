package com.strandlie.lambda.addperson;

import java.util.HashMap;
import java.util.Map;

import exceptions.InvalidCreateRequestStateException;

public class PersonRequest extends Object {
	
	
	
	/**
	 * Contains all the fields that not null, together with their values
	 */
	private Map<String, String> fields;
	
	
	
	@Override
	public String toString() {
		return "firstName: " + this.getFirstName() +
				"\nlastName: " + this.getLastName() +
				"\nemail: " + this.getEmail() + 
				"\nphoneNr: " + this.getPhoneNr() + 
				"\npictureURL: " + this.getPictureURL();
		
	}
	
	private Map<String, String> fields() {
		if (this.fields == null) {
			this.fields = new HashMap<>();
		}
		return this.fields;
	}
	
	public int getId() {
		String temp_id = this.fields().getOrDefault("id", null);
		if (temp_id == null) {
			throw new InvalidCreateRequestStateException("ID");
		}
		return Integer.parseInt(temp_id);
		
	}

	public void setId(int id) {
		this.fields().put("id", Integer.toString(id));
	}

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
	
	public Map<String, String> getNotNullFieldNames() {
		return fields;
	}
}
