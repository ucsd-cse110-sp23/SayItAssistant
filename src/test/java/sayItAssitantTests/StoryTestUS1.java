package sayItAssitantTests;
/**
 * JUnit Story Test for User Story1.
 * US1 - As a user, I want to browse through the prompt history so that 
 * 			I can remember the questions I asked previously
 * We are testing two BDD scenario at each test
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import sayItAssistant.data.History;
import sayItAssistant.functions.ChatGpt;
import sayItAssistant.mocking.MockChatGpt;
import sayItAssistant.mocking.MockHistory;
import sayItAssistant.mocking.MockWhisper;

class StoryTestUS1 {


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
	||  Scenario 1: No previous history
	||	Given: Helen uses the app for the first time and has not
	||		asked any questions before
	||	When : Helen launches the  SayIt Assistant desktop application
	||	Then : the blank list is shown on the side-bar
	||
	|+-----------------------------------------------------------------------
	||
	||  Initialize MockHistory object 
	||	The data file is initially empty
	||	Compare its array size to 0
	||	0 means the list is empty so the side-bar will shown blank
	||  Failure of test means there are some elements which should not exist
	||
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioOneTest() {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(dbFile);
			fw.write("");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		History mockHistory = new MockHistory();
		assertEquals(0, mockHistory.getHistory().size());
	}

	/*+----------------------------------------------------------------------
	||
	||  Scenario 2: Browse through the prompt history
	||	Given: Helen had asked several questions
	||	When : launches the app
	||	Then : The prompt history is shown on the side-bar
	||	When : Helen scrolls through the prompt history
	||	Then : Older prompts are revealed 
	||
	|+-----------------------------------------------------------------------
	||
	||  Initialize History object with empty data file 
	||	Manually write down questions and answers
	||	Use mockWhisper and mockChatGPT to ask question and get answer
	||	Initialize new History object
	||		- represents Helen launches the app after asking several questions
	||  Check if previous questions and answers are in data base
	||
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioTwoTest() {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(dbFile);
			fw.write("");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		History mockHistory = new MockHistory();
		assertEquals(0, mockHistory.getHistory().size());
		
		// asked several question
		try {
			fw = new FileWriter(dbFile);
			fw.write("Question for Test1\n");
			fw.flush();
			fw.write("Answer for Test1\n");
			fw.flush();
			fw.write("Question for Test2\n");
			fw.flush();
			fw.write("Answer for Test2\n");
			fw.write("Question for Test3\n");
			fw.flush();
			fw.write("Answer for Test3\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MockWhisper mockWhisper = new MockWhisper();
		ChatGpt mockChatGpt = new MockChatGpt();
		mockWhisper.askQuestion("How did Louis Braille come up with the idea for braille?");
		mockWhisper.toTranscribe();
		mockChatGpt.search();
		assertEquals(4, mockHistory.getHistory().size());

		// restart the application
		History testHistory = new MockHistory();
		assertEquals(4, testHistory.getHistory().size());
		assertEquals("Louis Braille came up with the idea for braille after an accident blinded him at the age of three.",
				testHistory.getHistory().get(0).getAnswerObject().getAnswerString());
	}
	
}
