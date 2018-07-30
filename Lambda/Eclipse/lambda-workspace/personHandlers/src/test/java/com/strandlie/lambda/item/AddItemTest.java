package com.strandlie.lambda.item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.strandlie.lambda.person.PersonRequest;
import com.strandlie.lambda.person.PersonResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddItemTest {

    private static PersonRequest deletePersonInput;

    @BeforeClass
    public static void createInput() throws IOException {
    	deletePersonInput = new PersonRequest();
    	deletePersonInput.setId(1);
    }
    
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
			
			statement.executeUpdate("INSERT INTO person(firstName, lastName) " + 
			"VALUES(marte, sivesind)");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("deletePerson");
        ctx.setMemoryLimitInMB(128);
        ctx.setRemainingTimeInMillis(15000);

        return ctx;
    }

    @Test
    public void testAddItem() {
        AddItemAPIHandler handler = new AddItemAPIHandler();
        Context ctx = createContext();

        PersonResponse output = (PersonResponse) handler.handleRequest(deletePersonInput, ctx);

        Assert.assertEquals(1, output.getId());
    }
}
