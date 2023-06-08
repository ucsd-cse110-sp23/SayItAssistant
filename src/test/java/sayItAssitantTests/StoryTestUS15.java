package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import sayItAssistant.components.EmailConfig;
import sayItAssistant.data.Answer;
import sayItAssistant.data.DataBase;
import sayItAssistant.data.Question;
import sayItAssistant.mocking.MockDataBase;

public class StoryTestUS15 {
    @AfterEach
    void tearDown() throws Exception {
            try (MongoClient mongoClient = MongoClients.create("mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority")) {
                MongoDatabase db = mongoClient.getDatabase("JunitTest");
                db.drop();
            }
    }

  /*+----------------------------------------------------------------------
  || 
  || Scenario1: Setup the email
  || Given: Helen has SayItAssistant 2 app opened in her desktop
  || When: Helen clicks the “Start” button and says “Setup email”
  || Then: The email setup screen pops up and asks for Helen’s first name, last name, display name, email address, SMTP host, TLS port and email password
  || When: Helen enters all the necessary information in the setup screen first name: “Helen”, last name: “Keller”, display name: “HelloHellen”, email address: “helenk@ucsd.edu”, SMTP host: “send.smtp.mailtrap.io”, TLS port: “2525” and email password: “hk12345” and clicks “Save” button
  || Then: All the information about Helen gets saved (and no need to repeat again if all the information is correct)
  || 
  |+-----------------------------------------------------------------------
  ||  First initialize DataBase object and call signUp method same as our UI
  ||  Check return value of the method
  ||  Create EmailConfig object
  ||  Set properties
  ||  Ensure the properties are stored correctly
  ||
  ++-----------------------------------------------------------------------*/
  @Test
  void scenarioOneTest() {
        DataBase db = new MockDataBase();
        boolean signUpResult = db.signUp("helenk@ucsd.edu","hk12345");
        assertEquals(true, signUpResult);
        EmailConfig emailDetails= new EmailConfig();
        emailDetails.setProperty("LastName", "Keller");
        emailDetails.setProperty("FirstName", "Helen");
        emailDetails.setProperty("DisplayName", "HelloHellen");
        emailDetails.setProperty("EmailAddress", "helenk@ucsd.edu");
        emailDetails.setProperty("SMTP", "send.smtp.mailtrap.io");
        emailDetails.setProperty("TLSPort", "2525");
        emailDetails.setProperty("Password","hk12345");

        assertEquals("helenk@ucsd.edu", emailDetails.getProperty("EmailAddress"));
        assertEquals("hk12345",emailDetails.getProperty("Password"));
        assertEquals("Keller",emailDetails.getProperty("LastName"));
        assertEquals("Helen",emailDetails.getProperty("FirstName"));
        assertEquals("HelloHellen",emailDetails.getProperty("DisplayName"));
        assertEquals("send.smtp.mailtrap.io",emailDetails.getProperty("SMTP"));
        assertEquals("2525",emailDetails.getProperty("TLSPort"));

  }

 /*+----------------------------------------------------------------------
  ||
  ||  Scenario 2: Setup email failed
  ||  Given: Helen has put all the information needed in the setup screen
  ||  When: Helen clicks “Cancel” button
  ||  Then: All the information written previously gets emptied
  |+-----------------------------------------------------------------------
  || 
  ||  First initialize DataBase object and call signUp method same as our UI
  ||  Check return value of the method
  ||  Create EmailConfig object
  ||  Ensure the properties are not set/stored
  ||
  ++-----------------------------------------------------------------------*/
    @Test
    void scenarioTwoTest() {
        DataBase db = new MockDataBase();
        boolean signUpResult = db.signUp("helenk@ucsd.edu","hk12345");
        assertEquals(true, signUpResult);
        EmailConfig emailDetails= new EmailConfig();
        assertEquals("", emailDetails.getProperty("EmailAddress"));
        assertEquals("",emailDetails.getProperty("Password"));
        assertEquals("",emailDetails.getProperty("LastName"));
        assertEquals("",emailDetails.getProperty("FirstName"));
        assertEquals("",emailDetails.getProperty("DisplayName"));
        assertEquals("",emailDetails.getProperty("SMTP"));
        assertEquals("",emailDetails.getProperty("TLSPort"));
    }
    
}
