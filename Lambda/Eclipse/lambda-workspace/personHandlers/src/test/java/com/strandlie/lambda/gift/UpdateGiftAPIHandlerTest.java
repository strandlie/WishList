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
import com.strandlie.lambda.item.AddItemAPIHandler;
import com.strandlie.lambda.item.ItemRequest;
import com.strandlie.lambda.person.AddPersonAPIHandler;
import com.strandlie.lambda.person.PersonRequest;

import common.APIResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UpdateGiftAPIHandlerTest {

    private static GiftRequest initialGiftInput;
    private static GiftRequest updateGiftInput;
    
    private static PersonRequest initialGiverInput;
    private static PersonRequest initialRecepientInput;
    
    private static ItemRequest initialItemInput;
    private static ItemRequest updateItemInput;

    @BeforeClass
    public static void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS gift");
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
			statement.executeUpdate("CREATE TABLE gift(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT, " + 
					"giverID INTEGER, " + 
					"recepientID INTEGER, " +
					"itemID INTEGER, " + 
					"quantity INTEGER, " + 
					"CONSTRAINT giverID_FK FOREIGN KEY (giverID) REFERENCES person(id) " + 
						"ON DELETE SET NULL " + 
					    "ON UPDATE CASCADE, " +
					 "CONSTRAINT recepientID_FK FOREIGN KEY (recepientID) REFERENCES person(id) " + 
					    "ON DELETE SET NULL " + 
					    "ON UPDATE CASCADE, " + 
					"CONSTRAINT itemID_FK FOREIGN KEY (itemID) REFERENCES item(id) " + 
					    "ON DELETE CASCADE " + 
					    "ON UPDATE CASCADE" + 
					");");

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void setupInitialDatabase(Context ctx) {
    	AddPersonAPIHandler personHandler = new AddPersonAPIHandler();
    	AddItemAPIHandler itemHandler = new AddItemAPIHandler();
    	
    	APIResponse giverResponse = personHandler.handleRequest(initialGiverInput, ctx);
    	APIResponse recepientResponse = personHandler.handleRequest(initialRecepientInput, ctx);
    	APIResponse initialItemResponse = itemHandler.handleRequest(initialItemInput, ctx);
    	APIResponse updateItemResponse = itemHandler.handleRequest(updateItemInput, ctx);
    	
    	// Set remaining input-fields here
    	initialGiftInput.setGiverID(giverResponse.getId());
    	initialGiftInput.setRecepientID(recepientResponse.getId());
    	initialGiftInput.setId(initialItemResponse.getId());
    	
    	updateGiftInput.setItemID(updateItemResponse.getId());
    }
    
    @BeforeClass
    public static void createInput() throws IOException {
    	initialGiverInput = new PersonRequest();
    	initialGiverInput.setFirstName("Giver");
    	initialGiverInput.setLastName("Person");
    	
    	initialRecepientInput = new PersonRequest();
    	initialRecepientInput.setFirstName("Recepient");
    	initialRecepientInput.setLastName("Person");
    	
    	initialItemInput = new ItemRequest();
    	initialItemInput.setTitle("iPhone X");
    	
    	updateItemInput = new ItemRequest();
    	updateItemInput.setTitle("iPhone 8");
    	
    	initialGiftInput = new GiftRequest();
    	initialGiftInput.setQuantity(1);
    	
    	updateGiftInput = new GiftRequest();
    	updateGiftInput.setQuantity(1);
    	
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("updateGift");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

	@SuppressWarnings("unused")
	@Test
    public void testUpdateGiftAPIHandler() {
        AddGiftAPIHandler addHandler = new AddGiftAPIHandler();
        UpdateGiftAPIHandler updateHandler = new UpdateGiftAPIHandler();
        
        Context ctx = createContext();
        setupInitialDatabase(ctx);

        GiftResponse output = (GiftResponse) addHandler.handleRequest(initialGiftInput, ctx);

        updateGiftInput.setId(output.getId());
        
        Assert.assertEquals(1, output.getId());
        Assert.assertEquals(false, output.getGiftIsUpdated());
        
        GiftResponse updateOutput = (GiftResponse) updateHandler.handleRequest(updateGiftInput, ctx);
        
        Assert.assertEquals(1, updateOutput.getId());
        Assert.assertEquals(true, updateOutput.getGiftIsUpdated());
    }
}
