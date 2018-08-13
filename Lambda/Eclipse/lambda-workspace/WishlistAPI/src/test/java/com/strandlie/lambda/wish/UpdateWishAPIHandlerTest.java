package com.strandlie.lambda.wish;

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
import com.strandlie.lambda.common.APIResponse;
import com.strandlie.lambda.item.AddItemAPIHandler;
import com.strandlie.lambda.item.ItemRequest;
import com.strandlie.lambda.person.AddPersonAPIHandler;
import com.strandlie.lambda.person.PersonRequest;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UpdateWishAPIHandlerTest {

    private static WishRequest initialWishInput;
    private static WishRequest updateWishInput;
    
    private static PersonRequest initialWisherInput;
    private static ItemRequest initialItemInput;
    
    private static PersonRequest updateWisherInput;
    private static ItemRequest updateItemInput;
    
    @Before
    public void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS gift");
			statement.executeUpdate("DROP TABLE IF EXISTS wish");
			statement.executeUpdate("DROP TABLE IF EXISTS person");
			statement.executeUpdate("DROP TABLE IF EXISTS item");
			
			
			statement.executeUpdate("CREATE TABLE item(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT," + "title VARCHAR(200)," + 
					"description MEDIUMTEXT," + 
					"pictureURL MEDIUMTEXT," +
					"websiteURL MEDIUMTEXT," +
					"price DOUBLE" +
					");");
			statement.executeUpdate("CREATE TABLE person(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"firstName VARCHAR(60)," + 
					"lastName VARCHAR(100)," + 
					"email VARCHAR(200)," + 
					"phoneNr VARCHAR(20)," +
					"pictureURL VARCHAR(300)" + 
					");");
			statement.executeUpdate("CREATE TABLE wish(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT, " + 
					"wisherID INTEGER, " + 
					"itemID INTEGER, " + 
					"quantity INTEGER, " + 
					"CONSTRAINT wisherID_FK FOREIGN KEY (wisherID) REFERENCES person(id) " + 
						"ON DELETE SET NULL " + 
					    "ON UPDATE CASCADE, " +
					"CONSTRAINT itemID_FK FOREIGN KEY (itemID) REFERENCES item(id) " + 
					    "ON DELETE CASCADE " + 
					    "ON UPDATE CASCADE" + 
					");");

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @BeforeClass
    public static void createInput() throws IOException {
    	initialWisherInput = new PersonRequest();
    	initialWisherInput.setFirstName("Wisher");
    	initialWisherInput.setLastName("Person");
    	
    	initialItemInput = new ItemRequest();
    	initialItemInput.setTitle("Coffee Lake");
    	
    	updateWisherInput = new PersonRequest();
    	updateWisherInput.setFirstName("New Wisher");
    	updateWisherInput.setLastName("Person");
    	
    	updateItemInput = new ItemRequest();
    	updateItemInput.setTitle("Kaby Lake");
    	
    	initialWishInput = new WishRequest();
    	initialWishInput.setQuantity(2);
    	
    	updateWishInput = new WishRequest();
    	updateWishInput.setQuantity(3);
    }
    
    private void setupInitialDatabase(Context context) {
    	AddPersonAPIHandler personHandler = new AddPersonAPIHandler();
    	AddItemAPIHandler itemHandler = new AddItemAPIHandler();
    	AddWishAPIHandler addWishHandler = new AddWishAPIHandler();
    	
    	APIResponse initialWisherResponse = personHandler.handleRequest(initialWisherInput, context);
    	APIResponse initialItemResponse = itemHandler.handleRequest(initialItemInput, context);
        
    	
    	APIResponse updateWisherResponse = personHandler.handleRequest(updateWisherInput, context);
    	APIResponse updateItemResponse = itemHandler.handleRequest(updateItemInput, context);
    	
    	// Set remaining input-fields here
    	initialWishInput.setWisherID(initialWisherResponse.getId());
    	initialWishInput.setItemID(initialItemResponse.getId());
    	
    	updateWishInput.setWisherID(updateWisherResponse.getId());
    	updateWishInput.setItemID(updateItemResponse.getId());
    	
    	APIResponse addWishOutput = addWishHandler.handleRequest(initialWishInput, context);
    	updateWishInput.setId(addWishOutput.getId());
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("updateWish");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testUpdateWishAPIHandler() {
        UpdateWishAPIHandler updateWishHandler = new UpdateWishAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);
        
        WishResponse updateWishOutput = (WishResponse) updateWishHandler.handleRequest(updateWishInput, ctx);
        
        Assert.assertEquals(1, updateWishOutput.getId());
        Assert.assertTrue(updateWishOutput.getWishIsUpdated());

    }
}
