package com.strandlie.lambda.common;

import java.sql.SQLException;
import java.util.Map;

import com.strandlie.lambda.exceptions.DatabaseErrorException;
import com.strandlie.lambda.exceptions.InvalidUpdateRequestFormatException;

public abstract class UpdateAPIHandler extends APIHandler {

	/**
	 * This implementation differs some from the implementation of the
	 * AddAPIHandler, although it handles similar data. 
	 * 
	 * Here we have several updateInDatabase-methods, that differ on
	 * the type of the newValue-argument, and the different types of
	 * fields are handled in the handleRequest method, before being passed
	 * to the correct updateInDatabase-method.
	 * 
	 * We also have the try-catch statement implemented here, 
	 * because we do not use auto-commit for the connection.
	 * 
	 * @param request The request object with the new values
	 * @param response The response object
	 * @param context The context provided by AWS Lambda
	 * @param tableName The table name for the current table
	 * @throws SQLException In case it is not possible to connect to the datbase
	 */
	protected void handleRequest(APIRequest request, APIResponse response, String tableName) {
		getContext().getLogger().log("\nReceived update request with new values: \n" + request.toString() + "\n");
		 
		// Since all update-requests are for ID the validation is done here
		Integer id = request.getId();
    	if (id == null) {
    		throw new InvalidUpdateRequestFormatException("Invalid ID for update: " + request.getId().toString());
    	}
		try {
			getConnection();
			connection.setAutoCommit(false);
			
			Map<String, String> notNullFields = request.getNotNullFields();
			
			for (String fieldName : notNullFields.keySet()) {
				switch(fieldName) {
					case "id":
						// Never update id
						break;
					case "giverID":
					case "recepientID":
					case "itemID":
					case "quantity":
					case "wisherID":
						updateInDatabase(tableName, fieldName, Integer.parseInt(notNullFields.get(fieldName)), APIHandler.IDCOLUMN, id);
						break;
					case "price":
						updateInDatabase(tableName, fieldName, Double.parseDouble(notNullFields.get(fieldName)), APIHandler.IDCOLUMN, id);
						break;
					case "title":
					case "description":
					case "pictureURL":
					case "websiteURL":
					case "name":
					case "firstName":
					case "lastName":
					case "email":
					case "phoneNr":
						updateInDatabase(tableName, fieldName, notNullFields.get(fieldName), APIHandler.IDCOLUMN, id);
						break;
					default:
						throw new RuntimeException(fieldName + " is not a valid fieldName");
				}
			}
			connection.commit();
			response.setId(id);
			
			getContext().getLogger().log("\nUpdate successfully processed\n");
			getContext().getLogger().log("\n################\n");
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException re) {
				throw new DatabaseErrorException("Could not create connection and update, and could also not rollback transaction after aborting", e.toString() + "\n#######\n" + re.toString());
			}
    		
			throw new DatabaseErrorException("Could not create connection and update.", e.toString());
			
		} finally {
			closeDatabaseConnection();
		}
	}
	 
	private String prePrepareUpdateStatement(String tableName, String columnNameForUpdate, String columnNameForSelection) {
		StringBuilder string = new StringBuilder();
		string.append("UPDATE " + tableName + " ");
		string.append("SET " + columnNameForUpdate + " = ? ");
		string.append("WHERE " + columnNameForSelection + " = ?");
		return string.toString();
	}

    protected void updateInDatabase(String tableName, String columnNameForUpdate, String newValue, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareUpdateStatement(tableName, columnNameForUpdate, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setStringOrNull(1, newValue);
    	setIntOrNull(2, id);
    	statement.executeUpdate();
    }
    
    protected void updateInDatabase(String tableName, String columnNameForUpdate, int newValue, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareUpdateStatement(tableName, columnNameForUpdate, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setIntOrNull(1, newValue);
    	setIntOrNull(2, id);
    	statement.executeUpdate();
    }
    
    protected void updateInDatabase(String tableName, String columnNameForUpdate, Double newValue, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareUpdateStatement(tableName, columnNameForUpdate, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setDoubleOrNull(1, newValue);
    	setIntOrNull(2, id);
    	statement.executeUpdate();
    }
}
