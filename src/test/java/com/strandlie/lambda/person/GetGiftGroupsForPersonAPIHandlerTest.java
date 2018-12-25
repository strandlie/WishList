package com.strandlie.lambda.person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.strandlie.lambda.common.APIResponse;
import com.strandlie.lambda.giftgroup.AddGiftGroupAPIHandler;
import com.strandlie.lambda.giftgroup.AddPersonsToGiftGroupAPIHandler;
import com.strandlie.lambda.giftgroup.GiftGroupRequest;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetGiftGroupsForPersonAPIHandlerTest {

    private static GiftGroupRequest giftGroup1Input;
    private static GiftGroupRequest giftGroup2Input;
    private static GiftGroupRequest giftGroup3Input;
    private static PersonRequest personInput;
    
    private static GiftGroupRequest addPersonToGiftGroup1Input;
    private static GiftGroupRequest addPersonToGiftGroup2Input;
    private static GiftGroupRequest addPersonToGiftGroup3Input;
    
    private static PersonRequest getGiftGroupForPersonInput;
	
    @BeforeClass
    public static void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("DROP TABLE IF EXISTS gift");
			statement.executeUpdate("DROP TABLE IF EXISTS wish");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }

    @BeforeClass
    public static void createInput() throws IOException {
    	giftGroup1Input = new GiftGroupRequest();
    	giftGroup1Input.setName("Christmas 2018");
    	
    	giftGroup2Input = new GiftGroupRequest();
    	giftGroup2Input.setName("Christmas 2019");
    	
    	giftGroup3Input = new GiftGroupRequest();
    	giftGroup3Input.setName("Christmas 2020");
    	
    	personInput = new PersonRequest();
    	personInput.setFirstName("No. 1");
    	personInput.setLastName("Person");
    	
    	addPersonToGiftGroup1Input = new GiftGroupRequest();
    	addPersonToGiftGroup2Input = new GiftGroupRequest();
    	addPersonToGiftGroup3Input = new GiftGroupRequest();
    	
    	getGiftGroupForPersonInput = new PersonRequest();
    	
    	
    	
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("updatePerson");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }
    
    private void setupInitialDatabase(Context ctx) {
    	AddPersonAPIHandler addPersonHandler = new AddPersonAPIHandler();
    	AddGiftGroupAPIHandler addGiftGroupHandler = new AddGiftGroupAPIHandler();
    	AddPersonsToGiftGroupAPIHandler addPersonToGiftGroupHandler = new AddPersonsToGiftGroupAPIHandler();
    	
    	APIResponse personResponse = addPersonHandler.handleRequest(personInput, ctx);
    	
    	APIResponse giftGroup1Response = addGiftGroupHandler.handleRequest(giftGroup1Input, ctx);
    	APIResponse giftGroup2Response = addGiftGroupHandler.handleRequest(giftGroup2Input, ctx);
    	APIResponse giftGroup3Response = addGiftGroupHandler.handleRequest(giftGroup3Input, ctx);
    	
    	addPersonToGiftGroup1Input.setId(giftGroup1Response.getId());
    	addPersonToGiftGroup1Input.setPersonsInGiftGroup(Arrays.asList(personResponse.getId()));
    	addPersonToGiftGroupHandler.handleRequest(addPersonToGiftGroup1Input, ctx);
    	
    	addPersonToGiftGroup2Input.setId(giftGroup2Response.getId());
    	addPersonToGiftGroup2Input.setPersonsInGiftGroup(Arrays.asList(personResponse.getId()));
    	addPersonToGiftGroupHandler.handleRequest(addPersonToGiftGroup2Input, ctx);
    	
    	addPersonToGiftGroup3Input.setId(giftGroup3Response.getId());
    	addPersonToGiftGroup3Input.setPersonsInGiftGroup(Arrays.asList(personResponse.getId()));
    	addPersonToGiftGroupHandler.handleRequest(addPersonToGiftGroup3Input, ctx);
    	
    	getGiftGroupForPersonInput = new PersonRequest();
    	getGiftGroupForPersonInput.setId(personResponse.getId());
    	
    	
    }

    @Test
    public void testGetPersonAPIHandler() {
        GetGiftGroupsForPersonAPIHandler handler = new GetGiftGroupsForPersonAPIHandler();
        Context ctx = createContext();
		setupInitialDatabase(ctx);
        
        PersonResponse output = (PersonResponse) handler.handleRequest(getGiftGroupForPersonInput, ctx);
        
        Assert.assertTrue(output.getGiftGroupsForPerson().contains(1));
        Assert.assertTrue(output.getGiftGroupsForPerson().contains(2));
        Assert.assertTrue(output.getGiftGroupsForPerson().contains(3));

    }
}