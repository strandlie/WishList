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
public class GetItemAPIHandlerTest {

    private static ItemRequest initialItemInput;
    private static ItemRequest getItemInput;
    
    @BeforeClass
    public static void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS gift");
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
			e.printStackTrace();
		}
    }

    @BeforeClass
    public static void createInput() throws IOException {
    	initialItemInput = new ItemRequest();
    	initialItemInput.setTitle("iPhone X");
    	initialItemInput.setDescription("The newest, most powerful phone ever. And we made it smaller and lighter");
    	initialItemInput.setPrice(10990);
    	initialItemInput.setWebsiteURL("www.apple.com/iphoneX");
    	
    	getItemInput = new ItemRequest();
    	getItemInput.setId(1);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("getItem");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }
    
    
    private void setupInitialDatabase(Context ctx) {
    	AddItemAPIHandler handler = new AddItemAPIHandler();
    	
    	handler.handleRequest(initialItemInput, ctx);
    }

    @Test
    public void testGetItemAPIHandler() {
        GetItemAPIHandler handler = new GetItemAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);

        ItemResponse output = (ItemResponse) handler.handleRequest(getItemInput, ctx);

        Assert.assertEquals(initialItemInput.getTitle(), output.getTitle());
        Assert.assertEquals(initialItemInput.getDescription(), output.getDescription());
        Assert.assertEquals(initialItemInput.getPictureURL(), output.getPictureURL());
        Assert.assertEquals(initialItemInput.getWebsiteURL(), output.getWebsiteURL());
        Assert.assertEquals(initialItemInput.getPrice(), output.getPrice());
    }
}
