package io.jona.framework;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import static io.jona.framework.ProcessingOfRequests.processStaticPath;

@Slf4j
public class JonaServer {
    private final int port;
    @Getter
    private volatile String staticContentLocation;
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> endPoints = new LinkedHashMap<>();
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> inboundFilters = new LinkedHashMap<>();
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> outboundFilters = new LinkedHashMap<>();

    public JonaServer() {
        this(8080);
    }

    public JonaServer(int port) {
        this.port = port;
    }

    public void registerInboundFilter(Methods method, String path, BiConsumer<HttpRequest, HttpResponse> biConsumer) {
        inboundFilters.put(path, biConsumer);
    }

    public void registerEndPoint(Methods method, String path, BiConsumer<HttpRequest, HttpResponse> biConsumer) {
        endPoints.put(path, biConsumer);
    }

    public void addStaticContent(String location) {
        this.staticContentLocation = location;
    }

    public void registerOutboundFilter(Methods method, String path, BiConsumer<HttpRequest, HttpResponse> biConsumer) {
        outboundFilters.put(path, biConsumer);
    }

    public void start() throws IOException {
        try (ExecutorService requestExecutor = Executors.newCachedThreadPool();
             ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server started");
            while (true) {
                requestExecutor.execute(() -> processRequest(serverSocket));
            }
        }
    }

    private void processRequest(ServerSocket serverSocket) {
        HttpRequest request = null;
        try (Socket client = serverSocket.accept()) {
            request = new HttpRequest(client);
            HttpResponse response = new HttpResponse();

            for(String regex : inboundFilters.keySet()) {
                if (request.canonicalPath().matches(regex)) {
                    BiConsumer<HttpRequest, HttpResponse> biConsumer = inboundFilters.get(regex);
                    biConsumer.accept(request, response);
                    if (response.isFinal()) {
                        break;
                    }
                }
            }

            if (response.isFinal()) {
                byte[] responseBytes = response.buildResponse();
                client.getOutputStream().write(responseBytes);
                client.getOutputStream().flush();
                return;
            }

            boolean isDynamic = endPoints.containsKey(request.canonicalPath());
            if (isDynamic) {
                BiConsumer<HttpRequest, HttpResponse> biConsumer = endPoints.get(request.canonicalPath());
                biConsumer.accept(request, response);
            } else {
                processStaticPath(request, response, staticContentLocation);
            }

            for(String regex : outboundFilters.keySet()) {
                if (request.canonicalPath().matches(regex)) {
                    BiConsumer<HttpRequest, HttpResponse> biConsumer = outboundFilters.get(regex);
                    biConsumer.accept(request, response);
                }
            }

            client.getOutputStream().write(response.buildResponse());
            client.getOutputStream().flush();
        } catch (IOException e) {
            log.error("Exception while processing request: {}", request, e);
        }
    }
}
