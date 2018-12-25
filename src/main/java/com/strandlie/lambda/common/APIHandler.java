package com.strandlie.lambda.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.amazonaws.services.lambda.runtime.Context;
import com.strandlie.lambda.gift.GiftRequest;
import com.strandlie.lambda.giftgroup.GiftGroupRequest;
import com.strandlie.lambda.item.ItemRequest;
import com.strandlie.lambda.person.PersonRequest;
import com.strandlie.lambda.wish.WishRequest;

public abstract class APIHandler {
	
	protected static final String PERSONTABLE = "person";
	protected static final String ITEMTABLE = "item";
	protected static final String GIFTTABLE = "gift";
	protected static final String WISHTABLE = "wish";
	protected static final String GIFTGROUPTABLE = "giftGroup";
	protected static final String PERSONINGIFTGROUPTABLE = "personInGiftGroup";
	
	protected static final String IDCOLUMN = "id";
	protected static final String PERSONIDCOLUMN = "personID";
	protected static final String GROUPIDCOLUMN = "groupID";
	
	protected Connection connection;
	protected ResultSet resultSet;
	protected PreparedStatement statement;
	
	private Context context;
	
	
	protected void setContext(Context context) {
		this.context = context;
	}
	
	protected Context getContext() {
		return this.context;
	}
	
	protected void setStringOrNull(int parameterIndex, String string) throws SQLException {
		if (string == null) {
			statement.setNull(parameterIndex, Types.VARCHAR);
		}
		else {
			statement.setString(parameterIndex, string);
		}
	}
	
	protected void setDoubleOrNull(int parameterIndex, Double number) throws SQLException {
		if (number == null) {
			statement.setNull(parameterIndex, Types.DOUBLE);
		}
		else {
			statement.setDouble(parameterIndex, number);
		}
	}
	
	protected void setIntOrNull(int parameterIndex, Integer value) throws SQLException {
		if (value == null) {
			statement.setNull(parameterIndex, Types.INTEGER);
		}
		else {
			statement.setInt(parameterIndex, value);
		}
	}
	
    
	
	protected void getConnection() throws SQLException {
		if (connection == null) {
			createConnection();
		}
		else if (connection.isClosed()) {
			createConnection();
		}
	}
	
	private void createConnection() throws SQLException {
		connection = DriverManager.getConnection(
						"jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), 
						System.getenv("DBUsername"), 
						System.getenv("DBPassword"));
		
		connection.setCatalog(System.getenv("DBDatabase"));
		
	}
	
	
	/**
	 * Tries to close the resources connected to the DB-connection. Doesn't do anything if it can't close.
	 */
	protected void closeDatabaseConnection() {
			try { 
				if (!(resultSet == null)) {
					resultSet.close(); 
				}
			} catch (SQLException e) {}
			
			try { 
				if (!(statement == null)) {
					statement.close(); 
				}
			} catch (SQLException e) {}
			
			try { 
				if (!(connection == null)) {
					connection.close(); 
				}
			} catch (SQLException e) {}
	}
	
	protected static GiftRequest APIRequestIsGiftRequest(APIRequest request) {
		try {
			GiftRequest r = (GiftRequest) request;
			return r;
		}
		catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" an GiftRequest.");
		}
	}
	
	protected static ItemRequest APIRequestIsItemRequest(APIRequest request) {
		try {
			ItemRequest r = (ItemRequest) request;
			return r;
		}
		catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" an ItemRequest.");
		}
	}
	
	protected static PersonRequest APIRequestIsPersonRequest(APIRequest request) {
		try {
			PersonRequest r = (PersonRequest) request;
			return r;
		}
		catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" a PersonRequest.");
		}
	}
	
	protected static WishRequest APIRequestIsWishRequest(APIRequest request) {
		try {
			WishRequest r = (WishRequest) request;
			return r;
		} catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" a WishRequest.");
		}
	}
	
	protected static GiftGroupRequest APIRequestIsGiftGroupRequest(APIRequest request) {
		try {
			GiftGroupRequest r = (GiftGroupRequest) request;
			return r;
		} catch (ClassCastException e) {
			throw new RuntimeException("API Request: " + request.toString() + " is not" + 
			" a WishRequest.");
		}
	}
}