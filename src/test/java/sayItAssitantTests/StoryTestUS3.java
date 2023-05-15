package sayItAssitantTests;
/**
 * JUnit Story Test for User Story3.
 * US3 - As a user, I want to ask my question out loud so that 
 * 			I can avoid having to type them
 * We are testing two BDD scenario at each test
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sayItAssistant.api.Whisper;
import sayItAssistant.data.History;
import sayItAssistant.mocking.MockHistory;
import sayItAssistant.mocking.MockWhisper;

class StoryTestUS3 {

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
	||  Scenario 1: Asking a New Question
	||	Given: Helen wants to ask “How did Louis Braille come up with the idea 
	||		for braille?” and her computer has a working mic.
	||	When Helen launches the  SayIt Assistant desktop application
	||	Then a “ “New Question” button” is displayed  on the launch screen 
	||	When Helen clicks the “New Question” button
	||	Then Helen says her question aloud as her mic is active
	||	When Helen finishes asking question	
	||	Then She clicks the “Stop recording” button
	||
	|+-----------------------------------------------------------------------
	||
	||  First test if the question data base is properly called
	||	by testing the size
	||	ask question using mocking class of Whisper
	||	Initialize MockWhisper because we are not using audio file and
	||	the real API
	||	Test if the size of data base is increased
	||	Test if the question is now in the index:0 of the history array
	||	Test if the question has corresponding answer "unanswered question"
	||	The story is part where user ask question so it should not have any
	||	answers
	||	Use MockHistory class, so the data base won't be changed by running the test
	||
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioOneTest() {
		History usTestHistory = new MockHistory();
		MockWhisper mockWhisper = new MockWhisper();
		assertEquals(3, usTestHistory.getHistory().size());
		
		mockWhisper.askQuestion("How did Louis Braille come up with the idea for braille?");
		mockWhisper.toTranscribe();
		assertEquals(4, usTestHistory.getHistory().size());
		assertEquals("How did Louis Braille come up with the idea for braille?",
				usTestHistory.getHistory().get(0).getQuestionString());
		assertEquals("unanswered question",
				usTestHistory.getHistory().get(0).getAnswerObject().getAnswerString());
		
	}
	
	/*+----------------------------------------------------------------------
	||
	||  Scenario 2: No mic detected 
	||  Given: Helen’s pc doesn’t have microphone connected  and  launches 
	||	the Saylt Assistant desktop application 
	||  When Helen clicks the “New Question” button
	||  Then A “No mic detected”  message appears
	||
	|+-----------------------------------------------------------------------
	||
	||  Opening app is shown by initializing History class because
	||	it contains field which is static
	||	Check if the previous questions are properly loaded
	||	askQuestion using mocking class, the audio file will give the null
	||	string since there is no mic
	||	askQuestion returns boolean value which is corresponding to 
	||	error in real whisper class
	||	check if the boolean value is false.
	||	Use MockHistory class, so the data base won't be changed by running the test
	||
	++-----------------------------------------------------------------------*/
	@Test
	void scenarioTwoTest() {
		History usTestHistory = new MockHistory();
		MockWhisper mockWhisper = new MockWhisper();
		assertEquals(3, usTestHistory.getHistory().size());
		
		boolean isMicDetected = mockWhisper.askQuestion(null);
		assertEquals(false, isMicDetected);
	}

}
