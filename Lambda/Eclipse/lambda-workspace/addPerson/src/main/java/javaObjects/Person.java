package javaObjects;

import java.net.MalformedURLException;
import java.net.URL;

public class Person {
	
	/**
	 * The id for the person from the DB
	 */
	private int id;
	

	/**
	 * The first name of the user
	 */
	private String firstName;
	
	/**
	 * The last name of the user
	 */
	private String lastName;
	
	/**
	 * The email adress of the user
	 */
	private String email;
	
	/**
	 * The phonenumber of the user
	 */
	private String phoneNr;
	
	/**
	 * The URL to the picture in the pictures S3-bucket
	 */
	private URL pictureURL;
	
	public Person(int id, String firstName, String lastName, String email, String phoneNr, String pictureUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNr = phoneNr;
		this.pictureURL = createURLFromString(pictureUrl);
	}
	
	
	public int getId() {
		if (this.id == -1) {
			throw new IllegalStateException("Cannot get ID of person without valid ID");
		}
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return firstName;
	}

	public void setName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoneNr() {
		return phoneNr;
	}


	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}


	public URL getPictureURL() {
		return pictureURL;
	}
	
	private URL createURLFromString(String string) {
		if (string == null) {
			return null;
		}
		try {
			return new URL(string);
		}
		catch (MalformedURLException e) {
			return null;
		}
	}

	public void setPicture(String pictureURL) {
		this.pictureURL = createURLFromString(pictureURL);
	}
}
