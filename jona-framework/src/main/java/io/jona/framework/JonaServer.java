package io.jona.framework;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

import static io.jona.framework.ProcessingOfRequests.processRequest;

public class JonaServer {
    private final int port;
    private volatile String staticContentLocation;

    public JonaServer() {
        this(8080);
    }

    public JonaServer(int port) {
        this.port = port;
    }

    public void registerEndPoint(Methods method, String tag, Function<HttpResponse, HttpRequest> function) {

    }

    public void addStaticContent(String location) {
        this.staticContentLocation = location;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while(true) {
            try (Socket client = serverSocket.accept()) {
                System.out.println("Server started");
                HttpRequest request = new HttpRequest();
                request.readFromSocket(client);
                HttpResponse response = new HttpResponse();
                processRequest(request, response, staticContentLocation);

                byte[] responseBytes = response.buildResponse();

                client.getOutputStream().write(responseBytes);
                client.getOutputStream().flush();
            } catch (IOException e) {
                System.out.println("Fail in start method");
            }
        }
    }
}
