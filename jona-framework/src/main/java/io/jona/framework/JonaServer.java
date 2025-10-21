package io.jona.framework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.jona.framework.ProcessingOfRequests.processRequest;

public class JonaServer {
    private final int port;
    private volatile String staticContentLocation;
    private final Map<String, Function<HttpRequest, HttpResponse>> endPoints = new HashMap<>();
    private final Map<String, Function<HttpRequest, HttpResponse>> filters = new HashMap<>();

    public JonaServer() {
        this(8080);
    }

    public JonaServer(int port) {
        this.port = port;
    }

    public void registerFilter(Methods method, String path, Function<HttpRequest, HttpResponse> function) {
        filters.put(path, function);
    }

    public void registerEndPoint(Methods method, String path, Function<HttpRequest, HttpResponse> function) {
        endPoints.put(path, function);
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
                System.out.println(request.getPath());
                HttpResponse response = new HttpResponse();
                boolean isDynamic = endPoints.containsKey("/" + request.getPath());
                boolean isValid = true;
                for(String regex : filters.keySet()) {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher("/" + request.getPath());
                    if (matcher.matches()) {
                        isValid = false;
                        Function<HttpRequest, HttpResponse> function = filters.get(regex);
                        response = function.apply(request);
                        break;
                    }
                }
                if (isDynamic && isValid) {
                    Function<HttpRequest, HttpResponse> function = endPoints.get("/" + request.getPath());
                    response = function.apply(request);
                } else if (isValid) {
                    processRequest(request, response, staticContentLocation, endPoints);
                }

                byte[] responseBytes = response.buildResponse();

                client.getOutputStream().write(responseBytes);
                client.getOutputStream().flush();
            } catch (IOException e) {
                System.out.println("Fail in start method");
            }
        }
    }
}
