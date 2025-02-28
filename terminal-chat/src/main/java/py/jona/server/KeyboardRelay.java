package py.jona.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * \lee lineas del teclado en un loop y envia esas lineas a todos los clientes conectados
 */

class KeyboardRelay implements Runnable {
    List<Socket> clientList;
    public KeyboardRelay(List<Socket> clientList) {
        this.clientList = clientList;
    }
    @Override
    public void run() {
        try {
            // Set up console input for server
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while ((message = consoleInput.readLine()) != null) {
                for (Socket clientSocket : clientList) {
                    // Set up output for streams
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("Server: " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
