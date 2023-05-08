package sayItAssistant;
/*+----------------------------------------------------------------------
||
||  Class ChatGpt
||
||         Author:  Chanho Jeon
||
||        Purpose:  Get answers from openai api using String type question
||
|+-----------------------------------------------------------------------
||
||          Field:
||					API_ENDPOINT - URL of API
||					API_KEY - API key
||					MODEL - Version/ currently GPT3.5
||					MAX_TOKENS - Maximum tokens in generating answer
||					TEMPERATRUE - clarity(0: focused, 2:random)
||					questionString - question goes into API
||					answerString - Answer get from API
||					requestJson - Json object to send request
||					responseJson - Json object of response
||					history - History object contains list of questions
||					request - Http request
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					ChatGpt()- default constructor
||					Initialize history, questionString, requestJson
||					put variables into request Json object
||
||  Class Methods:				
||					setRequest() - private method to set request field
||					setResponse() - private method to send request and get
||									response
||					search() - method calls setRequest, setResponse
||								put Question object in the history and save it
||
++-----------------------------------------------------------------------*/
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGpt {
	private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
	private static final String API_KEY = "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";
	private static final String MODEL = "text-davinci-003";
	private static final int MAX_TOKENS = 100;
	private static final double TEMPERATURE = 1.0;
	
	String questionString;
	String answerString;
	JSONObject requestJson;
	JSONObject responseJson;
	History history;
	HttpRequest request;
	
    /*---------------------------------------------------------------------
    |  Constructor ChatGpt()
    |
    |         Purpose: default constructor
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize ChatGpt object. Answer in Question object
    |					of static arrayList in History class updated
    |
    |      Parameters: None
    |
    |         Returns: ChatGpt Object
    *-------------------------------------------------------------------*/
	public ChatGpt() {
		history = new History();
		questionString = history.getHistory().get(0).getQuestionString();
		requestJson = new JSONObject();
		requestJson.put("model", MODEL);
		requestJson.put("prompt", questionString);
		requestJson.put("max_tokens", MAX_TOKENS);
		requestJson.put("temperature", TEMPERATURE);
	}
	
    /*---------------------------------------------------------------------
    |  Method search()
    |
    |         Purpose: call setRequest and setResponse, put answer in
    |					History object and save it to DB
    |
    |   Pre-condition: Initialized ChatGpt object needed
    |
    |  Post-condition: History object has answer to the question
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
	public void search() {
		setRequest();
		try {
			setResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		history.getHistory().get(0).getAnswerObject().setAnswerString(answerString);
		history.saveHistory(false);
	}
	
    /*---------------------------------------------------------------------
    |  Method setRequest()
    |
    |         Purpose: private method to set HttpRequest
    |
    |   Pre-condition: None
    |
    |  Post-condition: HttpRequest is set to use chatGPT API
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
	private void setRequest() {
		request = HttpRequest
				.newBuilder()
				.uri(URI.create(API_ENDPOINT))
				.header("Content-Type", "application/json")
				.header("Authorization", String.format("Bearer %s", API_KEY))
				.POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
				.build();
	}
	
    /*---------------------------------------------------------------------
    |  Method setResponse()
    |
    |         Purpose: private method to get response and set answerString
    |
    |   Pre-condition: None
    |
    |  Post-condition: answerString now contains the answer from API
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
	private void setResponse() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String responseBody = response.body();
		responseJson = new JSONObject(responseBody);
		JSONArray choices = responseJson.getJSONArray("choices");
		answerString = choices.getJSONObject(0).getString("text").replace("\n", "").replace("\r", "");
	}
}
