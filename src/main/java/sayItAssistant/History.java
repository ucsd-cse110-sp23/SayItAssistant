package sayItAssistant;
/*+----------------------------------------------------------------------
||
||  Class History
||
||         Author:  Chanho Jeon
||
||        Purpose:  This class contains list of Question objects
||
|+-----------------------------------------------------------------------
||
||          Field:
||					dbPath: path to the text file
||					testDbPath: path to the testing text file
||					history: arraylist containing Question object
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					History()- default constructor
||					 
||
||  Class Methods:				
||					getHistory() - return history arrayList
||					addQuestion() - add question object into arrayList
||					removeQuestion() - remove object from arrayList using index
||					saveHistory() - save arraylist in text file	
||
++-----------------------------------------------------------------------*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class History {

	private final String dbPath = "./DB/Questions.txt";
	private final String testDbPath = "./DB/QuestionsForTest.txt";
	private static ArrayList<Question> history;

    /*---------------------------------------------------------------------
    |  Constructor History()
    |
    |         Purpose: default constructor
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize History object containing arrayList
    |					Question and answer is loaded from text file
    |
    |      Parameters: None
    |
    |         Returns: History Object
    *-------------------------------------------------------------------*/
	public History() {this(false);}

    /*---------------------------------------------------------------------
    |  Constructor History(boolean test)
    |
    |         Purpose: constructor used for testing
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize History object containing arrayList
    |					Question and answer is loaded from text file
    |
    |      Parameters: test - set it to true for testing
    |
    |         Returns: History Object
    *-------------------------------------------------------------------*/
	public History(boolean test) {
		File dbFile;
		if(!test) {
			dbFile = new File(dbPath);
		} else {
			dbFile = new File(testDbPath);
		}
		history = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(dbFile))) {
			String questionString;
			String answerString;
			while((questionString=br.readLine()) != null) {
				Question tempQuestion= new Question();
				tempQuestion.setQuestionString(questionString);
				answerString = br.readLine();
				tempQuestion.setAnswerObject(new Answer(answerString));
				history.add(tempQuestion);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /*---------------------------------------------------------------------
    |  Method getHistory()
    |
    |         Purpose: Get arraylist saved in history Object
    |
    |   Pre-condition: Initialized history object needed
    |
    |  Post-condition: None
    |
    |      Parameters: None
    |
    |         Returns: arrayList containing Question objects
    *-------------------------------------------------------------------*/
	public ArrayList<Question> getHistory() {
		return history;
	}

    /*---------------------------------------------------------------------
    |  Method addQuestion(Question question)
    |
    |         Purpose: add Question object at index 0(front)
    |
    |   Pre-condition: Initialized history object needed
    |
    |  Post-condition: new Question object inserted at the front
    |
    |      Parameters: question
    |
    |         Returns: none
    *-------------------------------------------------------------------*/
	public void addQuestion(Question question) {addQuestion(question,false);}

    /*---------------------------------------------------------------------
    |  Method addQuestion(Question question, boolean test)
    |
    |         Purpose: add Question object at index 0(front)
    |
    |   Pre-condition: Initialized history object needed
    |
    |  Post-condition: new Question object inserted at the front
    |
    |      Parameters: question, test(set true for testing)
    |
    |         Returns: none
    *-------------------------------------------------------------------*/
	public void addQuestion(Question question, boolean test) {
		ArrayList<Question> addedHistory = new ArrayList<>();
		addedHistory.add(question);
		for (Question q : history) {
			addedHistory.add(q);
		}
		history = addedHistory;
		saveHistory(test);
	}

    /*---------------------------------------------------------------------
    |  Method removeQuestion(int index)
    |
    |         Purpose: remove one Question object from list
    |
    |   Pre-condition: Initialized history object needed
    |					Index should be in range of array size
    |
    |  Post-condition: Question object removed
    |
    |      Parameters: index
    |
    |         Returns: none
    *-------------------------------------------------------------------*/
	public void removeQuestion(int index) {removeQuestion(index, false);}

    /*---------------------------------------------------------------------
    |  Method removeQuestion(int index, boolean test)
    |
    |         Purpose: remove one Question object from list
    |
    |   Pre-condition: Initialized history object needed
    |					Index should be in range of array size
    |
    |  Post-condition: Question object removed
    |
    |      Parameters: index, test(set true for testing)
    |
    |         Returns: none
    *-------------------------------------------------------------------*/
	public void removeQuestion(int index, boolean test) {
		ArrayList<Question> removedHistory = new ArrayList<>();
		for(int i=0; i<history.size();i++) {
			if(i==index) {continue;}
			removedHistory.add(history.get(i));
		}
		history = removedHistory;
		saveHistory(test);
	}

    /*---------------------------------------------------------------------
    |  Method saveHistory(boolean test)
    |
    |         Purpose: method used to save current arrayList in text file
    |
    |   Pre-condition: none
    |
    |  Post-condition: current arrayList saved in text file
    |
    |      Parameters: test(set true for testing)
    |
    |         Returns: none
    *-------------------------------------------------------------------*/
	public void saveHistory(boolean test) {
		File dbFile;
		if(!test) {
			dbFile = new File(dbPath);
		} else {
			dbFile = new File(testDbPath);
		}
		try (FileWriter fw = new FileWriter(dbFile)) {
			for(Question q : history) {
				fw.write(q.getQuestionString()+"\n");
				fw.flush();
				fw.write(q.getAnswerObject().getAnswerString()+"\n");
				fw.flush();
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
