package com.strandlie.lambda.person;

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
public class DeletePersonAPIHandlerTest {

	private static PersonRequest initialPersonInput;
    private static PersonRequest deletePersonInput;
    
    
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
			statement.executeUpdate("CREATE TABLE person(" + 
					"id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
					"firstName VARCHAR(60)," + 
					"lastName VARCHAR(100)," + 
					"email VARCHAR(200)," + 
					"phoneNr VARCHAR(20)," +
					"pictureURL VARCHAR(300)" + 
					");");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @BeforeClass
    public static void createInput() throws IOException {
        initialPersonInput = new PersonRequest();
        initialPersonInput.setFirstName("Marte");
        initialPersonInput.setLastName("Sivesind");
        initialPersonInput.setEmail("tull@tull.no");
        initialPersonInput.setPhoneNr("12345678");
        initialPersonInput.setPictureURL("skjdla.iitjhja.hgb");
    	
    	deletePersonInput = new PersonRequest();
    	deletePersonInput.setId(1);
    }
    
    private void setupInitialDatabase(Context ctx) {
    	AddPersonAPIHandler handler = new AddPersonAPIHandler();
    	
    	handler.handleRequest(initialPersonInput, ctx);
    	
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("deletePerson");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testDeletePersonAPIHandler() {
        DeletePersonAPIHandler handler = new DeletePersonAPIHandler();
        Context ctx = createContext();
		setupInitialDatabase(ctx);
        
        PersonResponse output = (PersonResponse) handler.handleRequest(deletePersonInput, ctx);
        
        Assert.assertEquals(true, output.getPersonIsDeleted());
        Assert.assertEquals(1, output.getId());

    }
}
