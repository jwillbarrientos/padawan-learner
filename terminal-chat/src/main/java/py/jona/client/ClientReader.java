package py.jona.client;

import java.io.BufferedReader;
import java.io.IOException;

class ClientReader implements Runnable {
    BufferedReader socketIn;
    public ClientReader(BufferedReader socketIn) {
        this.socketIn = socketIn;
    }
    @Override
    public void run() {
        try {
            String message;
            while ((message = socketIn.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("Server disconnected");
        }
    }
}
