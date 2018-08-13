package common;

import java.sql.SQLException;

import exceptions.InvalidRetrieveRequestFormatException;

public abstract class GetAPIHandler extends APIHandler {

	public void handleRequest(APIRequest request, APIResponse response, String tableName) throws SQLException {
		getContext().getLogger().log("Received retreive request: \n" + request.toString());
		
		// Since all retrieval-operations is based on ID, validation is done here
		Integer id = request.getId();
		if (id == null) {
			throw new InvalidRetrieveRequestFormatException(request.toString());
		}
		
		getConnection();
		retrieveFromDatabaseUsingID(tableName, APIHandler.IDCOLUMN, id);
		response.setId(id);
	}
	
	protected void logEnd() {
		getContext().getLogger().log("\nRetrieve request successfully processed\n");
		getContext().getLogger().log("\n################\n");
	}
	
	/**
	 * Retrieves the info from the database using the ID-column, 
	 * and stores the result in the superclass' resultSet-field
	 * 
	 * @param tableName The name of the table used
	 * @param columnForSelection the name of the column used for selection (E.G. "id")
	 * @param id The value of the ID used
	 * @throws SQLException 
	 */
	private void retrieveFromDatabaseUsingID(String tableName, String columnForSelection, int id) throws SQLException {
			String sql = prePrepareRetrieveStatement(tableName, columnForSelection);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
	}
	
	public static String prePrepareRetrieveStatement(String tableName, String columnForSelection) {
		String sql = "SELECT * FROM " + tableName + 
					" WHERE " + columnForSelection + 
					" = ?";
		
		return sql;
	}

}
