package io.jona.framework;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static io.jona.framework.ProcessingOfRequests.processRequest;

@Slf4j
public class JonaServer {
    private final int port;
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

    public void registerEndPoint(Methods method, String path, BiConsumer<HttpRequest, HttpResponse> biConsumer) {
        endPoints.put(path, biConsumer);
    }

    public void registerInboundFilter(Methods method, String path, BiConsumer<HttpRequest, HttpResponse> biConsumer) {
        inboundFilters.put(path, biConsumer);
    }

    public void registerOutboundFilter(Methods method, String path, BiConsumer<HttpRequest, HttpResponse> biConsumer) {
        outboundFilters.put(path, biConsumer);
    }

    public void addStaticContent(String location) {
        this.staticContentLocation = location;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        log.info("Server started");
        while(true) {
            try (Socket client = serverSocket.accept()) {
                HttpRequest request = new HttpRequest();
                request.readFromSocket(client);
                HttpResponse response = new HttpResponse();
                boolean isDynamic = endPoints.containsKey(("/" + request.getPath()).replaceAll("/{2,}", "/"));
                boolean notFiltered = true;
                for(String regex : inboundFilters.keySet()) {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(("/" + request.getPath()).replaceAll("/{2,}", "/"));
                    if (matcher.matches()) {
                        notFiltered = false;
                        BiConsumer<HttpRequest, HttpResponse> biConsumer = inboundFilters.get(regex);
                        biConsumer.accept(request, response);
//                        if (response.isFinal()) break;
                            // todo: se puede tener mas de un filtro
                        break;
                    }
                }
                //for(String regex : outboundFilters.keySet()) {
                //    Pattern pattern = Pattern.compile(regex);
                //    Matcher matcher = pattern.matcher(("/" + request.getPath()).replaceAll("/{2,}", "/"));
                //    if (matcher.matches()) {
                //        notFiltered = false;
                //        BiConsumer<HttpRequest, HttpResponse> biConsumer = outboundFilters.get(regex);
                //        biConsumer.accept(request, response);
                //        if (response.isFinal()) break;
                //        // todo: se puede tener mas de un filtro
                //    }
                //}

                // todo si el response ya isFinal() enmtonces no se tiene que mas llamar a un endpoint ni a un outbound filter,
                //  ni a el contenido estatico, se tiene  que retornar ya al cliente.

                if (isDynamic && notFiltered) {
                    BiConsumer<HttpRequest, HttpResponse> biConsumer = endPoints.get(("/" + request.getPath()).replaceAll("/{2,}", "/"));
                    biConsumer.accept(request, response);
                } else if (notFiltered) {
                    processRequest(request, response, staticContentLocation);
                }

                // todo recorrer los outboundFilters para agregar cabeceras...

                byte[] responseBytes = response.buildResponse();

                client.getOutputStream().write(responseBytes);
                client.getOutputStream().flush();
            } catch (IOException e) {
                log.error("Fail in start method: " + e);
            }
        }
    }
}
