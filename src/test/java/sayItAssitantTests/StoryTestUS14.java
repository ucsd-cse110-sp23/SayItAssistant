package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import sayItAssistant.data.Answer;
import sayItAssistant.data.DataBase;
import sayItAssistant.data.Question;
import sayItAssistant.mocking.MockDataBase;

public class StoryTestUS14 {
  @AfterEach
  void tearDown() throws Exception {
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority")) {
            MongoDatabase db = mongoClient.getDatabase("JunitTest");
            db.drop();
        }
  }

  /*+----------------------------------------------------------------------
  ||
  ||  Scenario 1: Clear all questions
  ||  Given: The question history is displayed on the SayItAssistant2 application
  ||  When: Helen click “Start” button and say “Clear All”
  ||  Then: Every question in question history existing on Helen’s account is deleted.
  ||
  |+-----------------------------------------------------------------------
  ||
  ||  First initialize DataBase object and call signUp method same as our UI
  ||  Check return value of the method
  ||  Check if the question list is empty or not
  ||  If the question list is not empty, then using voice command, clear all questions
  ||
  ++-----------------------------------------------------------------------*/

  /*	
    public void removeQuestion(int index) {
		ArrayList<Question> removedHistory = new ArrayList<>();
		for(int i=0; i<history.size();i++) {
			if(i==index) {continue;}
			removedHistory.add(history.get(i));
		}
		history = removedHistory;
		saveHistory();
	}
   */
  @Test
  void scenarioOneTest() {
    DataBase db = new MockDataBase();
    boolean signUpResult = db.signUp("helenk@ucsd.edu","hk12345");
    assertEquals(true, signUpResult);

    db.addQuestion(new Question("What caused the fall of the Roman Empire?", 
    new Answer("The fall of the Roman Empire was a complex and multifaceted event,influenced by a combination of internal and external factors over several centuries.")));
    assertEquals(1, db.getHistory().size());

    boolean success = db.clearAll();
    assertEquals(true, success);
    assertEquals(0, db.getHistory().size());
}

  /*+----------------------------------------------------------------------
  ||
  ||  Scenario 2: Clear no questions
  ||  Given: The empty history is displayed on the SayItAssistant2 application
  ||  When: Helen click “Start” button and say “Clear All”
  ||  Then: The question history is still empty.
  ||
  |+-----------------------------------------------------------------------
  ||
  ||  First initialize DataBase object and call signUp method same as our UI
  ||  Check return value of the method
  ||  Check if the question list is empty or not
  ||  If the question list is empty, then using voice command, clear all questions
  ||
  ++-----------------------------------------------------------------------*/
  @Test
  void scenarioTwoTest() {
    DataBase db = new MockDataBase();
    boolean signUpResult = db.signUp("helenk@uscd.edu","hk12345");
    assertEquals(true, signUpResult);

    assertEquals(0, db.getHistory().size());

    boolean success = db.clearAll();
    assertEquals(true, success); 
    assertEquals(0, db.getHistory().size());
  }
}