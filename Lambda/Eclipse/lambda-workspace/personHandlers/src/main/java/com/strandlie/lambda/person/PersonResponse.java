package com.strandlie.lambda.person;

import common.APIResponse;

public class PersonResponse extends APIResponse {
	
	private boolean personIsAdded;
	private boolean personIsUpdated;
	private boolean personIsDeleted;
	
	
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
	
	public boolean getPersonIsDeleted() {
		return personIsDeleted;
	}

	public void setPersonIsDeleted(boolean personIsDeleted) {
		this.personIsDeleted = personIsDeleted;
	}

}
