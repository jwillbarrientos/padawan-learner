package io.jona;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static io.jona.Main.CONTENT_ROOT;

public class HttpResponse {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
    private ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
    private String protocol = "HTTP/1.1";
    private Path path;
    private HttpCodes responseCode;
    private String date = now.format(formatter);
    private String serverName = "jonatitoServer";
    private String contentType;
    private String range;
    private byte[] body;

    public void setPath(String path) { this.path = Paths.get(path); }
    public Path getPath() { return this.path; }
    public void setResponseCode(HttpCodes responseCode) { this.responseCode = responseCode; }
    public void setContentType(MimeType mimeType, String charset) { this.contentType = mimeType.value + "; charset=" + charset; }
    public void setContentType(MimeType mimeType) { this.contentType = mimeType.value; }
    public String getContentType() { return this.contentType; }
    public void setRange(String range) { this.range = range; }
    public void setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
        System.out.println("Body for response:\n"+ body);
    }
    public void setBody(byte[] body) {
        this.body = body;
    }

    byte[] buildResponse() {
        String headers;
        if(this.contentType.equals("video/mp4")) {
            String bytesRange = this.range.substring("bytes=".length());
            String startStr = bytesRange.split("-")[0];
            long sof = Long.parseLong(startStr);
            long eof = Long.parseLong(startStr);
            if((eof + 8191) < body.length) {
                eof = eof + 8191;
            } else {
                eof = body.length - 1;
            }
            long contentLength = eof - sof + 1;
            headers = """
                    %s %s %s
                    Date: %s
                    Server: %s
                    Content-Type: %s
                    Content-Range: %s%d/%d
                    Content-Length: %d
                    Accept-Ranges: bytes
                    
                    """.formatted(protocol, responseCode.code, responseCode.desc, date, serverName, contentType, range, eof, body.length, contentLength);
        } else {
            headers = """
                    %s %s %s
                    Date: %s
                    Server: %s
                    Content-Type: %s
                    Content-Length: %s
                    
                    """.formatted(protocol, responseCode.code, responseCode.desc, date, serverName, contentType, body.length);
        }

        byte[] headerBytes = headers.getBytes(StandardCharsets.US_ASCII);
        System.out.println("Response HEADERS: \n"+ headers);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if(!(headers.contains("Range"))) {
                baos.write(headerBytes);
                baos.write(body);
            } else {
                //InputStream fileIn = new FileInputStream(getPath());
                //byte[] buffer = new byte[8192];
                //int bytesRead;
                //while ((bytesRead = this.getPath().toFile().read) != -1) {
                //    baos.write(buffer, 0, bytesRead);
                //}
            }
        } catch (IOException ignored) {

        }
        byte[] output = baos.toByteArray();
        return output;
    }
}
