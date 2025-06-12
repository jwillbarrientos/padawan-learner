package io.jona;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
    private Methods method;
    private String path;
    private Protocols protocol;
    private Map<String, String> queryParams = new LinkedHashMap<>();
    private Map<String, String> headers = new LinkedHashMap<>();
    private String range;

    public void setMethod(Methods method) { this.method = method; }
    public void setProtocol(Protocols protocol) { this.protocol = protocol; }
    public Protocols getProtocol() {
        return protocol;
    }
    public Methods getMethod() {
       return method;
    }
    public String getPath() { return path; }
    public Map<String, String> getHeaders() { return headers; }
    public void setRange(String range) { this.range = range; }
    public String getRange() { return this.range; }

    public void readFromSocket(Socket client) throws IOException {
        InputStream inputStream = client.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        System.out.println(line);
        String[] methodPathProtocol = line.split(" ", 3);
        method = Methods.valueOf(methodPathProtocol[0]);
        path = methodPathProtocol[1];
        path = path.startsWith("/")? path.substring(1) : path;
        protocol = methodPathProtocol[2].equals(Protocols.HTTP_1_1.desc)? Protocols.HTTP_1_1 : Protocols.UNSUPPORTED;
        boolean containsQueryParams = false;
        String[] queryAndParamsTogether = new String[0];
        if (methodPathProtocol[1].contains("\\?")) {
            String[] pathQueryParams = methodPathProtocol[1].split("\\?", 2);
            path = pathQueryParams[0];
            queryAndParamsTogether = pathQueryParams[1].split("&");
            containsQueryParams = true;
        }
        while (true) { //initialize headers
            line = bufferedReader.readLine();
            System.out.println(line);
            String[] keyAndValue = line.split(": ", 2);
            if (line.isEmpty())
                break;
            headers.put(keyAndValue[0], keyAndValue[1]);
            if (keyAndValue[0].equals("Range"))
                setRange(keyAndValue[1]);
        }
        if (containsQueryParams && queryAndParamsTogether.length > 1) {
            int i = 0;
            while (i < queryAndParamsTogether.length) { //initialize queryParams
                System.out.println(String.join(" ", queryAndParamsTogether));
                String[] queryAndParamsSeparate = queryAndParamsTogether[i].split("=", 2);
                queryParams.put(queryAndParamsSeparate[0], queryAndParamsSeparate[1]);
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
