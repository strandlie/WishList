package com.strandlie.lambda.addperson;

public class AddPersonRequest {
	
	/**
	 * The first name of the person specified in the API call JSON
	 */
	private String firstName;
	
	/**
	 * The last name of the person specified in the API call JSON
	 */
	private String lastName;
	
	/**
	 * The email of the person specified in the API call JSON
	 */
	private String email;
	
	/**
	 * The phonenumber of the person specified in the API call JSON
	 */
	private String phoneNr;
	
	/**
	 * The URL to the picture contained in an S3 bucket, of the person specified in the API call JSON
	 */
	private String pictureURL;
	
	/*public AddPersonRequest(String firstName, String lastName, String email, String phoneNr, String pictureURL) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNr = phoneNr;
		this.pictureURL = pictureURL;
	}*/
	
	@Override
	public String toString() {
		return "firstName: " + this.getFirstName() +
				"\nlastName: " + this.getLastName() +
				"\nemail: " + this.getEmail() + 
				"\nphoneNr: " + this.getPhoneNr() + 
				"\npictureURL: " + this.getPictureURL();
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return nullProcedure(email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNr() {
		return nullProcedure(phoneNr);
	}

	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}

	public String getPictureURL() {
		return nullProcedure(pictureURL);
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	
	/**
	 * The common method for handling NULL values in the input
	 * @param field The relevant field to be handles
	 * @return the value, or "NULL" if the value is null
	 */
	private String nullProcedure(String field) {
		if (field == null) {
			return "NULL";
		}
		return field;
		
	}
	
}
