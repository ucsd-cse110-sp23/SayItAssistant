package sayItAssistant.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sayItAssistant.components.LoginConfig;
import sayItAssistant.data.Answer;
import sayItAssistant.data.DataBase;
import sayItAssistant.data.History;
import sayItAssistant.data.Question;

public class RequestHandler implements HttpHandler{

	private final History data;
	private final DataBase database;
	private LoginConfig properties;
	private String userID;


	public RequestHandler(History data, DataBase database) {
		this.data = data;
		this.database = database;
		this.properties = new LoginConfig();
		userID = properties.getProperty("username");
		database.setUserID(userID);
	}
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		String response = "Request Received";
		String method = httpExchange.getRequestMethod();
		try {
			if(method.equals("GET")) {
				//response = handleGet(httpExchange);
			} else if (method.equals("POST")) {
				handlePost(httpExchange);
			} else if (method.equals("DELETE")) {
				handleDelete(httpExchange);
			} else if (method.equals("PUT")) {
				handleDeleteAll(httpExchange);
			} else {
				throw new Exception("Not Valid Request Method");
			}
		} catch (Exception e) {
			System.out.println("An erroneous request");
			response = e.toString();
			e.printStackTrace();
		}
		httpExchange.sendResponseHeaders(200, response.length());
		OutputStream outStream = httpExchange.getResponseBody();
		outStream.write(response.getBytes());
		outStream.close();
	}
	
	private void handlePost(HttpExchange httpExchange) throws IOException {
		InputStream inStream = httpExchange.getRequestBody();
		Scanner scanner = new Scanner(inStream);
		String postData = scanner.nextLine();
		String questionString = postData.substring(
		  0,
		  postData.indexOf(",")
		), answerString = postData.substring(postData.indexOf(",") + 1);

		Question question = new Question(questionString, new Answer(answerString));
		data.addQuestion(question);
		database.addQuestion(question);
		scanner.close();
	}
	
	private void handleDelete(HttpExchange httpExchange) throws IOException {
		URI uri = httpExchange.getRequestURI();
		String query = uri.getRawQuery();
		String stringIndex = query.substring(query.indexOf("=") + 1);
		int intIndex = Integer.parseInt(stringIndex);
		data.removeQuestion(intIndex);
		database.removeQuestion(intIndex);
	}
	
	private void handleDeleteAll(HttpExchange httpExchange) throws IOException {
		data.clearHistory();
		database.clearAll();
	}

	public void setUser() {
		userID = properties.getProperty("username");
		database.setUserID(userID);
	}

}
