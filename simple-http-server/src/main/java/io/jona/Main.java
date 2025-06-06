package io.jona;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.jona.ProcessingOfRequests.processRequest;

public class Main {

    static Path CONTENT_ROOT = Paths.get("C:\\Users\\barri\\IdeaProjects\\padawan-learner\\simple-http-server\\websites\\jona-portafolio");

    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket client = serverSocket.accept();
            HttpRequest request = new HttpRequest();
            request.readFromSocket(client);
            HttpResponse response = new HttpResponse();

            processRequest(request, response);

            byte[] responseBytes = response.buildResponse();
            System.out.println("Respuesta al navegador:\n "+ response);

            client.getOutputStream().write(responseBytes);
            client.getOutputStream().flush();

            client.close();
        }
    }
}