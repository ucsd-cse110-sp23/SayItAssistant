package sayItAssistant.mocking;

import sayItAssistant.Answer;
import sayItAssistant.History;
import sayItAssistant.Question;
import sayItAssistant.Whisper;

public class MockWhisper extends Whisper{

	String questionString;
	
	public MockWhisper() {
		super();
	}
	
	public boolean askQuestion(String questionString) {
		if(questionString == null) {return false;}
		this.questionString = questionString;
		return true;
	}

	@Override
	public void toTranscribe() {
		History history = new History(true);
		history.addQuestion(new Question(questionString, new Answer("unanswered question")),true);
	}
}
