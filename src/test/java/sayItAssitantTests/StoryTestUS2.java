package sayItAssitantTests;

/**
 * JUnit Story Test for User Story2.
 * US2 - As a user, I want to view previous answers so that I can look for specific question’s answer whenever I want.
 * 
 * Scenario 1: View previous answers
 * Given: Helen scrolled through the prompt history
 * When Helen clicks on a previously asked question “Who is Joe Biden?” in the prompt history
 * Then “Who is Joe Biden?” “Joe Biden is the President of the United States.” is displayed on the main screen. 
 * We are testing one BDD scenario.
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

public class StoryTestUS2 {
    /**
	 * @throws java.lang.Exception
	 * Create QuestionsForTest.txt file before the test
	 */
	@BeforeEach
    void setUp() throws Exception {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		FileWriter fw = new FileWriter(dbFile);
		fw.write("Question for Test1\n");
		fw.flush();
		fw.write("Answer for Test1\n");
        fw.write("Who is Joe Biden?\n");
		fw.flush();
		fw.write("Joe Biden is the President of the United States.\n");
		fw.flush();
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
    ||  Scenario 1: View previous answers
	||	Given: Helen scrolled through the prompt history
	||	When Helen clicks on a previously asked question “Who is Joe Biden?” in the prompt history
    ||  Then “Who is Joe Biden?” “Joe Biden is the President of the United States” is displayed on the main screen.
	||	
	|+-----------------------------------------------------------------------
	||
	||  First test if the question data base is properly called
	||	by testing the size
    	Since the question is already asked, test index:1 of history
	||	if it contains the right question
        Test if the answer of the question is the correct corresponding answer 
	||	
	++-----------------------------------------------------------------------*/
    @Test
	void scenarioOneTest() {
        History usTestHistory = new MockHistory();
		assertEquals(3, usTestHistory.getHistory().size());
		assertEquals("Who is Joe Biden?",
				usTestHistory.getHistory().get(1).getQuestionString());
		assertEquals("Joe Biden is the President of the United States.",
				usTestHistory.getHistory().get(1).getAnswerObject().getAnswerString());	

        // restart the application
		History freshTestHistory = new MockHistory();
        assertEquals(3, freshTestHistory.getHistory().size());
		assertEquals("Who is Joe Biden?",
                freshTestHistory.getHistory().get(1).getQuestionString());
		assertEquals("Joe Biden is the President of the United States.",
                freshTestHistory.getHistory().get(1).getAnswerObject().getAnswerString());	
                
	}
    

    
}
