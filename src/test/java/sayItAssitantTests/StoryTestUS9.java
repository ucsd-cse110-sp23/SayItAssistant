package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import sayItAssistant.data.DataBase;
import sayItAssistant.mocking.MockDataBase;

class StoryTestUS9 {

	@BeforeEach
    void setUp() throws Exception {
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority")) {
            MongoDatabase db = mongoClient.getDatabase("JunitTest");
            db.drop();
        }
    }

	@AfterEach
	void tearDown() throws Exception {
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority")) {
            MongoDatabase db = mongoClient.getDatabase("JunitTest");
            db.drop();
        }
	}
	
	/*+----------------------------------------------------------------------
	||
	||	Scenario 1: Creating a new account
	||	Given: Helen updates the SayItAssisant application
	||	When: Helen opens the SayItAssistant 2 application on her secure computer for the first time
	||	Then: The application prompts Helen to enter her email address and a password
	||	When: Helen enters “helenk@ucsd.edu” in email address field, enters “hk12345” in password field and enters “hk12345” again in verify password field then presses the “Create Account” button.
	||	Then: Application shows the success message “New account created!”
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method same as our UI
    ||	Check return value of the method
    ||	true - new account created!
    ||	false - failed
    ||	check if its question list is emptied initially
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioOneTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helen@ucsd.edu", "hk12345");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
	}
	
	/*+----------------------------------------------------------------------
	||
	||	Scenario 2: Empty email and password fields
	||	Given: Helen opens the SayItAssistant2 app and it prompts her to the create account screen
	||	When: Helen put nothing in email and password fields and press the “Create Account” button
	||	Then: The application shows an error message “Email address is required!”
	||	When: Helen puts “helenk@ucsd.edu” in email address field then press the “Create Account” button.
	||	Then: The application shows an error message “Password is required!”
	||	When: Helen puts “hk12345” in the password field then presses the “Create Account” button.
	||	Then: The application shows an error message “Password Verification is required!”
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method same as our UI
    ||	Pass empty string for id and pw
    ||	Check return value of the method
    ||	true - new account created!
    ||	false - failed
    ||	Pass empty string for pw
    ||	Check return value of the method
    ||	pass valid id and pw 
    ||	Check return value of the method
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioTwoTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("", "");
		assertEquals(false, signUpResult);
		signUpResult = db.signUp("helenk@ucsd.edu", "");
		assertEquals(false, signUpResult);
		signUpResult = db.signUp("helenk@ucsd.edu", "hk12345");
		assertEquals(true, signUpResult);
		
	}
	
	/*+----------------------------------------------------------------------
	||
	||	Scenario 3: Existing account
	||	Given: Account email address “helenk@ucsd.edu” already exist
	||	When: Helen enters “helenk@ucsd.edu” in email address field, enters “hk12345” in password field and enters “hk12345” again in verify password field then presses the “Create Account” button.
	||	Then: Application shows the error message “Account already exists!”
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method same as our UI
    ||	Check return value of the method
    ||	true - new account created!
    ||	false - failed
    ||	reset database object and call signup method using same id
    ||	check return value of the method - should fail
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioThreeTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helen@ucsd.edu", "hk12345");
		assertEquals(true, signUpResult);
		
		db = new MockDataBase();
		signUpResult = db.signUp("helen@ucsd.edu", "hk12345");
		assertEquals(false, signUpResult);
	}
}