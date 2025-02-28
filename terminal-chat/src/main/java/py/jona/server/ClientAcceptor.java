package py.jona.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * //acepta conexiones entrantes y instancia un serverSocketReader para atender a esa conexion
 */
class ClientAcceptor implements Runnable{
    List<Socket> clientList;
    ServerSocket serverSocket;
    public ClientAcceptor (List<Socket> clientList, ServerSocket serverSocket){
        this.clientList = clientList;
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        while (true) {
            try {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());
                clientList.add(clientSocket);

                // Create a thread to handle incoming messages
                Thread receiveThread = new Thread(new ServerSocketReader(clientList, clientSocket));
                receiveThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
