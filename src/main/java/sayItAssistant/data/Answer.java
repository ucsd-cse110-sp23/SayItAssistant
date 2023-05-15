package sayItAssistant.data;
/*+----------------------------------------------------------------------
||
||  Class Answer
||
||         Author:  Chanho Jeon
||
||        Purpose:  This class contains the answer for each question
||
|+-----------------------------------------------------------------------
||
||          Field:
||					answerString - the answer, String type
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					public Answer() - constructor without arguments
||					public Answer(String answerString)
||									- constructor with String argument
||
||  Class Methods:
||					Getter and Setter for answerString
||
++-----------------------------------------------------------------------*/
public class Answer {

	// The answer in String type
	private String answerString;

    /*---------------------------------------------------------------------
    |  Constructor Answer()
    |
    |         Purpose: Constructor for Answer object using no arguments
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize Answer Object with empty answerString
    |
    |      Parameters: None
    |
    |         Returns: Answer Object with empty answerString
    *-------------------------------------------------------------------*/
	public Answer() {
		answerString = "";
	}

    /*---------------------------------------------------------------------
    |  Constructor Answer(String answerString)
    |
    |         Purpose: Constructor for Answer object using
    |					String as an argument
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize Answer Object with answerString saved
    |
    |      Parameters: String answerString
    |
    |         Returns: answerString saved Answer Object
    *-------------------------------------------------------------------*/
	public Answer(String answerString) {
		this.answerString = answerString;
	}

    /*---------------------------------------------------------------------
    |  Method getAnswerString()
    |
    |         Purpose: Get answerString saved in Answer Object
    |
    |   Pre-condition: Initialized Answer object needed
    |
    |  Post-condition: None
    |
    |      Parameters: None
    |
    |         Returns: String saved in the object as "answerString"
    *-------------------------------------------------------------------*/
	public String getAnswerString() {
		return answerString;
	}

    /*---------------------------------------------------------------------
    |  Method setAnswerString(String answerString)
    |
    |         Purpose: set answerString in Answer Object
    |
    |   Pre-condition: Initialized Answer object needed
    |
    |  Post-condition: answerString of Answer object is changed
    |
    |      Parameters: String answerString
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}

}
