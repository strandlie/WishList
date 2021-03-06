package com.strandlie.lambda.common;

import java.util.HashMap;
import java.util.Map;


public class APIResponse {
	
	private int id;
	private String errorMessage;
	
	private Map<String, String> fields;
	
	protected Map<String, String> fields() {
		if (this.fields == null) {
			this.fields = new HashMap<>();
		}
		return this.fields;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	protected Integer getIntFromMap(String key) {
		String temp_string = this.fields().getOrDefault(key, null);
		if (temp_string == null) {
			return null;
		}
		return Integer.parseInt(temp_string);
		
	}
	
}
