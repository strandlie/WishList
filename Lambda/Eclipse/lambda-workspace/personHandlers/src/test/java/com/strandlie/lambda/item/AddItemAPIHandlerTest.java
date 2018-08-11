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
public class AddItemAPIHandlerTest {

    private static ItemRequest addItemInput;

    @BeforeClass
    public static void createInput() throws IOException {
    	addItemInput = new ItemRequest();
    	addItemInput.setTitle("iPhone X");
    	addItemInput.setDescription("The newest, most powerful phone ever. And we made it smaller and lighter");
    	addItemInput.setPrice(10990);
    	addItemInput.setWebsiteURL("www.apple.com/iphone");
    }
    
    @BeforeClass
    public static void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS wish");
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

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("deletePerson");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testAddItemAPIHandler() {
        AddItemAPIHandler handler = new AddItemAPIHandler();
        Context ctx = createContext();

        ItemResponse output = (ItemResponse) handler.handleRequest(addItemInput, ctx);
        
        Assert.assertEquals(1, output.getId());

    }
}
