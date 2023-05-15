package sayItAssistant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.net.httpserver.HttpServer;

import sayItAssistant.data.History;
import sayItAssistant.handler.RequestHandler;

public class Server {

	private static final int SERVER_PORT = 8100;
	private static final String SERVER_HOSTNAME = "localhost";
	 
	public static void main(String[] args) throws IOException {
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
	   	History data = new History(); 
	   	HttpServer server = HttpServer.create(
	   			new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT), 0);
	   	server.createContext("/", new RequestHandler(data));
	   	server.setExecutor(threadPoolExecutor);
	    server.start();
	    System.out.println("Server started on port " + SERVER_PORT);
	}
   	
}
