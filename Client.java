
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        // Set client ip address (localhost) and port number
        InetAddress SERVER_IP = InetAddress.getLocalHost();
        int PORT = 9078;

        try (
            // Creat a socket (inside try-with-resources) to allow auto-closing of I/O
            Socket clientSocket = new Socket(SERVER_IP, PORT);
            // Open output and input streams for the socket to allow communication with the server
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer, fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("BYE"))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + SERVER_IP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                SERVER_IP);
            System.exit(1);
        }
    }
}
