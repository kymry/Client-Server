import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

    public static List<ThreadServer> clientList;

    public static ThreadServer getClient(String client){
      for (ThreadServer t : clientList){
        if (t.getName() == client) return t;
      }
      return null;
    }

    public static void main(String[] args) throws IOException {
        // Set client ip address (localhost) and port number
        InetAddress SERVER_IP = InetAddress.getLocalHost();
        int PORT = 9078;
        clientList = new ArrayList<ThreadServer>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
	            ThreadServer client = new ThreadServer(serverSocket.accept());
              client.start();
              clientList.add(client);
              System.out.println(client.getName());
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
            System.exit(-1);
        }
    }
}
