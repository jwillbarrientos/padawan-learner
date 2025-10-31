package io.jona.framework.http;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequest {
    private Method method;
    @Getter
    private String path;
    private Protocol protocol;
    @Getter
    private final Map<String, String> queryParams = new HashMap<>();
    @Getter
    private final Map<String, String> headers = new HashMap<>();
    @Getter
    private final Map<String, String> cookies = new HashMap<>();
    @Getter
    private long rangeStart;
    @Getter
    private byte[] body;

    public HttpRequest(Socket client) throws IOException {
        this.readFromSocket(client);
    }

    public String canonicalPath() {
        return ("/" + this.path).replaceAll("/{2,}", "/");
    }

    private void readFromSocket(Socket client) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.US_ASCII));
        String line = bufferedReader.readLine();
        log.trace(line);

        String[] methodPathProtocol = extractPath(line);
        extractQueryParams(methodPathProtocol);
        extractHeaders(bufferedReader);
    }

    private String[] extractPath(String line) {
        String[] methodPathProtocol = line.split(" ", 3);
        method = Method.valueOf(methodPathProtocol[0]);
        path = methodPathProtocol[1];
        path = path.startsWith("/")? path.substring(1) : path;
        protocol = methodPathProtocol[2].equals(Protocol.HTTP_1_1.desc)? Protocol.HTTP_1_1 : Protocol.UNSUPPORTED;
        return methodPathProtocol;
    }

    private void extractHeaders(BufferedReader bufferedReader) throws IOException {
        String line;
        log.debug("Received {} request for: {}", method, path);
        while (true) { //initialize headers
            line = bufferedReader.readLine();
            log.trace(line);
            String[] keyAndValue = line.split(": ", 2);
            if (line.isEmpty())
                break;
            headers.put(keyAndValue[0], keyAndValue[1]);
            if (keyAndValue[0].equals("Range")) {
                String bytesRange = keyAndValue[1].substring("bytes=".length());
                String startStr = bytesRange.split("-")[0];
                rangeStart = Long.parseLong(startStr);
                continue;
            }
            if (keyAndValue[0].equals("Cookie")) {
                String[] cookiesHeader = keyAndValue[1].split("; ");
                for (String cookie : cookiesHeader) {
                    String[] cookieKeyAndValue = cookie.split("=", 2);
                    String cookieKey = cookieKeyAndValue[0];
                    String cookieValue = cookieKeyAndValue[1];
                    cookies.put(cookieKey, cookieValue);
                }
            }
        }
    }

    private void extractQueryParams(String[] methodPathProtocol) {
        boolean containsQueryParams = methodPathProtocol[1].contains("?");
        if (containsQueryParams) {
            String[] pathQueryParams = methodPathProtocol[1].split("\\?", 2);
            path = pathQueryParams[0];
            String[] queryAndParamsTogether = pathQueryParams[1].split("&");
            for (String kvPair : queryAndParamsTogether) { //initialize queryParams
                String[] queryAndParamsSeparate = kvPair.split("=", 2);
                String key = URLDecoder.decode(queryAndParamsSeparate[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(queryAndParamsSeparate[1], StandardCharsets.UTF_8);
                queryParams.put(key, value);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpRequest {\n");
        sb.append("method = ").append(method).append('\n');
        sb.append("path = ").append(path).append('\n');
        sb.append("protocol = ").append(protocol).append('\n');
        sb.append("queryParams = \n");
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
        }
        sb.append("headers = \n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
        }
        sb.append("}\n");
        return sb.toString();
    }
}
