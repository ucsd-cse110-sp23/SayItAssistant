package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import sayItAssistant.components.QAScreen;
import sayItAssistant.data.Answer;
import sayItAssistant.data.DataBase;
import sayItAssistant.data.Question;
import sayItAssistant.mocking.MockDataBase;

public class StoryTestUS16 {

	@BeforeEach
    void setUp() throws Exception {
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority")) {
            MongoDatabase db = mongoClient.getDatabase("JunitTest");
            db.drop();
        }
    }

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
	||	When: Helen clicks “Start” button and says “Create email to Jill letlr’s meet at Geisel
	||	for our 7 pm study session”
	||	Then: In the main screen, “Create email to Jill let’s meet at Geisel for out 7 pm study
	||		session” is shown and at the bottom of the email “HelloHellen” is
	||		displayed
	||
	|+-----------------------------------------------------------------------
	||
	||	First initialize DataBase object and call signUp method same as our UI
	||	This sets up the user's email account
	||	Then create the email content and sender name
	||	Then add the question to the database
	||  Then create the QAScreen object
	||  Verify that the expected email content is equal to the output string with sender name
	||
	++-----------------------------------------------------------------------*/

	@Test
	void scenarioOne(){
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helenk@ucsd.edu","hk12345");
		assertEquals(true, signUpResult);

		String email = "Create email to Jill let’s meet at Geisel for our 7 pm study session";
		String sender = "HelloHellen";
		
		Question question = new Question();
		question.setQuestionString(email);
		Answer answer = new Answer();
		answer.setAnswerString(sender);
		question.setAnswerObject(answer);

		db.addQuestion(question);
		QAScreen qaScreen = new QAScreen();

		String outputString = QAScreen.QAText.getText();
		assertEquals("Create email to Jill let’s meet at Geisel for our 7 pm study session", outputString);
	}

	/*+----------------------------------------------------------------------
	||
	||	Scenario 2: Create an email prompt before setting up email
	||	Given: SayItAssistant2 is opened showing the main screen
	||	When: Helen clicks “Start” button and says “Create an email to Jill let’s meet at Geisel
	||	for our 7 pm study session”
	||	Then: In the main screen, “Create an email to Jill let’s meet at Geisel for out 7 pm study
	||		session” is shown and at the bottom of the email “ ______ ” with
	||		blank display name
	||
	|+-----------------------------------------------------------------------
	||
	||	First initialize DataBase object and call signUp method same as our UI
	||	This sets up the user's email account
	||	Then create the email content without sender name
	||	Then add the question to the database
	||  Then create the QAScreen object
	||  Verify that the expected email content is equal to the output string with blank sender name
	||
	++-----------------------------------------------------------------------*/

	@Test
	void scenarioTwo(){
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("helenk@ucsd.edu","hk12345");
		assertEquals(true, signUpResult);

		String email = "Create email to Jill let’s meet at Geisel for our 7 pm study session";
		String sender = " ______ ";

		Question question = new Question();
		question.setQuestionString(email);
		Answer answer = new Answer();
		answer.setAnswerString(sender);
		question.setAnswerObject(answer);

		db.addQuestion(question);
		QAScreen qaScreen = new QAScreen();

		String outputString = QAScreen.QAText.getText();
		assertEquals("Create email to Jill let’s meet at Geisel for our 7 pm study session", outputString);
	}



}








