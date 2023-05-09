package sayItAssitantTests;
/**
 * JUnit Story Test for User Story4.
 * As a user, I want to view the results of my current 
 * 		questions so that I can read through it 
 * We are testing one BDD scenario
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sayItAssistant.ChatGpt;
import sayItAssistant.History;
import sayItAssistant.mocking.MockChatGpt;
import sayItAssistant.mocking.MockHistory;

class StoryTestUS4 {

	
	/**
	 * @throws java.lang.Exception
	 * Create QuestionsForTest.txt file before each test
	 */
	@BeforeEach
	void setUp() throws Exception {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		FileWriter fw = new FileWriter(dbFile);
		fw.write("How did Louis Braille come up with the idea for braille?\n");
		fw.flush();
		fw.write("unanswered question\n");
		fw.flush();
		fw.write("Question for Test2\n");
		fw.flush();
		fw.write("Answer for Test2\n");
		fw.write("Question for Test3\n");
		fw.flush();
		fw.write("Answer for Test3\n");
		fw.flush();
		fw.close();
	}

	/**
	 * @throws java.lang.Exception
	 * Delete QuestionForTest.txt file after each test
	 */
	@AfterEach
	void tearDown() throws Exception {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		dbFile.delete();
	}
	

	/*+----------------------------------------------------------------------
	||
	||  Scenario 1: Viewing the answer of current question
	||  Given: Helen finished asking question: "How did Louis Braille 
	||			come up with the idea for braille?"
	||  When: Helen clicks the stop recording button
	||  Then: The question and the answer("Louis Braille came up with the 
	||			idea for braille after an accident blinded him at the age of three.") 
	||			appears on the main screen
	||
	|+-----------------------------------------------------------------------
	||
	||  First test if the question data base is properly called
	||	by testing the size
	||	Since the question is already asked, test index:0 of history
	||	if it contains the right question
	||	Test if the answer of the question is currently "unanswered question"
	||	Initialize MockChatGpt because we are not the real API
	||	Run mock method in MockChatGpt
	||	Test if the answer is changed to the right answer string
	||	Use MockHistory class, so the data base won't be changed by running the test
	||
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioOneTest() {
		History usTestHistory = new MockHistory();
		ChatGpt mockChatGpt = new MockChatGpt();
		assertEquals(3, usTestHistory.getHistory().size());
		assertEquals("How did Louis Braille come up with the idea for braille?",
				usTestHistory.getHistory().get(0).getQuestionString());
		assertEquals("unanswered question",
				usTestHistory.getHistory().get(0).getAnswerObject().getAnswerString());
		
		mockChatGpt.search();
		assertEquals("Louis Braille came up with the idea for braille after an accident blinded him at the age of three.",
				usTestHistory.getHistory().get(0).getAnswerObject().getAnswerString());
		
	}

}
