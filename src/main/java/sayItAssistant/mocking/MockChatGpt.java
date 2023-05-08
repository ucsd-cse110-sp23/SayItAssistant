package sayItAssistant.mocking;

import sayItAssistant.ChatGpt;
import sayItAssistant.History;

public class MockChatGpt extends ChatGpt{
	History history;
	
	public MockChatGpt() {
		history = new History(true);
	}
}
