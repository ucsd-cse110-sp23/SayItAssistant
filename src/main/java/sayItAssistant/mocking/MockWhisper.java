package sayItAssistant.mocking;

import sayItAssistant.data.Answer;
import sayItAssistant.data.History;
import sayItAssistant.data.Question;
import sayItAssistant.functions.Whisper;

public class MockWhisper extends Whisper{

	String questionString;
	/*---------------------------------------------------------------------
  	|  Constructor Whisper()
	|	
	|         Purpose: constructor used for testing(mocking)
	|	
	|   Pre-condition: None
	|		
	|  Post-condition: Initialize Whisper object
	|	
	|      Parameters: none
	|	
	|         Returns: Whisper Object
	*-------------------------------------------------------------------*/
	
	public MockWhisper() {
		super();
	}
	/*---------------------------------------------------------------------
  	|  Method askQuestion()
	|
	|         Purpose: Ask question and save it to history
	|
	|   Pre-condition: Initialized Whisper object needed
	|
	|  Post-condition: None
	|
	|      Parameters: String questionString	
	|
	|         Returns: boolean
	*-------------------------------------------------------------------*/
	public boolean askQuestion(String questionString) {
		if(questionString == null) {return false;}
		this.questionString = questionString;
		return true;
	}
	/*---------------------------------------------------------------------
  	|  Method toTranscribe()
	|
	|         Purpose: Transcribe question and answer to history
	|
	|   Pre-condition: Initialized Whisper object needed
	|
	|  Post-condition: None
	|
	|      Parameters: none
	|
	|         Returns: none
	*-------------------------------------------------------------------*/
	@Override
	public Question toTranscribe() {
		History history = new MockHistory();
		history.addQuestion(new Question(questionString, new Answer("unanswered question")));
		return null; 
	}


}
