package py.jona;

import java.io.*;
import java.net.*;

public class Client {
    // Use same port as server
    private static final int PORT = 1500;
    private static final String SERVER_ADDRESS = "localhost";

    public static void main( String[] args ) {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connected to server!");

            // Set up input and output streams
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

            // Set up console input
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Create a thread to handle incoming messages
            ClientReader clientReader = new ClientReader(socketIn);
            Thread receiveThread = new Thread(clientReader);
            receiveThread.start();

            // Main thread handles sending messages
            String message;
            while ((message = consoleInput.readLine()) != null) {
                socketOut.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
