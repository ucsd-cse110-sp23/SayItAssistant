package sayItAssistant.mocking;

import sayItAssistant.ChatGpt;
import sayItAssistant.History;

public class MockChatGpt extends ChatGpt{
	History history;
	String questionString;
	String answerString = "Louis Braille came up with the idea for braille after an accident blinded him at the age of three.";
	
	public MockChatGpt() {
		history = new MockHistory();
	}
	
	public MockChatGpt(String answerString) {
		history = new MockHistory();
		this.answerString = answerString;
	}
	
	private void setRequest() {
		questionString = history.getHistory().get(0).getQuestionString();
	}
	
	private void setResponse() {
		history.getHistory().get(0).getAnswerObject().setAnswerString(answerString);
	}
	
	@Override
	public void search() {
		setRequest();
		setResponse();
		history.saveHistory();
	}
}
