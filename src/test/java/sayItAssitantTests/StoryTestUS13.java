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
public class StoryTestUS13 {

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
	||	Scenario 1: Select single question and answer from my history side bar to delete
	||	Given: The prompt history is displayed on the launch screen
	||	When: Helen clicks on a previously asked question “Question. What is a famous restaurant in La Jolla?” in the prompt history
	||	Then: The previously asked question with command “Question. What is a famous restaurant in La Jolla?” and answer “Haidilao which is a hot pot place” to the previously asked shows up.
	||	When: Helen clicks on the “Start” button and says “Delete prompt”
	||	Then: Currently selected question and answer disappears from the prompt history and the main screen.
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method same as our UI
    ||	Check return value of the method
    ||	Ask question by calling addQuestion method
	||  Ensure the size of history list is equal to 1 after adding the question
	||  Delete question by calling removeQuestion method 
    ||  Ensure the question list is now empty or not
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioOneTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helenk@ucsd.edu","hk12345");
		assertEquals(true, signUpResult);

		boolean success = db.addQuestion(new Question("What is a famous restaurant in La Jolla?", 
		new Answer("Haidilao which is a hot pot place")));
		assertEquals(true, success);
		assertEquals(1, db.getHistory().size());

		db.removeQuestion(0);
		assertEquals(0, db.getHistory().size());
	}

	/*+----------------------------------------------------------------------
	||
	||	Scenario 2: Select single question and answer from my history side bar that has multple questions
	||	Given: The prompt history is displayed on the launch screen
	||	When: Helen clicks on a previously asked question “Question. What is a famous restaurant in La Jolla?” in the prompt history
	||	Then: The previously asked question with command “Question. What is a famous restaurant in La Jolla?” and answer “Haidilao which is a hot pot place” to the previously asked shows up.
	||	When: Helen clicks on the “Start” button and says “Delete prompt”
	||	Then: Currently selected question and answer disappears from the prompt history and the main screen while the other questions remain unchanged. 
	|+-----------------------------------------------------------------------
	||
    ||  First initialize DataBase object and call signUp method same as our UI
    ||	Check return value of the method
    ||	Ask question by calling addQuestion method
	||  Ensure the size of history list is equal to 1 after adding the question
	||  Ask question by calling addQuestion method
	||  Ensure the size of history list is equal to 2 after adding the question
	||  Ask question by calling addQuestion method
	||  Ensure the size of history list is equal to 3 after adding the question
	||  Delete question by calling removeQuestion method 
    ||  Ensure the question list size is now 2
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioTwoTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helenk@ucsd.edu","hk12345");
		assertEquals(true, signUpResult);

		
		boolean success = db.addQuestion(new Question("What caused the fall of the Roman Empire?", 
		 new Answer("The fall of the Roman Empire was a complex and multifaceted event,influenced by a combination of internal and external factors over several centuries.")));
		assertEquals(true, success);
		assertEquals(1, db.getHistory().size());

		boolean success2 = db.addQuestion(new Question("What is a famous restaurant in La Jolla?", 
		new Answer("Haidilao which is a hot pot place")));
		assertEquals(true, success2);
		assertEquals(2, db.getHistory().size());



		boolean success3 = db.addQuestion(new Question("What is icecream?", 
		new Answer("Ice cream is a frozen dessert.")));
		assertEquals(true, success3);
		assertEquals(3, db.getHistory().size());

		db.removeQuestion(1);
		assertEquals(2, db.getHistory().size());
	}
}
