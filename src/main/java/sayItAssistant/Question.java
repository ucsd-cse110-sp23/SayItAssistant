package sayItAssistant;

/*+----------------------------------------------------------------------
||
||  Class Answer
||
||         Author:  Chanho Jeon
||
||        Purpose:  This class contains question and its answer
||
|+-----------------------------------------------------------------------
||
||          Field:
||					questionString - the question, String type
||					answerObject - its answer, Answer object type
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					public Question() - constructor without arguments
||					public Question(String questionString)
||									- constructor with String argument
||
||  Class Methods:
||					Getters and Setters for questionString and answerObject
||
++-----------------------------------------------------------------------*/
public class Question {

	// the question in String type
	private String questionString;
	// Answer object. The answer will be saved in the object as String
	private Answer answerObject;

    /*---------------------------------------------------------------------
    |  Constructor Question()
    |
    |         Purpose: Constructor for Question object using no arguments
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize Question Object with empty string
    |					and new Answer object
    |
    |      Parameters: None
    |
    |         Returns: Question object with empty questionString
    |					and new Answer object
    *-------------------------------------------------------------------*/
	public Question() {
		questionString = "";
		answerObject = new Answer();
	}

    /*---------------------------------------------------------------------
    |  Constructor Question(String questionString, Answer answerObject)
    |
    |         Purpose: Constructor for Question object using arguments
    |
    |   Pre-condition: initialized Answer object needed
    |
    |  Post-condition: Initialize Question Object, questionString and
    |					Answer object saved
    |
    |      Parameters:  String questionString
    |					Answer answerObject
    |
    |         Returns: Question object which passed arguments are saved
    *-------------------------------------------------------------------*/
	public Question(String questionString, Answer answerObject) {
		super();
		this.questionString = questionString;
		this.answerObject = answerObject;
	}

    /*---------------------------------------------------------------------
    |  Method getQuestionString()
    |
    |         Purpose: Get questionString saved in Question Object
    |
    |   Pre-condition: Initialized Question object needed
    |
    |  Post-condition: None
    |
    |      Parameters: None
    |
    |         Returns: String saved in the object as "questionString"
    *-------------------------------------------------------------------*/
	public String getQuestionString() {
		return questionString;
	}

    /*---------------------------------------------------------------------
    |  Method setQuestionString(String questionString)
    |
    |         Purpose: set questionString in Question Object
    |
    |   Pre-condition: Initialized Question object needed
    |
    |  Post-condition: questionString of Question object is changed
    |
    |      Parameters: String questionString
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

    /*---------------------------------------------------------------------
    |  Method getAnswerObject()
    |
    |         Purpose: Get Answer Object saved in Question Object
    |
    |   Pre-condition: Initialized Question object needed
    |
    |  Post-condition: None
    |
    |      Parameters: None
    |
    |         Returns: Answer object saved as "answerObject"
    *-------------------------------------------------------------------*/
	public Answer getAnswerObject() {
		return answerObject;
	}

    /*---------------------------------------------------------------------
    |  Method setAnswerObject(Answer answerObject)
    |
    |         Purpose: set Answer Object in Question Object
    |
    |   Pre-condition: Initialized Question object needed
    |
    |  Post-condition: answerObject is changed
    |
    |      Parameters: Answer answerObject
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
	public void setAnswerObject(Answer answerObject) {
		this.answerObject = answerObject;
	}

}
