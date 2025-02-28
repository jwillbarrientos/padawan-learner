package py.jona.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Server {
    // Use same port as server
    private static final int PORT = 1500;

    public static void main(String[] args) throws IOException {
        List<Socket> clientsList = Collections.synchronizedList(new ArrayList<>());

        // Create a server socket to listen for connections
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");

        Thread acceptor = new Thread(new ClientAcceptor(clientsList, serverSocket));
        acceptor.start();

        Thread keyboardRelay = new Thread(new KeyboardRelay(clientsList));
        keyboardRelay.start();
    }
}
