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

class DatabaseTest {

//	@BeforeEach
//	void setUp() throws Exception {
//		DataBase db = new MockDataBase();
//	}

	@AfterEach
	void tearDown() throws Exception {
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority")) {
            MongoDatabase db = mongoClient.getDatabase("JunitTest");
            db.drop();
        }
	}

	// Test if database creates new data when successfully signing up
	@Test
	void signUpSuccess() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		db = new MockDataBase();
		int logInResult = db.logIn("testId@ppp.com", "testpw");
		assertEquals(0, logInResult);
		assertEquals(0,db.getHistory().size());
	}
	
	// Test if it returns false if the id is already exist
	@Test
	void signUpFailed() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		db = new MockDataBase();
		signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(false, signUpResult);
	}
	
	// Test if it returns 0 when successfully logging in
	@Test
	void logInSuccess() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		db = new MockDataBase();
		
		int logInResult = db.logIn("testId@ppp.com", "testpw");
		assertEquals(0, logInResult);
		assertEquals(0, db.getHistory().size());
	}
	
	// Test if it return -1 when the id does not exist on database
	@Test
	void logInWrongId() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		db = new MockDataBase();
		
		int logInResult = db.logIn("tetId@ppp.com", "testpw");
		assertEquals(-1, logInResult);
	}
	
	// Test if it return 1 when the pw does not math on database
	@Test
	void logInWrongPw() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		db = new MockDataBase();
		
		int logInResult = db.logIn("testId@ppp.com", "testp");
		assertEquals(1, logInResult);
	}
	
	// Test if local question list is updated when the new question is asked
	@Test
	void addQuestionLocal() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		db.addQuestion(new Question("test question1", new Answer("test answer1")));
		db.addQuestion(new Question("test question2", new Answer("test answer2")));
		db.addQuestion(new Question("test question3", new Answer("test answer3")));
		assertEquals(3, db.getHistory().size());
		assertEquals("test question1", db.getHistory().get(2).getQuestionString());
		assertEquals("test answer1", db.getHistory().get(2).getAnswerObject().getAnswerString());
		assertEquals("test question2", db.getHistory().get(1).getQuestionString());
		assertEquals("test answer2", db.getHistory().get(1).getAnswerObject().getAnswerString());
		assertEquals("test question3", db.getHistory().get(0).getQuestionString());
		assertEquals("test answer3", db.getHistory().get(0).getAnswerObject().getAnswerString());
	}
	
	// Test if database question list is changed when the new question is asked
	@Test
	void addQuestionRemote() {
		DataBase db = new MockDataBase();
		boolean signUpResult = db.signUp("testId@ppp.com", "testpw");
		assertEquals(true, signUpResult);
		assertEquals(0,db.getHistory().size());
		db.addQuestion(new Question("test question1", new Answer("test answer1")));
		db.addQuestion(new Question("test question2", new Answer("test answer2")));
		db.addQuestion(new Question("test question3", new Answer("test answer3")));
		
		db = new MockDataBase();
		int logInResult = db.logIn("testId@ppp.com", "testpw");
		assertEquals(0, logInResult);
		assertEquals(3, db.getHistory().size());
		assertEquals("test question1", db.getHistory().get(2).getQuestionString());
		assertEquals("test answer1", db.getHistory().get(2).getAnswerObject().getAnswerString());
		assertEquals("test question2", db.getHistory().get(1).getQuestionString());
		assertEquals("test answer2", db.getHistory().get(1).getAnswerObject().getAnswerString());
		assertEquals("test question3", db.getHistory().get(0).getQuestionString());
		assertEquals("test answer3", db.getHistory().get(0).getAnswerObject().getAnswerString());
	}

}
