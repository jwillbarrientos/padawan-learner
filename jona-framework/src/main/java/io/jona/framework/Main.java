package io.jona.framework;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import static io.jona.framework.ProcessingOfRequests.processRequest;

@Slf4j
public class Main {
    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
//            new Thread(() -> {
                try (Socket client = serverSocket.accept()) {
                    log.info("Server started");
                    HttpRequest request = new HttpRequest();
                    request.readFromSocket(client);
                    HttpResponse response = new HttpResponse();
                    processRequest(request, response, "");

                    byte[] responseBytes = response.buildResponse();

                    client.getOutputStream().write(responseBytes);
                    client.getOutputStream().flush();
                } catch (IOException e) {
                    log.error("Fallo en la clase main", e);
                }
//            }).start();
            }
        }
    }
}