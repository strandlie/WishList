package com.strandlie.lambda.addperson;

public class PersonResponse {
	
	private boolean personIsAdded;
	private boolean personIsUpdated;
	private int personID;
	private String errorMessage;
	
	
	public boolean getPersonIsAdded() {
		return personIsAdded;
	}
	
	public void setPersonIsAdded(boolean personIsAdded) {
		this.personIsAdded = personIsAdded;
	}
	
	public boolean getPersonIsUpdated() {
		return personIsUpdated;
	}
	
	public void setPersonIsUpdated(boolean personIsUpdated) {
		this.personIsUpdated = personIsUpdated;
	}
	
	public int getId() {
		return personID;
	}
	public void setId(int id) {
		this.personID = id;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
