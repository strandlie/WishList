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
public class DeleteGiftAPIHandlerTest {


    private static GiftRequest initialGiftInput;
    private static GiftRequest deleteGiftInput;
    
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
    
    @BeforeClass
    public static void createInput() throws IOException {
    	giverInput = new PersonRequest();
    	giverInput.setFirstName("Giver");
    	giverInput.setLastName("Person");
    	
    	recepientInput = new PersonRequest();
    	recepientInput.setFirstName("Recepient");
    	recepientInput.setLastName("Person");
    	
    	itemInput = new ItemRequest();
    	itemInput.setTitle("iPhone X");
    	
    	initialGiftInput = new GiftRequest();
    	initialGiftInput.setQuantity(4);
    	
    	deleteGiftInput = new GiftRequest();
    	deleteGiftInput.setId(1);
    }

    private void setupInitialDatabase(Context ctx) {
    	AddPersonAPIHandler personHandler = new AddPersonAPIHandler();
    	AddItemAPIHandler itemHandler = new AddItemAPIHandler();
    	
    	APIResponse giverResponse = personHandler.handleRequest(giverInput, ctx);
    	APIResponse recepientResponse = personHandler.handleRequest(recepientInput, ctx);
    	APIResponse itemResponse = itemHandler.handleRequest(itemInput, ctx);
    	
    	// Set remaining input-fields here
    	initialGiftInput.setGiverID(giverResponse.getId());
    	initialGiftInput.setRecepientID(recepientResponse.getId());
    	initialGiftInput.setId(itemResponse.getId());
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("deleteGift");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }
    
    @Test
    public void testDeleteGiftAPIHandler() {
    	DeleteGiftAPIHandler handler = new DeleteGiftAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);

        GiftResponse output = (GiftResponse) handler.handleRequest(deleteGiftInput, ctx);

        Assert.assertEquals(true, output.getGiftIsDeleted());
        Assert.assertEquals(1,  output.getId());
    }
}
