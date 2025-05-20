package io.jona;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method; //esto tiene que ser un enum con dos valores iniciales GET y POST
    private String path;
    private String protocol; //esto tiene que ser un enum con dos valores iniciales HTTP_1_1 y UNSUPPORTED
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();

    public String getProtocol() {
        return protocol;
    }

    public String getMethod() {
       return method;
    }

    public String getPath() {
        return path;
    }

    public Map getHeaders() {
        return headers;
    }

    public void readFromSocket(Socket client) throws IOException {
        InputStream inputStream = client.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        System.out.println(line);
        int i = 0;
        while (line.charAt(i) != ' ') {
            method = line.substring(0, i + 1);
            i++;
        }
        i++;
        int j = i;
        while (line.charAt(j) != ' ') {
            path = line.substring(i, j);
            j++;
        }
        j++;
        int k = j;
        while (k < line.length()) {
            protocol = line.substring(j, k + 1);
            k++;
        }
        while(true) {
            line = bufferedReader.readLine();
            String[] keyAndValue = line.split(": ", 2);
            System.out.println(String.join(" ", keyAndValue));
            if(line == null || line.isEmpty())
                break;
            headers.put(keyAndValue[0], keyAndValue[1]);
        }
    }

    @Override
    public String toString() { //hacer que se vea lindo
        final StringBuilder sb = new StringBuilder("HttpRequest{\n");
        sb.append("method='\n").append(method).append('\'');
        sb.append(", path='\n").append(path).append('\'');
        sb.append(", protocol=\n'").append(protocol).append('\'');
        sb.append(", queryParams=\n").append(queryParams);
        sb.append(", headers=\n").append(headers);
        sb.append('}');
        return sb.toString();
    }
}
