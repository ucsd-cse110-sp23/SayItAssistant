/**
 * 
 */
package test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.Test;
import sayltassistant.*;

class AnswerTest {

	/**
	 * Test method for {@link sayltassistant.Answer#Answer()}.
	 */
	@Test
	void testConstructor() {
		Answer constest1 = new Answer();
		assertEquals("", constest1.getAnswerString());
		Answer constest2 = new Answer("Sample answer for constructor test");
		assertEquals("Sample answer for constructor test", constest2.getAnswerString());
	}

	/**
	 * Test method for {@link sayltassistant.Answer#setAnswerString(java.lang.String)}.
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
