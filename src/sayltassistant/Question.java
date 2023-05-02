package sayltassistant;

public class Question {
	
	private String questionString;
	private Answer answerObject;
	
	public Question() {
		questionString = "";
		answerObject = new Answer();
	}
	
	public Question(String questionString, Answer answerObject) {
		super();
		this.questionString = questionString;
		this.answerObject = answerObject;
	}

	public String getQuestionString() {
		return questionString;
	}

	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

	public Answer getAnswerObject() {
		return answerObject;
	}

	public void setAnswerObject(Answer answerObject) {
		this.answerObject = answerObject;
	}
	
}
