package com.strandlie.lambda.addperson;

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
public class UpdatePersonAPIHandlerTest {
	
	private static PersonRequest initialPersonInput;
    private static PersonRequest updatePersonInput;
    
    @BeforeClass
    public static void createDatabase() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:" + System.getenv("DBDriver") + ":" + System.getenv("DBPath"), System.getenv("DBUsername"), System.getenv("DBPassword"));
			connection.setCatalog(System.getenv("DBDatabase"));
			Statement statement = connection.createStatement();
			
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
        initialPersonInput.setEmail("martesivesind@gmail.com");
        initialPersonInput.setPhoneNr("99511597");
        initialPersonInput.setPictureURL("skjdla.iitjhja.hgb");
        
        updatePersonInput = new PersonRequest();
        updatePersonInput.setId(1);
        updatePersonInput.setFirstName("Martin");
        /*updatePersonInput.setLastName("Stangjordet");
        updatePersonInput.setEmail("martin.stangjordet@gmail.com");
        updatePersonInput.setPhoneNr("81549300");
        updatePersonInput.setPictureURL("hhhla.skjda.nkl");
        */
    }
    
    private void setupInitialDatabase(Context ctx) throws SQLException {
    	AddPersonAPIHandler handler = new AddPersonAPIHandler();
    	
    	handler.handleRequest(initialPersonInput, ctx);
    	
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("updatePerson");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testUpdatePersonAPIHandler() {
    	UpdatePersonAPIHandler handler = new UpdatePersonAPIHandler();
    	Context ctx = createContext();
    	try {
			setupInitialDatabase(ctx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	PersonResponse output = handler.handleRequest(updatePersonInput, ctx);
    	
    	Assert.assertEquals(true, output.getPersonIsUpdated());
    	Assert.assertEquals(1, output.getId());
    }
}
