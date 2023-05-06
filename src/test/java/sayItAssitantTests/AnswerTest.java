/**
 * 
 */
package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sayItAssistant.Answer;

class AnswerTest {

	/**
	 * Test method for {@link main.java.Answer#Answer()}.
	 */
	@Test
	void testConstructor() {
		Answer constest1 = new Answer();
		assertEquals("", constest1.getAnswerString());
		Answer constest2 = new Answer("Sample answer for constructor test");
		assertEquals("Sample answer for constructor test", constest2.getAnswerString());
	}

	/**
	 * Test method for {@link main.java.Answer#setAnswerString(java.lang.String)}.
	 */
	@Test
	void testSetAnswerString() {
		Answer setterTest = new Answer();
		assertEquals("", setterTest.getAnswerString());
		setterTest.setAnswerString("Test No.1");
		assertEquals("Test No.1", setterTest.getAnswerString());
		setterTest.setAnswerString("String with\n new line charachter");
		assertEquals("String with\n new line charachter", setterTest.getAnswerString());
		setterTest.setAnswerString("");
		assertEquals("", setterTest.getAnswerString());
		setterTest.setAnswerString("String with speci@l charachter");
		assertEquals("String with speci@l charachter", setterTest.getAnswerString());
		setterTest.setAnswerString("All test passed!");
		assertEquals("All test passed!", setterTest.getAnswerString());
	}

}
