/**
 *
 */
package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import sayItAssistant.data.Answer;
import sayItAssistant.data.Question;

class QuestionTest {

	/**
	 * Test method for {@link main.java.Question#Question()}.
	 */
	@Test
	void testQuestion() {
		Question consTest1 = new Question();
		assertEquals("", consTest1.getQuestionString());
		assertEquals("", consTest1.getAnswerObject().getAnswerString());
		Question consTest2 = new Question("Constructor Test", new Answer("It's working!"));
		assertEquals("Constructor Test", consTest2.getQuestionString());
		assertEquals("It's working!", consTest2.getAnswerObject().getAnswerString());
	}

	/**
	 * Test method for {@link main.java.Question#setQuestionString(java.lang.String)}.
	 */
	@Test
	void testSetQuestionString() {
		Question questionStringSetterTest = new Question();
		assertEquals("", questionStringSetterTest.getQuestionString());
		questionStringSetterTest.setQuestionString("Test No.1");
		assertEquals("Test No.1", questionStringSetterTest.getQuestionString());
		questionStringSetterTest.setQuestionString("String with\\n new line charachter");
		assertEquals("String with\\n new line charachter", questionStringSetterTest.getQuestionString());
		questionStringSetterTest.setQuestionString("");
		assertEquals("", questionStringSetterTest.getQuestionString());
		questionStringSetterTest.setQuestionString("String with speci@l charachter");
		assertEquals("String with speci@l charachter", questionStringSetterTest.getQuestionString());
	}

	/**
	 * Test method for {@link main.java.Question#setAnswerObject(main.java.Answer)}.
	 */
	@Test
	void testSetAnswerObject() {
		Question answerObjectSetterTest = new Question();
		assertEquals("", answerObjectSetterTest.getAnswerObject().getAnswerString());
		answerObjectSetterTest.setAnswerObject(new Answer("Using constructor with argument"));
		assertEquals("Using constructor with argument", answerObjectSetterTest.getAnswerObject().getAnswerString());
		answerObjectSetterTest.setAnswerObject(new Answer("String with\\\\n new line charachter"));
		assertEquals("String with\\\\n new line charachter", answerObjectSetterTest.getAnswerObject().getAnswerString());
		answerObjectSetterTest.setAnswerObject(new Answer("String with speci@l charachter"));
		assertEquals("String with speci@l charachter", answerObjectSetterTest.getAnswerObject().getAnswerString());
	}

}
