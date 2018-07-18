package com.strandlie.lambda.addperson;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPersonAPIHandler implements RequestHandler<AddPersonRequest, AddPersonResponse> {
	
	private PreparedStatement statement;

    @Override
    public AddPersonResponse handleRequest(AddPersonRequest request, Context context) {
        context.getLogger().log("Received request: " + request.toString());

		AddPersonResponse response = new AddPersonResponse(false);
		
		String firstName = request.getFirstName();
		String lastName = request.getLastName();
		String email = request.getEmail();
		String phoneNr = request.getPhoneNr();
		String pictureURL = request.getPictureURL();
		
		try {
			int id = createInDatabase(firstName, lastName, email, phoneNr, pictureURL);
			response.setId(id);
			response.setPersonIsAdded(true);
			}
		catch (SQLException e) {
			response.setErrorMessage(e.toString());
			context.getLogger().log("Database error: " + e.toString());
			// Using RuntimeException to be able to return the error through the API
			throw new RuntimeException("Database error. Could not add the person to the database. Error message: \n" + e.toString());
		}
		context.getLogger().log("Request successfully processed");
		
		return response;
    }
    
	
	private int createInDatabase(String firstName, String lastName, String email, String phoneNr, String pictureURL) throws SQLException  {
		
		String sql = "INSERT INTO person (firstName, lastName, email, phoneNr, pictureURL)" +
					  "VALUES(?, ?, ?, ?, ?)";
		Connection connection = DriverManager.getConnection("jdbc:mysql://" + 
					  			System.getenv("DBUsername") + ":" + 
					  			System.getenv("DBPassword") + 
					  			"@" + System.getenv("DBPath"));
		connection.setCatalog(System.getenv("DBDatabase"));

		statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		statement.setString(1, firstName);
		statement.setString(2, lastName);
		statement.setString(3, email);
		statement.setString(4, phoneNr);
		statement.setString(5, pictureURL);
		statement.executeUpdate();
		
		// Gets the key generated by the DB for the object inserted
		ResultSet generatedKeys =  statement.getGeneratedKeys();
		
		if (generatedKeys.next()) {
			int id = generatedKeys.getInt(1);
			connection.close();
			return id;
		}
		connection.close();
		return -1;
	}

}
