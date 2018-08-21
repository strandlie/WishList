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
public class AddWishAPIHandlerTest {

    private static WishRequest addWishInput;
    
    private static PersonRequest wisherInput;
    private static ItemRequest itemInput;

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
    	wisherInput = new PersonRequest();
    	wisherInput.setFirstName("Wisher");
    	wisherInput.setLastName("Person");
    	
    	itemInput = new ItemRequest();
    	itemInput.setTitle("Coffee Lake");
    	
    	addWishInput = new WishRequest();
    	addWishInput.setQuantity(2);
    }
    
    
    private void setupInitialDatabase(Context context) {
    	AddPersonAPIHandler personHandler = new AddPersonAPIHandler();
    	AddItemAPIHandler itemHandler = new AddItemAPIHandler();
    	
    	APIResponse wisherResponse = personHandler.handleRequest(wisherInput, context);
    	APIResponse itemResponse = itemHandler.handleRequest(itemInput, context);
    	
    	// Set remaining input-fields here
    	addWishInput.setWisherID(wisherResponse.getId());
    	addWishInput.setItemID(itemResponse.getId());
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("addWish");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testAddWishAPIHandler() {
        AddWishAPIHandler handler = new AddWishAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);

        WishResponse output = (WishResponse) handler.handleRequest(addWishInput, ctx);
        
        Assert.assertEquals(1, output.getId());
        Assert.assertEquals(true, output.getWishIsAdded());

    }
}
