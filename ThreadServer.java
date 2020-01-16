import java.net.*;
import java.io.*;
import java.util.*;

public class ThreadServer extends Thread{
    private Socket socket = null;
    public ThreadServer myConnection = null;

    public ThreadServer(Socket socket) {
        this.socket = socket;
      }

    public void sendMessagetoClient(ThreadServer c, String message){



    }

    // Connects two clients and enables them to send messages to each other
    public ThreadServer connectClients(String clientName){
            ThreadServer t = Server.getClient(clientName);
            return t;
    }

    @Override
    public void run() {

        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            ServerProtocol customProtocol = new ServerProtocol();

            // Gets a reference to the thread currently executing in this block of code
            // With a reference, methods can be called on it
            Thread currentThread = Thread.currentThread();
            outputLine = customProtocol.processInput("hello " + currentThread.getName());
            out.println(outputLine);

            // Loop forever while client is connected to server
            while ((inputLine = in.readLine()) != null) {
              // If client wants to connect to another client
              if (inputLine.toLowerCase().contains("connect")){
                  out.println("Who would you like to connect to?");
                  myConnection = connectClients(in.readLine());
                  continue;
              }
              if (myConnection != null){
                  sendMessagetoClient(myConnection, inputLine);
                  continue;
              }
                outputLine = customProtocol.processInput(inputLine);

                out.println(outputLine);
                if (outputLine.equals("Q"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
