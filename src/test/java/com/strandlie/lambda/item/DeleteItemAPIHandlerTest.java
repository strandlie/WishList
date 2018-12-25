package com.strandlie.lambda.item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteItemAPIHandlerTest {

    private static ItemRequest initialItemInput;
    private static ItemRequest deleteItemInput;

    @BeforeClass
    public static void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS gift");
			statement.executeUpdate("DROP TABLE IF EXISTS wish");
			statement.executeUpdate("DROP TABLE IF EXISTS item");
			statement.executeUpdate("CREATE TABLE item(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"title VARCHAR(200)," + 
					"description MEDIUMTEXT," + 
					"pictureURL MEDIUMTEXT," +
					"websiteURL MEDIUMTEXT," +
					"price DOUBLE" +
					");");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @BeforeClass
    public static void createInput() throws IOException {
    	initialItemInput = new ItemRequest();
    	initialItemInput.setTitle("iPhone X");
    	initialItemInput.setDescription("The newest, most powerful phone ever. And we made it smaller and lighter");
    	initialItemInput.setPrice(10990);
    	initialItemInput.setWebsiteURL("www.apple.com/iphone");
    	
    	deleteItemInput = new ItemRequest();
    	deleteItemInput.setId(1);
    }

    private void setupInitialDatabase(Context ctx) {
    	AddItemAPIHandler handler = new AddItemAPIHandler();
    	
    	handler.handleRequest(initialItemInput, ctx);
    	
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("deletePerson");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testDeleteItemAPIHandler() {
        DeleteItemAPIHandler handler = new DeleteItemAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);
        
        ItemResponse output = (ItemResponse) handler.handleRequest(deleteItemInput, ctx);
        
        Assert.assertEquals(true, output.getItemIsDeleted());
        Assert.assertEquals(1, output.getId());
    }
}
