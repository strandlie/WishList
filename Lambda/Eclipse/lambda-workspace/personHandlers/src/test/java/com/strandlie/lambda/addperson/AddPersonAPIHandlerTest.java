package com.strandlie.lambda.addperson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddPersonAPIHandlerTest {

    private static PersonRequest addPersonInput;
    private static PersonRequest updatePersonInput;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        addPersonInput = new PersonRequest();
        addPersonInput.setFirstName("Marte");
        addPersonInput.setLastName("Sivesind");
        addPersonInput.setEmail("martesivesind@gmail.com");
        addPersonInput.setPhoneNr("99511597");
        
        updatePersonInput = new PersonRequest();
        updatePersonInput.setId(1);
        updatePersonInput.setFirstName("Martin");
    }
    
    @BeforeClass
    public static void createDatabase() {
    	
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testAddPersonAPIHandler() {
        AddPersonAPIHandler handler = new AddPersonAPIHandler();
        Context ctx = createContext();

        //String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        //Assert.assertEquals("Hello from Lambda!", output);
    }
}
