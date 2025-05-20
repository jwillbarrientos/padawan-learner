package io.jona;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class HttpResponse {
    private String protocol = "HTTP/1.1";
    private HttpCodes responseCode;
    private LocalDateTime date = LocalDateTime.now();
    private String serverName = "jonatitoServer";
    private String contentType; //tiene que ser un enum de mimetypes
    private byte[] body;

    public void setResponseCode(HttpCodes responseCode) {
        this.responseCode = responseCode;
    }

    public void setContentType(MimeType mimeType, String charset) {
        this.contentType = mimeType.value+"; "+ charset;
    }
    public void setContentType(MimeType mimeType) {
        this.contentType = mimeType.value;
    }


    public void setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }
    public void setBody(byte[] body) {
        this.body = body;
    }

    byte[] buildResponse() {
        String headers = """
                %s %s %s
                Date: %s
                Server: %s
                Content-Type: %s
                Content-Length: %s
                
                """.formatted(protocol, responseCode.code, responseCode.desc, date, serverName, contentType, body.length);

        byte[] headerBytes = headers.getBytes(StandardCharsets.US_ASCII);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(headerBytes);
            baos.write(body);
        } catch (IOException ignored) {

        }
        byte[] output = baos.toByteArray();
        return output;
    }



}
