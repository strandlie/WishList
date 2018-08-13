package common;

import java.sql.SQLException;

import exceptions.InvalidUpdateRequestFormatException;

public abstract class DeleteAPIHandler extends APIHandler {
	

	protected void handleRequest(APIRequest request, APIResponse response, String tableName, String columnNameForSelection) throws SQLException { 
		getContext().getLogger().log("Received delete request: \n" + request.toString());
		
		
		// Since all delete-requests are for ID the validation is done here
		Integer id = request.getId();
        if (id == null) {
        	throw new InvalidUpdateRequestFormatException("Invalid ID for delete: " + request.getId().toString());
        }
        
		getConnection();
		deleteFromDatabase(tableName, columnNameForSelection, id);
		response.setId(id);
        	
		getContext().getLogger().log("\nDelete successfully processed");
		getContext().getLogger().log("\n################\n");
		
	}
	
	private String prePrepareDeleteStatement(String tableName, String columnNameForSelection) {
		StringBuilder string = new StringBuilder();
		string.append("DELETE FROM " + tableName + " ");
		string.append("WHERE " + columnNameForSelection + " = ?");
		return string.toString();
	}

    protected void deleteFromDatabase(String tableName, String columnNameForSelection, int id) throws SQLException {
    	String sql = prePrepareDeleteStatement(tableName, columnNameForSelection);
    	
    	statement = connection.prepareStatement(sql);
    	setIntOrNull(1, id);
    	statement.executeUpdate();
    }
}
