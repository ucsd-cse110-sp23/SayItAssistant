package sayItAssitantTests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sayItAssistant.api.ChatGpt;
import sayItAssistant.data.History;
import sayItAssistant.mocking.MockChatGpt;
import sayItAssistant.mocking.MockHistory;
import sayItAssistant.mocking.MockWhisper;

class StoryTestUS5 {

	/**
	 * @throws java.lang.Exception
	 * Create QuestionsForTest.txt file before each test
	 */
	@BeforeEach
	void setUp() throws Exception {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		FileWriter fw = new FileWriter(dbFile);
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
	||  Scenario 1: Select single question and answer from my history side bar
	||	Given the prompt history is displayed on the launch screen
	||	When Helen clicks on a previously asked question 
	||		“Q: What is a famous restaurant in La Jolla?” in the prompt history
	||	Then the same answer “A: Haidilao which is a hot pot place” to the 
	||		previously asked question pops up
	||	When Helen clicks on the “Delete” button
	||	Then the answer disappears from the prompt history and main screen
	||
	|+-----------------------------------------------------------------------
	||
	||  Initialize mockHistory, mockWhisper, mockChatGpt objects
	||	 because we will not be using real data
	||	ask question "What is a famous restaurant in La Jolla?"
	||	 and get answer using MockChatGpt object
	||	Check the size of data, and if the question and answer is properly
	||	 added
	||	Remove question using index 0
	||	The currently asked question should've been removed
	||	Check the size of data again
	||	Check remaining questions order
	||		-> index 0 must be replaced by index 1 and keep going
	||
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioOneTest(){
		History mockHistory = new MockHistory();
		MockWhisper mockWhisper = new MockWhisper();;
		mockWhisper.askQuestion("What is a famous restaurant in La Jolla?");
		mockWhisper.toTranscribe();
		ChatGpt mockChatGpt = new MockChatGpt("Haidilao which is a hot pot place");
		mockChatGpt.search();
		assertEquals(4, mockHistory.getHistory().size());
		assertEquals("What is a famous restaurant in La Jolla?",
				mockHistory.getHistory().get(0).getQuestionString());
		assertEquals("Haidilao which is a hot pot place",
				mockHistory.getHistory().get(0).getAnswerObject().getAnswerString());
		
		mockHistory.removeQuestion(0);
		assertEquals(3, mockHistory.getHistory().size());
		assertEquals("Question for Test1", mockHistory.getHistory().get(0).getQuestionString());
		assertEquals("Answer for Test1", mockHistory.getHistory().get(0).getAnswerObject().getAnswerString());
		assertEquals("Question for Test2", mockHistory.getHistory().get(1).getQuestionString());
		assertEquals("Answer for Test2", mockHistory.getHistory().get(1).getAnswerObject().getAnswerString());
		assertEquals("Question for Test3", mockHistory.getHistory().get(2).getQuestionString());
		assertEquals("Answer for Test3", mockHistory.getHistory().get(2).getAnswerObject().getAnswerString());
		
	}

}
