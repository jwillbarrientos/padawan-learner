package io.jona.framework;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class HttpRequest {
    @Setter
    private Methods method;
    @Setter @Getter
    private String path;
    @Setter
    private Protocols protocol;
    @Getter
    private Map<String, String> queryParams = new LinkedHashMap<>();
    @Getter
    private Map<String, String> headers = new LinkedHashMap<>();
    @Setter @Getter
    private long range;

    public void readFromSocket(Socket client) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.US_ASCII));
        String line = bufferedReader.readLine();
        log.trace(line);
        String[] methodPathProtocol = line.split(" ", 3);
        setMethod(Methods.valueOf(methodPathProtocol[0]));
        path = methodPathProtocol[1];
        path = path.startsWith("/")? path.substring(1) : path;
        setProtocol(methodPathProtocol[2].equals(Protocols.HTTP_1_1.desc)? Protocols.HTTP_1_1 : Protocols.UNSUPPORTED);
        boolean containsQueryParams = false;
        String[] queryAndParamsTogether = new String[0];
        if (methodPathProtocol[1].contains("?")) {
            String[] pathQueryParams = methodPathProtocol[1].split("\\?", 2);
            setPath(pathQueryParams[0]);
            queryAndParamsTogether = pathQueryParams[1].split("&");
            containsQueryParams = true;
        }
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
                long sof = Long.parseLong(startStr);
                setRange(sof);
            }
        }
        if (containsQueryParams && queryAndParamsTogether.length > 1) {
            int i = 0;
            while (i < queryAndParamsTogether.length) { //initialize queryParams
                log.debug(String.join(" ", queryAndParamsTogether));
                String[] queryAndParamsSeparate = queryAndParamsTogether[i].split("=", 2);
                String key = URLDecoder.decode(queryAndParamsSeparate[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(queryAndParamsSeparate[1], StandardCharsets.UTF_8);
                queryParams.put(key, value);
                i++;
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
