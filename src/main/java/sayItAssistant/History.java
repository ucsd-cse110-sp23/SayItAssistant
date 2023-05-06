package sayItAssistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class History {

	private final String dbPath = "./DB/Questions.txt";
	private final String testDbPath = "./DB/QuestionsForTest.txt";
	private ArrayList<Question> history;

	public History() {this(false);}

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

	public ArrayList<Question> getHistory() {
		return history;
	}

	public void addQuestion(Question question) {addQuestion(question,false);}

	public void addQuestion(Question question, boolean test) {
		ArrayList<Question> addedHistory = new ArrayList<>();
		addedHistory.add(question);
		for (Question q : history) {
			addedHistory.add(q);
		}
		history = addedHistory;
		saveHistory(test);
	}

	public void removeQeustion(int index) {removeQuestion(index, false);}

	public void removeQuestion(int index, boolean test) {
		ArrayList<Question> removedHistory = new ArrayList<>();
		for(int i=0; i<history.size();i++) {
			if(i==index) {continue;}
			removedHistory.add(history.get(i));
		}
		history = removedHistory;
		saveHistory(test);
	}


	private void saveHistory(boolean test) {
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
