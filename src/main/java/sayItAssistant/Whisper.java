package sayItAssistant;
import java.io.*;
import java.net.*;
import org.json.*;
/*+----------------------------------------------------------------------
||
||  Class Whisper
||
||         Author:  Bella Jeong
||
||        Purpose:  This class transcribe audio using API
||
|+-----------------------------------------------------------------------
||
||          Field:
||					connection - protocol for communication between web servers and clients 
||                       (HTTP connection object)
||          outputStream - object to write request
||          boundary - responses having multipart (String type)
||          file - audio file to transcribe
||          url - API's URL object 
||          API_ENDPOINT - specifies the location (endpoint URL)
||          TOKEN - authorization token for API
||          MODEL - transcription for API 
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					public Whisper(File file_path) - constructor including file parameter
||
||  Class Methods:
||					writeParameterToOutputStream() 
||            - takes four parameters and writes to the output stream
||          writeFileToOutputStream() 
||            - takes three parameters and writes the audio file to the output stream
||          handleSuccessResponse () 
||            - takes input stream and analyze JSON response to get the result
||          handleErrorResponse () 
||            - reads error stream and gives error response
||          toTranscribe() 
||            - transcribe to audio file
||
++-----------------------------------------------------------------------*/

public class Whisper {
    //private String file_path;
    HttpURLConnection connection;
    OutputStream outputStream;
    String boundary;
    File file;
    URL url; 

    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";
 /*---------------------------------------------------------------------
    |  Constructor Whisper()
    |
    |         Purpose: transcribe audio using API
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize Whisper object containing the file
    |
    |      Parameters: file_path - file object to be trascribed
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public Whisper(File file_path) {
      file = file_path;
    }
 /*---------------------------------------------------------------------
    |  Method writeParameterToOutputStream()
    |
    |         Purpose: using the boundary string, it writes parameter 
    |                   to the OutputStream
    |
    |   Pre-condition: Initialized OutputStream, parameterName, parameterValue
    |                    and boundary string needed
    |
    |  Post-condition: using the boundary string the parameter will be written
    |                   to the OutputStream
    |
    |      Parameters: OutputStream outputStream, String parameterName,
    |                   String parameterValue, String boundary 
    |
    |         Returns: void
    *-------------------------------------------------------------------*/
    public static void writeParameterToOutputStream(
      OutputStream outputStream,
      String parameterName,
      String parameterValue,
      String boundary
  ) throws IOException{
      outputStream.write(("--" + boundary + "\r\n").getBytes());
      outputStream.write(
        (
          "Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n"
        ).getBytes()
      );
      outputStream.write((parameterValue + "\r\n").getBytes());
    }
/*---------------------------------------------------------------------
    |  Method writeFileToOutputStream()
    |
    |         Purpose: writes file to the OutputStream to be sent as HTTP request 
    |
    |   Pre-condition: valid OutputStream, file. 
    |
    |  Post-condition: file be written in the right format
    |
    |      Parameters: OutputStream outputStream, File file, String boundary
    |
    |         Returns: void
    *-------------------------------------------------------------------*/
  private static void writeFileToOutputStream(
    OutputStream outputStream,
    File file,
    String boundary
  ) throws IOException {
    outputStream.write(("--" + boundary + "\r\n").getBytes());
    outputStream.write(
    (
        "Content-Disposition: form-data; name=\"file\"; filename=\"" +
        file.getName () +
        "\"\r\n"
    ).getBytes()
  );
  outputStream.write(("Content-Type: audio/mpeg\r\n\r\n"). getBytes());

  FileInputStream fileInputStream = new FileInputStream(file);
  byte[] buffer = new byte[1024];
  int bytesRead;
  while ((bytesRead = fileInputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
  }
  fileInputStream.close();
}
/*---------------------------------------------------------------------
    |  Method handleSuccessResponse()
    |
    |         Purpose: get response from API
    |
    |   Pre-condition: Initialized HttpURLConnection connection needed
    |
    |  Post-condition: new Question object made and returned from the response
    |
    |      Parameters: HttpURLConnection connection
    |
    |         Returns: Question object
    *-------------------------------------------------------------------*/

private static Question handleSuccessResponse (HttpURLConnection connection)
  throws IOException, JSONException {
  BufferedReader in = new BufferedReader(
      new InputStreamReader(connection.getInputStream())
  );
  String inputLine;
  StringBuilder response = new StringBuilder();
  while((inputLine = in.readLine()) != null){
      response.append (inputLine);
  } 
  in.close();
  JSONObject responseJson = new JSONObject (response.toString());
  String generatedText = responseJson.getString("text");
  //Print the transcription result
  System.out.println("Transcription Result: " + generatedText);
  //Create New Question Object
  Question q = new Question(generatedText, new Answer("Unanswered Question"));
  History h = new History();
  h.addQuestion(q);
  return q;
  

}
/*---------------------------------------------------------------------
    |  Method handleErrorResponse()
    |
    |         Purpose: handles error response from API endpoint and prints 
    |
    |   Pre-condition: valid HTTP connection object needed, 
    |                   receive error message from the endpoint
    |
    |  Post-condition: error response will be read and printed 
    |
    |      Parameters: HttpURLConnection connection
    |
    |         Returns: None
    *-------------------------------------------------------------------*/

// Helper method to handle an error response
private static void handleErrorResponse (HttpURLConnection connection)
    throws IOException, JSONException {
    BufferedReader errorReader = new BufferedReader(
      new InputStreamReader (connection.getErrorStream())
    );
    String errorLine;
    StringBuilder errorResponse = new StringBuilder();
    while ((errorLine = errorReader .readLine()) != null) {
      errorResponse. append (errorLine);
    }
    errorReader .close();
    String errorResult = errorResponse. toString();
    System.out.println("Error Result: " + errorResult);
}

/*
  void createFileObj(){
    file = new File(file_path);
  }
*/

  void setUpHTTPCon()throws IOException {
    url = new URL(API_ENDPOINT);
    connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod( "POST");
    connection.setDoOutput(true);
  }

  void setUpReqHeaders(){
    boundary = "Boundary-" + System.currentTimeMillis();
    connection.setRequestProperty(
    "Content-Type",
    "multipart/form-data; boundary=" + boundary
    );
    connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
  }

  void setUpOutputStream()throws IOException{
    outputStream = connection.getOutputStream();
  }

  void writeRequestBody() throws IOException{
    // Write model parameter to request body 
    writeParameterToOutputStream(outputStream,
      "model", MODEL, boundary);
    // Write file parameter to request body
    writeFileToOutputStream(outputStream, file, boundary) ;
    // Write closing boundary to request body 
    outputStream.write(("\r\n--" + boundary +
        "--\r\n").getBytes( )) ;
  }

  void closeResponse() throws IOException{
    // Flush and close output stream
    outputStream. flush (); 
    outputStream.close();
  }

  void getAndCheckResponse()throws IOException{
    // Get response code
    int responseCode = connection.getResponseCode();
    // Check response code and handle response accordingly
    if (responseCode == HttpURLConnection.HTTP_OK) {
      handleSuccessResponse (connection);
    } else{
      handleErrorResponse (connection);
    }
  }

  void DisconnectConnection(){
    connection.disconnect();
  }
/*---------------------------------------------------------------------
    |  Method toTranscribe()
    |
    |         Purpose: transcribes the audio file 
    |
    |   Pre-condition: initialized Whisper object 
    |
    |  Post-condition: trasncription will be printed 
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/

  public void toTranscribe() throws IOException{
    //createFileObj();
    setUpHTTPCon();
    setUpReqHeaders();
    setUpOutputStream();
    writeRequestBody();
    closeResponse();
    getAndCheckResponse();
    DisconnectConnection();
    ChatGpt chatGpt = new ChatGpt();
    chatGpt.search();
  }
   
}
