package sayItAssistant;

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
	private static final double TEMPERATURE = 0.8;
	
	String questionString;
	String answerString;
	JSONObject requestJson;
	JSONObject responseJson;
	History history;
	HttpRequest request;
	
	
	public ChatGpt() {
		history = new History();
		questionString = history.getHistory().get(0).getQuestionString();
		requestJson = new JSONObject();
		requestJson.put("model", MODEL);
		requestJson.put("prompt", questionString);
		requestJson.put("max_tokens", MAX_TOKENS);
		requestJson.put("temperature", TEMPERATURE);
	}
	
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
	
	private void setRequest() {
		request = HttpRequest
				.newBuilder()
				.uri(URI.create(API_ENDPOINT))
				.header("Content-Type", "application/json")
				.header("Authorization", String.format("Bearer %s", API_KEY))
				.POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
				.build();
	}
	
	private void setResponse() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String responseBody = response.body();
		responseJson = new JSONObject(responseBody);
		JSONArray choices = responseJson.getJSONArray("choices");
		answerString = choices.getJSONObject(0).getString("text").replace("\n", "").replace("\r", "");
	}
}
