package io.jona.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.jona.framework.ProcessingOfRequests.processRequest;

public class Main {
//    static Path CONTENT_ROOT = Paths.get("C:\\Users\\barri\\IdeaProjects\\padawan-learner\\simple-http-server\\websites\\jona-portafolio");

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
//            new Thread(() -> {
                try (Socket client = serverSocket.accept()) {
                    logger.info("Server started");
                    HttpRequest request = new HttpRequest();
                    request.readFromSocket(client);
                    HttpResponse response = new HttpResponse();
                    processRequest(request, response, "");

                    byte[] responseBytes = response.buildResponse();

                    client.getOutputStream().write(responseBytes);
                    client.getOutputStream().flush();
                } catch (IOException e) {
                    logger.error("Fallo en la clase main", e);
                }
//            }).start();
            }
        }
    }
}