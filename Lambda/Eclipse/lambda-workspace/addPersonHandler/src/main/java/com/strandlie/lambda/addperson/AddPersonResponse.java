package com.strandlie.lambda.addperson;

public class AddPersonResponse {
	private boolean personisAdded;
	private int personID;
	private String errorMessage;
	
	public AddPersonResponse(boolean personIsAdded) {
		this.personisAdded = personIsAdded;
	}
	
	public boolean getPersonIsAdded() {
		return personisAdded;
	}
	
	public void setPersonIsAdded(boolean personIsAdded) {
		this.personisAdded = personIsAdded;
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
