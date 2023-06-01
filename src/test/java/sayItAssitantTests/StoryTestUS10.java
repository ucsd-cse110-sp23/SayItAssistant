package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import sayItAssistant.data.Answer;
import sayItAssistant.data.DataBase;
import sayItAssistant.data.Question;
import sayItAssistant.mocking.MockDataBase;

class StoryTestUS10 {
	
	@BeforeEach
	void setUp() throws Exception{
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helenk@ucsd.edu", "hk12345");
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
	||	Scenario 1: Logging In By Inputting Correct Email and Password
	||	Given: Helen has an account for the application created:  Email: “helenk@ucsd.edu”
	||	Password: “hk12345”
	||	When:  Helen opens up the app on a public computer
	||	Then: She is prompted to login by entering her email address and password
	||	When: Helen inputs her correct email and password
	||	Then: Login success
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method in @BeforeEach
    ||	call logIn method using valid id and password
    ||	Check the result:
    ||		-1:wrong id
    ||		0: login success
    ||		1: wrong pw
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioOneTest() {
		DataBase db = new MockDataBase();
		int result = db.logIn("helenk@ucsd.edu", "hk12345");
		assertEquals(0, result);
	}
	
	/*+----------------------------------------------------------------------
	||
	||	Scenario 2: Login Attempt with  Incorrect Password
	||	Given: Helen has an account for the application created:  Email: “helenk@ucsd.edu” 
	||	Password: “hk12345”
	||	When:  Helen opens up the app on a public computer
	||	Then: She is prompted to login by entering her email address, and password
	||	When: Helen inputs her correct email, “helenk@ucsd.edu”, and incorrect password, “hj12345”
	||	Then: An “Incorrect Password” message is displayed
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method in @BeforeEach
    ||	call logIn method using valid id and wrong password
    ||	Check the result:
    ||		-1:wrong id
    ||		0: login success
    ||		1: wrong pw
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioTwoTest() {
		DataBase db = new MockDataBase();
		int result = db.logIn("helenk@ucsd.edu", "hj12345");
		assertEquals(1, result);
	}
	
	/*+----------------------------------------------------------------------
	||
	||	Scenario 3:  Login Attempt with  Incorrect Email 
	||	Given: Helen has an account for the application created and  the app is opened:  Email: “helenk@ucsd.edu” Password: “hk12345”
	||	When:  Helen opens up the app on a public computer
	||	Then: She is prompted to login by entering her email address and password
	||	When: Helen inputs her incorrect email, “melenkl@ucsd.edu”,and correct password, “hk12345”
	||	Then: An “Account not Found” message is displayed
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method in @BeforeEach
    ||	call logIn method using wrong id and correct password
    ||	Check the result:
    ||		-1:wrong id
    ||		0: login success
    ||		1: wrong pw
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioThreeTest() {
		DataBase db = new MockDataBase();
		int result = db.logIn("melenk@ucsd.edu", "hk12345");
		assertEquals(-1, result);
	}
}
