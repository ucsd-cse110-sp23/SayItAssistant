package sayItAssistant.handler;

import com.sun.net.httpserver.*;

import sayItAssistant.data.History;

import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler implements HttpHandler{

	private final History data;


	public RequestHandler(History data) {
		this.data = data;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
