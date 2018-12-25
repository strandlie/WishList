package com.strandlie.lambda.person;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.strandlie.lambda.person.AddPersonAPIHandler;
import com.strandlie.lambda.person.PersonRequest;
import com.strandlie.lambda.person.PersonResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddPersonAPIHandlerTest {

    private static PersonRequest addPersonInput;

    @BeforeClass
    public static void createInput() {
        addPersonInput = new PersonRequest();
        addPersonInput.setFirstName("Test");
        addPersonInput.setLastName("Person");
        addPersonInput.setEmail("tull@tull.no");
        addPersonInput.setPhoneNr("12345678");
        addPersonInput.setPictureURL("skjdla.iitjhja.hgb");
        
    }
    
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
    
    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("addPerson");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testAddPersonAPIHandlerReturnsIDExpectOne() {
        AddPersonAPIHandler handler = new AddPersonAPIHandler();
        Context ctx = createContext();

        PersonResponse output = (PersonResponse) handler.handleRequest(addPersonInput, ctx);

        Assert.assertEquals(1, output.getId());
    }
}
