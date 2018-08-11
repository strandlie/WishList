package common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class AddAPIHandler extends APIHandler {

	protected void handleRequest(APIRequest request, APIResponse response, String tableName) throws SQLException {
		getContext().getLogger().log("\nReceived create request with values: \n" + request.toString());
		
		getConnection();
		response.setId(createInDatabase(tableName, request.getNotNullFields()));

		getContext().getLogger().log("\nCreate request successfully processed\n");
	}
	
	private String prePrepareCreateStatement(String tableName, List<String> columns) {
		StringBuilder firstString = new StringBuilder();
		StringBuilder lastString = new StringBuilder();
		
		firstString.append("INSERT INTO " + tableName + "(");
		lastString.append(") VALUES(");
		
		for (int i = 0; i < columns.size(); i++) {
			if (i == columns.size() - 1) {
				firstString.append(columns.get(i));
				lastString.append("?");
			} else {
				firstString.append(columns.get(i) + ',');
				lastString.append("?, ");
			}
		}
		lastString.append(")");
		
		firstString.append(lastString);
		return firstString.toString();
		
	}
	
	private int createInDatabase(String tableName, Map<String, String> fields) throws SQLException {
		// The ID-field is not relevant, and is removed from the Map
		if (fields.containsKey("id")) {
			fields.remove("id");
		}
		
		List<String> columns = new ArrayList<>(fields.keySet());
		String sql = prePrepareCreateStatement(tableName, columns);
		
		statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		for (int i = 0; i < columns.size(); i++) {
			String fieldName = columns.get(i);
			
			switch(fieldName) {
				case "id":
					// Don't do anything to ID-fields
					break;
				case "giverID":
				case "recepientID":
				case "wisherID":
				case "itemID":
				case "quantity":
					setIntOrNull(i+1, Integer.parseInt(fields.get(fieldName)));
					break;
				case "price":
					setDoubleOrNull(i+1, Double.parseDouble(fields.get(fieldName)));
					break;
				case "title":
				case "description":
				case "pictureURL":
				case "websiteURL":
				case "firstName":
				case "lastName":
				case "email":
				case "phoneNr":
					setStringOrNull(i+1, fields.get(fieldName));
					break;
				default:
					throw new RuntimeException(fieldName + " is not a valid fieldName");
			}
		}
		statement.executeUpdate();
		
		resultSet = statement.getGeneratedKeys();
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return 0;
	}

}
