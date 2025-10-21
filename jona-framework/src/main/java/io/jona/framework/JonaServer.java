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
    private final Map<String, Function<HttpRequest, HttpResponse>> endPoints = new LinkedHashMap<>();
    private final Map<String, Function<HttpRequest, HttpResponse>> inboundFilters = new LinkedHashMap<>();

    public JonaServer() {
        this(8080);
    }

    public JonaServer(int port) {
        this.port = port;
    }

    // todo en vez de fuinction, se tiene q usar un void biConsumer<request,response>
    public void registerInboundFilter(Methods method, String path, Function<HttpRequest, HttpResponse> function) {
        inboundFilters.put(path, function);
    }

    public void registerOutboundFilter(Methods method, String path, BiConsumer<HttpRequest, HttpResponse> biConsumer) {

    }

    public void registerEndPoint(Methods method, String path, Function<HttpRequest, HttpResponse> function) {
        endPoints.put(path, function);
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
                        Function<HttpRequest, HttpResponse> function = inboundFilters.get(regex);
                        response = function.apply(request); //response = function.acceptg(request, response);
//                        if (response.isFinal()) break
                        break;// todo: se puede tener mas de un filtro
                    }
                }

                // todo si el response ya isFinal() enmtonces no se tiene que mas llamar a un endpoint ni a un outbound filter,
                //  ni a el contenido estatico, se tiene  que retornar ya al cliente.

                if (isDynamic && notFiltered) {
                    Function<HttpRequest, HttpResponse> function = endPoints.get(("/" + request.getPath()).replaceAll("/{2,}", "/"));
                    response = function.apply(request);
                } else if (notFiltered) {
                    processRequest(request, response, staticContentLocation, endPoints);
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
