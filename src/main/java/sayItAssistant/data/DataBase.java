package sayItAssistant.data;
/*+----------------------------------------------------------------------
||
||  Class History
||
||         Author:  Chanho Jeon
||
||        Purpose:  Connect to MongoDB, get collections
||					Verify login, add data when account created,
||					Update question when new question is asked
||
|+-----------------------------------------------------------------------
||
||          Field:
||					uri: uri for connecting mongodb
||					collection: array of documents containing 
||							information of collection
||					user_id: id which will be set when user log in
||					history: arrayList containing Question object
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					DataBase() - default constructor/ initialize arraylist
||					DataBase(str, arrList) - constructor using user informations
||					 
||
||  Class Methods:				
||					logIn() - check if id and pw matches database
||					signUp() - check if id exist, create new data in database
||					addQuestion() - add question in database
||
++-----------------------------------------------------------------------*/
import com.mongodb.client.*;

import sayItAssistant.components.EmailConfig;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private final String dbPath = "./DB/Questions.txt";

    /*---------------------------------------------------------------------
    |  Constructor DataBase()
    |
    |         Purpose: default constructor
    |
    |   Pre-condition: None
    |
    |  Post-condition: history field(question list) is initialized
    |
    |      Parameters: none
    |
    |         Returns: DataBase object
    *-------------------------------------------------------------------*/
    public DataBase() {
        history = new ArrayList<>();
    }
    
    /*---------------------------------------------------------------------
    |  Constructor DataBase(str, arrList)
    |
    |         Purpose: constructor using user information
    |
    |   Pre-condition: None
    |
    |  Post-condition: object contains user_id and question list as 
    |					passed argument
    |
    |      Parameters: none
    |
    |         Returns: DataBase object
    *-------------------------------------------------------------------*/
    public DataBase(String user_id, ArrayList<Question> history) {
    	this.user_id=user_id;
    	this.history=history;
    }

    /*---------------------------------------------------------------------
    |  Method logIn(String id, String pw)
    |
    |         Purpose: check if id and pw exist on database
    |					if so, load question list from database
    |					else, return proper error code
    |
    |   Pre-condition: Initialized DataBase object needed
    |
    |  Post-condition: question list is loaded for valid id,pw
    |
    |      Parameters: String id, String pw
    |
    |         Returns: -1 | the id does not exist in database
    |					0 | login success
    |					1 | the pw does not match in database
    *-------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method signUp(String id, String pw)
    |
    |         Purpose: check if id exist on database
    |					if so, return false
    |					else, create new data on database
    |
    |   Pre-condition: Initialized DataBase object needed
    |
    |  Post-condition: new data inserted and logged in
    |
    |      Parameters: String id, String pw
    |
    |         Returns: True | the id already exist on database
    |					False | account created/logged in
    *-------------------------------------------------------------------*/
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
                   .append("question_list", "")
                   .append("DisplayName","")
                   .append("EmailAddress","")
                   .append("FirstName","")
                   .append("LastName","")
                   .append("Password","")
                   .append("SMTP","")
                   .append("TLSPort","");
                collection.insertOne(user);
                user_id=id;
                return true;
            }
        }
    }
    
    /*---------------------------------------------------------------------
    |  Method emailSettings(...)
    |
    |         Purpose: update email settings
    |
    |   Pre-condition: Initialized DataBase object needed
    |
    |  Post-condition: email settings changed
    |
    |      Parameters: displayName, emailAddress, firstName, lastName
    |					password, smtp, tlsPort
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public void emailSettings(String displayName, String emailAddress,
    						String firstName, String lastName, String password,
    						String smtp, String tlsPort) {
    	try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("SayItAssistant2");
            collection = db.getCollection("historyList");
           	collection.updateOne(eq("user_id",user_id), set("DisplayName",displayName));
           	collection.updateOne(eq("user_id",user_id), set("EmailAddress",emailAddress));
           	collection.updateOne(eq("user_id",user_id), set("FirstName",firstName));
           	collection.updateOne(eq("user_id",user_id), set("LastName",lastName));
           	collection.updateOne(eq("user_id",user_id), set("Password",password));
           	collection.updateOne(eq("user_id",user_id), set("SMTP",smtp));
           	collection.updateOne(eq("user_id",user_id), set("TLSPort",tlsPort));
    	}
    }

    /*---------------------------------------------------------------------
    |  Method getEmailSettings(...)
    |
    |         Purpose: update email settings on local file
    |
    |   Pre-condition: Initialized DataBase object needed
    |
    |  Post-condition: email settings changed on local file
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    
    public void getEmailSettings() {
        try(MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("SayItAssistant2");
            collection = db.getCollection("historyList");
            EmailConfig emailDetails = new EmailConfig();
            Document document = collection.find(eq("user_id", user_id)).first();
            emailDetails.setProperty("DisplayName", document.get("DisplayName").toString());
            emailDetails.setProperty("EmailAddress", document.get("EmailAddress").toString());
            emailDetails.setProperty("FirstName", document.get("FirstName").toString());
            emailDetails.setProperty("LastName", document.get("LastName").toString());
            emailDetails.setProperty("Password", document.get("Password").toString());
            emailDetails.setProperty("SMTP", document.get("SMTP").toString());
            emailDetails.setProperty("TLSPort", document.get("TLSPort").toString());
            emailDetails.store();
        }
    }
    /*---------------------------------------------------------------------
    |  Method removeQuestion(Question question)
    |
    |         Purpose: delete question in database
    |
    |   Pre-condition: Initialized DataBase object needed
    |
    |  Post-condition: selected question deleted
    |
    |      Parameters: Question
    |
    |         Returns: True | successfully deleted
    |					False | failed-fatal error
    *-------------------------------------------------------------------*/
    public boolean removeQuestion(int index){   
        if(user_id == null) {
    		return false;
    	}
        String question = history.get(index).getQuestionString();

    	ArrayList<Question> removedHistory = new ArrayList<>();
    
		for (Question q : history) {
            if(q.getQuestionString().equals(question)){
                continue;
            }
            removedHistory.add(q);
        }
        history = removedHistory;
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("SayItAssistant2");
            collection = db.getCollection("historyList");
           	collection.updateOne(eq("user_id",user_id), set("question_list",historyToString()));

           	return true;
    	}
    }

    /*---------------------------------------------------------------------
    |  Method addQuestion(Question question)
    |
    |         Purpose: add new question in database
    |
    |   Pre-condition: Initialized DataBase object needed
    |
    |  Post-condition: new question inserted
    |
    |      Parameters: Question
    |
    |         Returns: True | successfully added
    |					False | failed-fatal error
    *-------------------------------------------------------------------*/
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
    
    /*---------------------------------------------------------------------
    |  Method historyToString()
    |
    |         Purpose: private method to change current history field
    |					to string format
    |
    |   Pre-condition: Initialized DataBase object needed
    |
    |  Post-condition: None
    |
    |      Parameters: None
    |
    |         Returns: String formatted question list
    *-------------------------------------------------------------------*/
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

    public void setUserID(String userID) {
        this.user_id = userID;
    }

    public void writeToFile() {
        File dbFile=  new File(dbPath);

        try (FileWriter fw = new FileWriter(dbFile)) {
            fw.write("");
            fw.write(this.historyToString());
            fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
