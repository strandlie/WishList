package com.strandlie.lambda.giftgroup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.strandlie.lambda.common.APIResponse;
import com.strandlie.lambda.person.AddPersonAPIHandler;
import com.strandlie.lambda.person.PersonRequest;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddPersonsToGiftGroupAPIHandlerTest {

    private static GiftGroupRequest addPersonsToGiftGroupInput;
    
    private static GiftGroupRequest giftGroupInput;
    private static PersonRequest person1Input;
    private static PersonRequest person2Input;

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
			
			statement.executeUpdate("CREATE TABLE person(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"firstName VARCHAR(60)," + 
					"lastName VARCHAR(100)," + 
					"email VARCHAR(200)," + 
					"phoneNr VARCHAR(20)," +
					"pictureURL VARCHAR(300)" + 
					");");

			statement.executeUpdate("CREATE TABLE personInGiftGroup(" +
					"personID INTEGER, " + 
					"groupID INTEGER, " + 
					" CONSTRAINT personInGroup_PK PRIMARY KEY(personID, groupID), " +
					"CONSTRAINT personID_FK FOREIGN KEY (personID) REFERENCES person(id) " +
						"ON DELETE CASCADE " + 
						"ON UPDATE CASCADE, " +
					"CONSTRAINT groupID_FK FOREIGN KEY (groupID) REFERENCES giftGroup(id) " + 
						"ON DELETE CASCADE " + 
						"ON UPDATE CASCADE " +
					");");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @BeforeClass
    public static void createInput() throws IOException {
    	giftGroupInput = new GiftGroupRequest();
    	giftGroupInput.setName("Christmas 2018");
    	
    	person1Input = new PersonRequest();
    	person1Input.setFirstName("Nr1");
    	person1Input.setLastName("Person");
    	
    	person2Input = new PersonRequest();
    	person2Input.setFirstName("Nr2");
    	person2Input.setLastName("Person");
    	
    	addPersonsToGiftGroupInput = new GiftGroupRequest();
    }
    
    private void setupInitialDatabase(Context context) {
    	AddPersonAPIHandler personHandler = new AddPersonAPIHandler();
    	AddGiftGroupAPIHandler giftGroupHandler = new AddGiftGroupAPIHandler();
    	
    	APIResponse person1Response = personHandler.handleRequest(person1Input, context);
    	APIResponse person2Response = personHandler.handleRequest(person2Input, context);
    	APIResponse giftGroupResponse = giftGroupHandler.handleRequest(giftGroupInput, context);
    	
    	// Set remaining input-fields here
    	addPersonsToGiftGroupInput.setId(giftGroupResponse.getId());
    	addPersonsToGiftGroupInput.setPersonsInGiftGroup(Arrays.asList(person1Response.getId(), person2Response.getId()));
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("addPersonsToGiftGroup");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testAddPersonToGiftGroupAPIHandler() {
        AddPersonsToGiftGroupAPIHandler handler = new AddPersonsToGiftGroupAPIHandler();
        Context ctx = createContext();
        setupInitialDatabase(ctx);

        GiftGroupResponse output = (GiftGroupResponse) handler.handleRequest(addPersonsToGiftGroupInput, ctx);
        
        Assert.assertTrue(output.getPersonsAreAddedToGiftGroup());
    }
}
