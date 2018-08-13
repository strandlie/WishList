package com.strandlie.lambda.giftgroup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import common.APIResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UpdateGiftGroupAPIHandlerTest {

    private static GiftGroupRequest initialGiftGroupInput;
    private static GiftGroupRequest updateGiftGroupInput;

    @Before
    public void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS wish");
			statement.executeUpdate("DROP TABLE IF EXISTS gift");
			statement.executeUpdate("DROP TABLE IF EXISTS personInGiftGroup");
			statement.executeUpdate("DROP TABLE IF EXISTS giftGroup");
			statement.executeUpdate("DROP TABLE IF EXISTS person");
			statement.executeUpdate("DROP TABLE IF EXISTS item");
			
			
			statement.executeUpdate("CREATE TABLE giftGroup(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
					"name VARCHAR(100), " +
					"description MEDIUMTEXT, " +
					"pictureURL MEDIUMTEXT " +
					");");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    @BeforeClass
    public static void createInput() throws IOException {
    	initialGiftGroupInput = new GiftGroupRequest();
    	initialGiftGroupInput.setName("Christmas 2018");
    	
    	updateGiftGroupInput = new GiftGroupRequest();
    	updateGiftGroupInput.setName("Christmas 2019");
    	updateGiftGroupInput.setDescription("The best Christmas ever!");
    }
    
    private void setupInitialDatabase(Context context) {
    	AddGiftGroupAPIHandler addGiftGroupHandler = new AddGiftGroupAPIHandler();
    	
    	APIResponse addGiftGroupResponse = addGiftGroupHandler.handleRequest(initialGiftGroupInput, context);
    	
    	updateGiftGroupInput.setId(addGiftGroupResponse.getId());
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("updateGiftGroup");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testUpdateGiftGroupAPIHandler() {
        UpdateGiftGroupAPIHandler handler = new UpdateGiftGroupAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);

        GiftGroupResponse output = (GiftGroupResponse) handler.handleRequest(updateGiftGroupInput, ctx);

        Assert.assertEquals(updateGiftGroupInput.getId().intValue(), output.getId());
        Assert.assertTrue(output.getGiftGroupIsUpdated());
        
    }
}
