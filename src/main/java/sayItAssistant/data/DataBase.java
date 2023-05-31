package sayItAssistant.data;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


public class DataBase {

    private final String uri = "mongodb+srv://cjadmin:cksghS9(@cluster0.1b0dvhj.mongodb.net/?retryWrites=true&w=majority";
    private MongoCollection<Document> collection;
    private String user_id;
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
            	history = new ArrayList<>();
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
                user_id=id;
                return 0;
            } else {
                System.out.println("Wrong password");
                return 1;
            }
        }
    }

    public boolean signUp(String id, String pw) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("SayItAssistant2");
            collection = db.getCollection("historyList");
            Document document = collection.find(eq("user_id", id)).first();
            if(document != null) {
                //ID exist
                System.out.println("Account exist");
                return false;
            } else {
            	history = new ArrayList<>();
                Document user = new Document("_id", new ObjectId());
                user.append("user_id", id)
                   .append("user_pw", pw)
                   .append("question_list", "");
                collection.insertOne(user);
                user_id=id;
                return true;
            }
        }
    }
    
    public boolean addQuestion(Question question) {
    	if(user_id == null) {
    		return false;
    	}
    	ArrayList<Question> addedHistory = new ArrayList<>();
		addedHistory.add(question);
		for (Question q : history) {
			addedHistory.add(q);
		}
		history = addedHistory;

    	try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("SayItAssistant2");
            collection = db.getCollection("historyList");
           	collection.updateOne(eq("user_id",user_id), set("question_list",historyToString()));
           	
           	return true;
    	}
    }
    
    private String historyToString() {
		String historyString = "";
		for (Question q : history) {
			historyString += (q.getQuestionString()+"\n");
			historyString += (q.getAnswerObject().getAnswerString()+"\n");
		}
		return historyString;
    }


    public ArrayList<Question> getHistory() {
        return history;
    }
    
//    public static void main(String[] args) {
//    	DataBase db = new DataBase();
//    	Question q= new Question("q8", new Answer("a8"));
//		System.out.println(db.addQuestion(q));
//		db.logIn("admin", "admin1234");
//		System.out.println(db.addQuestion(q));
//		System.out.println("Size: " + db.getHistory().size());
//		System.out.println("ID: " + db.user_id);
//		for (Question s: db.getHistory()) {
//			System.out.println(s.getQuestionString());
//			System.out.println(s.getAnswerObject().getAnswerString());
//		}
//		db.logIn("test@gmail.com", "1234");
//		System.out.println("ID: " + db.user_id);
//		System.out.println("Size: " + db.getHistory().size());
//		for (Question s: db.getHistory()) {
//			System.out.println(s.getQuestionString());
//			System.out.println(s.getAnswerObject().getAnswerString());
//		}
//		db.logIn("admin", "admin1234");
//		System.out.println("ID: " + db.user_id);
//		System.out.println("Size: " + db.getHistory().size());
//		for (Question s: db.getHistory()) {
//			System.out.println(s.getQuestionString());
//			System.out.println(s.getAnswerObject().getAnswerString());
//		}
//	}
    
    
}
