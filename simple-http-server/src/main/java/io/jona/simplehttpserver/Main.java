package io.jona.simplehttpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.jona.simplehttpserver.ProcessingOfRequests.processRequest;

public class Main {
    static Path CONTENT_ROOT = Paths.get("C:\\Users\\barri\\IdeaProjects\\padawan-learner\\simple-http-server\\websites\\jona-portafolio");

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        logger.info("Server started");
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket client = serverSocket.accept();
//            new Thread(() -> {
                try {
                    HttpRequest request = new HttpRequest();
                    request.readFromSocket(client);
                    HttpResponse response = new HttpResponse();
                    processRequest(request, response);

                    byte[] responseBytes = response.buildResponse();

                    client.getOutputStream().write(responseBytes);
                    client.getOutputStream().flush();

                    client.close();
                } catch (IOException e) {
                    logger.error("Fallo en la clase main", e);
                }
//            }).start();
        }
    }
}