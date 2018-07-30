package common;

public abstract class APIResponse {
	
	private int personID;
	private String errorMessage;
	
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
