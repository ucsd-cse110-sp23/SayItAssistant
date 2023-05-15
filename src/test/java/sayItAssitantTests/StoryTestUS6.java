package sayItAssitantTests;
/**
 * JUnit Story Test for User Story6.
 * As a user, I want to delete all the questions 
 * and answers from my history so that I can reset all 
 * We are testing one BDD scenario
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sayItAssistant.History;
import sayItAssistant.mocking.MockHistory;



public class StoryTestUS6 { 
    /**
     * @throws java.lang.Exception
     * Create QuestionsForTest.txt file before each test //
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
	||  Scenario: Delete all questions from the prompt history
    ||  Given: There is a ‘clear’ button 
    ||  When: Helen clicks the ‘clear’ button
    ||  Then: All the questions (and by extension, their answers) will be cleared from the prompt history
	||
	|+-----------------------------------------------------------------------
	||
    ||  First we initialize a MockHistory object and a MockChatGpt object
    ||  Since the question is already in the database, we compare the size of the array to 3
    ||  3 means the list is not empty so the side-bar will shown the list
    ||  We then clear the history and compare the size of the array to 0
    ||  0 means the list is empty so the side-bar will shown blank
    ||  Failure of test means there are some elements which should not exist
    ||  
	++-----------------------------------------------------------------------*/
	@Test
    void scenarioOneTest() {
        History mockHistory = new MockHistory();  
        assertEquals(3, mockHistory.getHistory().size());
        mockHistory.clearHistory();
        assertEquals(0, mockHistory.getHistory().size());
    }
}


