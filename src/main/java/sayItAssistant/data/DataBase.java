package sayItAssistant.data;

import com.mongodb.client.*;
import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;


public class DataBase {

    private final String uri = "mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority";
    private MongoCollection<Document> collection;
    private ArrayList<Question> history;

    public DataBase() {
        history = new ArrayList<>();
    }

    public int logIn(String id, String pw) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("SayItAssistant2");
            collection = db.getCollection("historyList");
            Document document = collection.find(eq("user_id", id)).first();
            if (document == null) {
                System.out.println("ID does not exist");
                return -1;
            }
            if(document.get("user_pw").equals(pw)) {
                String questions = (String)document.get("question_list");
                Scanner scanner = new Scanner(questions);
                String questionString;
                String answerString;
                while (scanner.hasNextLine()) {
                    questionString = scanner.nextLine();
                    Question tempQuestion= new Question();
				    tempQuestion.setQuestionString(questionString);
				    answerString = scanner.nextLine();
				    tempQuestion.setAnswerObject(new Answer(answerString));
				    history.add(tempQuestion);
                }
                scanner.close();
                return 0;
            } else {
                System.out.println("Wrong password");
                return 1;
            }
        }
    }



    public ArrayList<Question> getHistory() {
        return history;
    }
    
}
