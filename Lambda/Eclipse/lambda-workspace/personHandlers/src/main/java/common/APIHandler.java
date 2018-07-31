package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.amazonaws.services.lambda.runtime.RequestHandler;

public abstract class APIHandler implements RequestHandler<APIRequest, APIResponse> {
	
	public static final String PERSONTABLE = "person";
	public static final String ITEMTABLE = "item";
	public static final String IDCOLUMN = "id";
	
	protected Connection connection;
	protected ResultSet resultSet;
	protected PreparedStatement statement;
	
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
	
	protected void setInt(int parameterIndex, int value) throws SQLException {
		statement.setInt(parameterIndex, value);
	}
	
    protected void updateInDatabase(String tableName, String columnNameForUpdate, String newValue, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareUpdateStatement(tableName, columnNameForUpdate, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setStringOrNull(1, newValue);
    	setInt(2, id);
    	statement.executeUpdate();
    }
    
    protected void updateInDatabase(String tableName, String columnNameForUpdate, int newValue, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareUpdateStatement(tableName, columnNameForUpdate, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setInt(1, newValue);
    	setInt(2, id);
    	statement.executeUpdate();
    }
    
    protected void updateInDatabase(String tableName, String columnNameForUpdate, Double newValue, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareUpdateStatement(tableName, columnNameForUpdate, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setDoubleOrNull(1, newValue);
    	setInt(2, id);
    	statement.executeUpdate();
    }
    
    protected void deleteFromDatabase(String tableName, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareDeleteStatement(tableName, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setInt(1, id);
    	statement.executeUpdate();
    }
	
	protected void getConnection() throws SQLException {
		connection = DriverManager.getConnection(
						"jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), 
						System.getenv("DBUsername"), 
						System.getenv("DBPassword"));
		
		connection.setCatalog(System.getenv("DBDatabase"));
	}
	
	protected String prePrepareUpdateStatement(String tableName, String columnNameForUpdate, String columnNameForSelection) {
		StringBuilder string = new StringBuilder();
		string.append("UPDATE " + tableName + " ");
		string.append("SET " + columnNameForUpdate + " = ? ");
		string.append("WHERE " + columnNameForSelection + " = ?");
		return string.toString();
	}
	
	private String prePrepareDeleteStatement(String tableName, String columnNameForSelection) {
		StringBuilder string = new StringBuilder();
		string.append("DELETE FROM " + tableName + " ");
		string.append("WHERE " + columnNameForSelection + " = ?");
		return string.toString();
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

}
