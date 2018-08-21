package com.strandlie.lambda.gift;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
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
public class AddGiftAPIHandlerTest {

    private static GiftRequest addGiftInput;
    
    private static PersonRequest giverInput;
    private static PersonRequest recepientInput;
    private static ItemRequest itemInput;
    
    
    @BeforeClass
    public static void createDatabase() {
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
					"id INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"title VARCHAR(200)," + 
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
					"CONSTRAINT wisherID_FK_wish FOREIGN KEY (wisherID) REFERENCES person(id) " + 
						"ON DELETE SET NULL " + 
					    "ON UPDATE CASCADE, " +
					"CONSTRAINT itemID_FK_wish FOREIGN KEY (itemID) REFERENCES item(id) " + 
					    "ON DELETE CASCADE " + 
					    "ON UPDATE CASCADE" + 
					");");
			statement.executeUpdate("CREATE TABLE gift(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT, " + 
					"giverID INTEGER, " + 
					"recepientID INTEGER, " +
					"itemID INTEGER, " + 
					"quantity INTEGER, " + 
					"wishID INTEGER, " + 
					"CONSTRAINT giverID_FK_gift FOREIGN KEY (giverID) REFERENCES person(id) " + 
						"ON DELETE SET NULL " + 
					    "ON UPDATE CASCADE, " +
					 "CONSTRAINT recepientID_FK_gift FOREIGN KEY (recepientID) REFERENCES person(id) " + 
					    "ON DELETE SET NULL " + 
					    "ON UPDATE CASCADE, " + 
					"CONSTRAINT itemID_FK_gift FOREIGN KEY (itemID) REFERENCES item(id) " + 
					    "ON DELETE CASCADE " + 
					    "ON UPDATE CASCADE, " + 
					"CONSTRAINT wishID_FK_gift FOREIGN KEY (wishID) REFERENCES wish(id) " +
					    "ON DELETE SET NULL " + 
					    "ON UPDATE CASCADE" + 
					");");

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @BeforeClass
    public static void createInput() throws IOException {
    	giverInput = new PersonRequest();
    	giverInput.setFirstName("Giver");
    	giverInput.setLastName("Person");
    	
    	recepientInput = new PersonRequest();
    	recepientInput.setFirstName("Recepient");
    	recepientInput.setLastName("Person");
    	
    	itemInput = new ItemRequest();
    	itemInput.setTitle("Bathing Suit");
    	itemInput.setDescription("This amazing feature can do anything");
    	
    	addGiftInput = new GiftRequest();
    	addGiftInput.setQuantity(1);
    	
    }
    
    private void setupInitialDatabase(Context context) {
    	AddPersonAPIHandler personHandler = new AddPersonAPIHandler();
    	AddItemAPIHandler itemHandler = new AddItemAPIHandler();
    	
    	APIResponse giverResponse = personHandler.handleRequest(giverInput, context);
    	APIResponse recepientResponse = personHandler.handleRequest(recepientInput, context);
    	APIResponse itemResponse = itemHandler.handleRequest(itemInput, context);
    	
    	// Set remaining input-fields here
    	addGiftInput.setGiverID(giverResponse.getId());
    	addGiftInput.setRecepientID(recepientResponse.getId());
    	addGiftInput.setItemID(itemResponse.getId());
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("addGift");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testaddGiftAPIHandler() {
        AddGiftAPIHandler handler = new AddGiftAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);
        
        GiftResponse output = (GiftResponse) handler.handleRequest(addGiftInput, ctx);
       
        
        Assert.assertEquals(true, output.getGiftIsAdded());
        Assert.assertEquals(1, output.getId());
    }
}
