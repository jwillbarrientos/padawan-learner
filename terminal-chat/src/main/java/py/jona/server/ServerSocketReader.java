package py.jona.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * //lee una linea de un socket en un loop, imprime la linea en la terminal, y envia esa misma linea a todos los clientes conectados
 */
class ServerSocketReader implements Runnable {
    List<Socket> clientList;
    Socket clientSocket;
    public ServerSocketReader(List<Socket> clientList, Socket clientSocket) {
        this.clientList = clientList;
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            // Set up input streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while((message = in.readLine()) != null) {
                System.out.println("Client: " + message);
                for(Socket otherClientSocket : clientList) {
                    if(otherClientSocket.equals(clientSocket)) {
                        continue;
                    }
                    PrintWriter outOthers = new PrintWriter(otherClientSocket.getOutputStream(), true);
                    outOthers.println("Client: " + message);
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }
}
