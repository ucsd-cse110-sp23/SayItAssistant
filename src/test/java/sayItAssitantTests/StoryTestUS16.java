/*Creating Email [Iteration 2][Mid] [8]
As a user, I want to use the Create Email voice command so that I can create an email

Scenario1: Create an email prompt after setting up email
Given: SayItAssistant2 is opened showing the main screen 
		When: Helen clicks “Start” button and says “Create an email to Jill let’s meet at Geisel 
for our 7 pm study session”
			session” is shown and at the bottom of the email “Best Regards, HelloHellen” is
			displayed

Scenario2: Create an email prompt before setting up email
Given: SayItAssistant2 is opened showing the main screen 
		When: Helen clicks “Start” button and says “Create an email to Jill let’s meet at Geisel 
for our 7 pm study session”
		Then: In the main screen, “Create an email to Jill let’s meet at Geisel for out 7 pm study
			session” is shown and at the bottom of the email “Best Regards, ______ ” with 
			blank display name
 */
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

public class StoryTestUS16 {

	@AfterEach
	void tearDown() throws Exception {
				try (MongoClient mongoClient = MongoClients.create("mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?"
						+ "retryWrites=true&w=majority")) {
						MongoDatabase db = mongoClient.getDatabase("JunitTest");
						db.drop();
				}
	}

	/*+----------------------------------------------------------------------
	||
	||	Scenario 1: Create an email prompt after setting up email
	||	Given: SayItAssistant2 is opened showing the main screen
	||	When: Helen clicks “Start” button and says “Create an email to Jill let’s meet at Geisel
	||	for our 7 pm study session”
	||	Then: In the main screen, “Create an email to Jill let’s meet at Geisel for out 7 pm study
	||		session” is shown and at the bottom of the email “Best Regards, HelloHellen” is
	||		displayed
	||
	|+-----------------------------------------------------------------------*/

	@Test
	void scenarioOneTest() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helenk@ucsd.edu", "hk12345");
		assertEquals(true, signUpResult);
		}	
  
}
