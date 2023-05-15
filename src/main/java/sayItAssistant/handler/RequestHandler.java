package sayItAssistant.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sayItAssistant.data.Answer;
import sayItAssistant.data.History;
import sayItAssistant.data.Question;

public class RequestHandler implements HttpHandler{

	private final History data;


	public RequestHandler(History data) {
		this.data = data;
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
				//response = handleDelete(httpExchange);
			} else if (method.equals("DELETEALL")) {
				//response = handleDeleteAll(httpExchange);
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
		scanner.close();
	}

}
