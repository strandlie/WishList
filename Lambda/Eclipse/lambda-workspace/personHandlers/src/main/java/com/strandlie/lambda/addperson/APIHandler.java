package com.strandlie.lambda.addperson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.amazonaws.services.lambda.runtime.RequestHandler;

public abstract class APIHandler implements RequestHandler<PersonRequest, PersonResponse> {
	
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

}
