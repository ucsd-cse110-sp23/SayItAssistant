package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.*;

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

class StoryTestUS12 {

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
	||	Scenario 1: Asking a New Question
	||	Given: Helen wants to ask “What caused the fall of the Roman Empire?” and her CSE lab computer has a working mic.
	||	When: Helen launches the SayIt Assistant 2 application
	||	Then: “Start” button is displayed on the launch screen
	||	When: Helen clicks the “Start” button
	||	Then: Helen says her question “Question. What caused the fall of the Roman Empire?”
	||	When: Helen finishes asking question
	||	Then: She clicks the “Stop recording” button
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method same as our UI
    ||	Check return value of the method
    ||	check if its question list is emptied initially
    ||	Ask question by calling addQuestion method
    ||	Check if the local history list contains the same question
    ||	Check if the remote database contains the same question
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioOneTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helen@ucsd.edu", "hk12345");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		
		boolean success = db.addQuestion(new Question("What caused the fall of the Roman Empire?", new Answer("The fall of the Roman Empire was a complex and multifaceted event, influenced by a combination of internal and external factors over several centuries.")));
		assertEquals(true, success);
		assertEquals("What caused the fall of the Roman Empire?", db.getHistory().get(0).getQuestionString());
		
		db = new MockDataBase();
		db.logIn("helen@ucsd.edu", "hk12345");
		assertEquals("What caused the fall of the Roman Empire?", db.getHistory().get(0).getQuestionString());
	}
	
	/*+----------------------------------------------------------------------
	||
	||	Scenario w: Asking Question Failed
	||	Given: Helen clicks the “Start” button
	||	When: Helen says nothing after clicking the “Start” button
	||	Then: A “No question detected” message appears
	||
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method same as our UI
    ||	add empty question as mic was not detected
    ||	Check if the adding question was failed
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioTwoTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helen@ucsd.edu", "hk12345");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		
		boolean success = db.addQuestion(new Question("", new Answer("")));
		assertEquals(false, success);
	}
}