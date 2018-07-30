package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.strandlie.lambda.person.PersonRequest;
import com.strandlie.lambda.person.PersonResponse;

public abstract class APIHandler implements RequestHandler<APIRequest, APIResponse> {
	
	protected Connection connection;
	
	
	protected static void setStringOrNull(int parameterIndex, String string, PreparedStatement statement) throws SQLException {
		if (string == null) {
			statement.setNull(parameterIndex, Types.VARCHAR);
		}
		else {
			statement.setString(parameterIndex, string);
		}
	}
	
	protected static void setInt(int parameterIndex, int value, PreparedStatement statement) throws SQLException {
		statement.setInt(parameterIndex, value);
	}
	
	protected void getConnection() throws SQLException {
		connection = DriverManager.getConnection(
						"jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), 
						System.getenv("DBUsername"), 
						System.getenv("DBPassword"));
		
		connection.setCatalog(System.getenv("DBDatabase"));
	}

}
