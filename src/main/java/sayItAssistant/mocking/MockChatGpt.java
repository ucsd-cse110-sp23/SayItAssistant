package sayItAssistant.mocking;

import sayItAssistant.ChatGpt;
import sayItAssistant.History;

public class MockChatGpt extends ChatGpt{
	History history;
	String questionString;
	String answerString = "Louis Braille came up with the idea for braille after an accident blinded him at the age of three.";
	
	/*---------------------------------------------------------------------
  	|  Constructor ChatGpt()
	|	
	|         Purpose: constructor used for testing(mocking)
	|
	|   Pre-condition: None
	|
	|  Post-condition: Initialize ChatGpt object
	|
	|      Parameters: none
	|
	|         Returns: ChatGpt Object
	*-------------------------------------------------------------------*/

	public MockChatGpt() {
		history = new MockHistory();
	}
	/*---------------------------------------------------------------------
  	|  Method MockChatGpt()
	|
	|         Purpose: constructor used for testing(mocking)
	|
	|   Pre-condition: None
	|
	|  Post-condition: Initialize ChatGpt object
	|
	|      Parameters: String answerString
	|
	|         Returns: ChatGpt Object
	*-------------------------------------------------------------------*/

	public MockChatGpt(String answerString) {
		history = new MockHistory();
		this.answerString = answerString;
	}
	
	/*---------------------------------------------------------------------
	|  Method setRequest()
	|
	|         Purpose: Set questionString to the first question in history
	|
	|   Pre-condition: Initialized ChatGpt object needed
	|
	|  Post-condition: None
	|
	|      Parameters: none
	|
	|         Returns: none
	*-------------------------------------------------------------------*/
	private void setRequest() {
		questionString = history.getHistory().get(0).getQuestionString();
	}
	
	/*---------------------------------------------------------------------
	|  Method setResponse()
	|
	|         Purpose: Set answerString to the first answer in history
	|
	|   Pre-condition: Initialized ChatGpt object needed
	|
	|  Post-condition: None
	|
	|      Parameters: none
	|
	|         Returns: none
	*-------------------------------------------------------------------*/
	private void setResponse() {
		history.getHistory().get(0).getAnswerObject().setAnswerString(answerString);
	}
	
	/*---------------------------------------------------------------------
	|  Method search()
	|
	|         Purpose: Search for answer to questionString
	|
	|   Pre-condition: Initialized ChatGpt object needed
	|
	|  Post-condition: None
	|
	|      Parameters: none
	|
	|         Returns: none
	*-------------------------------------------------------------------*/
	@Override
	public void search() {
		setRequest();
		setResponse();
		history.saveHistory();
	}
}