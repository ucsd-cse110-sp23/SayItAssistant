package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import sayItAssistant.components.LoginConfig;
import sayItAssistant.data.DataBase;
import sayItAssistant.mocking.MockDataBase;

public class StoryTestUS11 {
  @BeforeEach
  void setUp() throws Exception {
    DataBase db = new MockDataBase();
    boolean signUpResult = db.signUp("helenk@ucsd.edu", "hk12345");
  }

  @AfterEach
  void tearDown() throws Exception {
    try (MongoClient mongoClient =
        MongoClients.create(
            "mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvh"
                + "j.mongodb.net/?retryWrites=true&w=majority")) {
      MongoDatabase db = mongoClient.getDatabase("JunitTest");
      db.drop();
    }
  }

  /*+----------------------------------------------------------------------
  ||
  ||	Scenario 1: Using Auto Login  
  ||	Given: Account for the application is created
  ||	When: Helen creates her new account by entering her email “helenk@ucsd.edu",
  ||	password “hk12345” and verifies the password “hk12345” on her computer.
  ||	Then: A new screen pops up and asks “Would you like to save this account?”. Also
  ||	showing the “Yes” button and “No” button below
  ||	When: Helen clicks the “Yes” button to save her account on her CSE lab computer 
  ||	Then: The application remembers Helen’s account and lets Helen automatically log in
  ||	the next time when she uses her CSE lab computer
  ||
  |+-----------------------------------------------------------------------
  ||
  ||  First initialize DataBase object and call signUp method in @BeforeEach
  ||	call logIn method using valid id and password
  ||	Check the result:
  ||		-1:wrong id
  ||		0: login success
  ||		1: wrong pw
  ||  Create LoginConfig object and set autoLogin property to true
  ||  Store the property
  ||  Check if the property is stored correctly
  ||
  ++-----------------------------------------------------------------------*/
  @Test
  void scenarioOneTest() {
    DataBase db = new MockDataBase();
    int result = db.logIn("helenk@ucsd.edu", "hk12345");
    assertEquals(0, result);

    LoginConfig loginConfig = new LoginConfig();
    loginConfig.setProperty("autoLogin", "true");
    loginConfig.store();

    String autoLogin = loginConfig.getProperty("autoLogin");
    assertEquals("true", autoLogin);
  }

  /*+----------------------------------------------------------------------
  ||
  ||	Scenario 2: Not Using Auto Login
  ||	Given: Account for the application is created
  ||	When: Helen creates her new account by entering her email “helenk@ucsd.edu",
  ||	password “hk12345” and verifies the password “hk12345” in her CSE lab computer.
  ||	Then: New screen pops up and asks “Would you like to save this account?”
  ||	When: Helen clicks the “No” button accidentally
  ||	Then: The application fails to save Helen’s account
  ||
  |+-----------------------------------------------------------------------
  ||
  ||  First initialize DataBase object and call signUp method in @BeforeEach
  ||	call logIn method using valid id and password
  ||	Check the result:
  ||		-1:wrong id
  ||		0: login success
  ||		1: wrong pw
  ||  Create LoginConfig object and set autoLogin property to false
  ||  Store the property
  ||  Check if the property is stored correctly
  ||
  ++-----------------------------------------------------------------------*/

  @Test
  void scenarioTwoTest() {
    DataBase db = new MockDataBase();
    int result = db.logIn("helenk@ucsd.edu", "hk12345");
    assertEquals(0, result);

    LoginConfig loginConfig = new LoginConfig();
    loginConfig.setProperty("autoLogin", "false");
    loginConfig.store();

    String autoLogin = loginConfig.getProperty("autoLogin");
    assertEquals("false", autoLogin);
  }
}
