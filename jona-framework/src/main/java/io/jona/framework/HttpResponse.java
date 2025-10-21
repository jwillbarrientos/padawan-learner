package io.jona.framework;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
    private static ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
    private final String date = now.format(formatter);
    public static final long CHUNK_SIZE_BYTES = 2561024;

    private String protocol = "HTTP/1.1";
    @Setter
    private HttpCodes responseCode;
    private String serverName = "jonatitoServer";
    private String contentType;
    @Setter
    private long range;
    @Setter @Getter
    private long startOfFile;
    @Setter @Getter
    private long enfOfFile;
    @Setter @Getter
    private long totalFileSize;
    @Getter
    private byte[] body;
    private boolean login;
    private boolean signUp;
    private HashMap<String, String> cookies;
    private boolean deleteCookies;

    public void setContentType(MimeType mimeType, String charset) {
        this.contentType = mimeType.value + "; charset=" + charset;
    }

    public void setContentType(MimeType mimeType) {
        this.contentType = mimeType.value;
    }

    public void setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
        log.trace("Body for response:\n {}", body);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setBody(Path body, long starPositionOfReading, long endPositionOfReading) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(body.toFile(), "r")) {
            long fileLength = file.length();
            if (endPositionOfReading > fileLength) {
                endPositionOfReading = fileLength - 1;
            }
            long chunkSize = endPositionOfReading - starPositionOfReading + 1;

            if (chunkSize < 0 || chunkSize > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Invalid chunk size: " + chunkSize);
            }

            byte[] buffer = new byte[(int) chunkSize];
            file.seek(starPositionOfReading);
            file.readFully(buffer);
            this.body = buffer; // assign to the response body
        }
    }

    byte[] buildResponse() {
        String headers = protocol + " " + responseCode.code + " " + responseCode.desc + "\n";
        if(cookies != null && !deleteCookies) {
            for (Map.Entry<String, String> cookie : cookies.entrySet()) {
                String cookieString = cookie.getKey() + "=" + cookie.getValue();
                headers += "Set-Cookie: " + cookieString + "\n";
            }
        } else if (deleteCookies) {
            headers += "Clear-Site-Data: \"cookies\"\n";
        }

        if (body != null && contentType != null && contentType.equals("video/mp4")) {
            headers += """
                    Date: %s
                    Server: %s
                    Content-Type: %s
                    Content-Range: bytes %s-%d/%d
                    Content-Length: %s
                    Accept-Ranges: bytes
                    
                    """
                    .formatted(
                            date,
                            serverName,
                            contentType,
                            startOfFile,
                            enfOfFile,
                            totalFileSize,
                            this.body.length
                    );
        } else if (body != null){
            headers += """
                    Date: %s
                    Server: %s
                    Content-Type: %s
                    Content-Length: %s
                    
                    """
                    .formatted(
                            date, serverName,
                            contentType,
                            body.length
                    );
        } else {
            headers += """
                    Date: %s
                    Server: %s
                    
                    """
                    .formatted(
                            date, serverName
                    );
        }
        headers = headers.replace("\n", "\r\n");

        byte[] headerBytes = headers.getBytes(StandardCharsets.US_ASCII);
        log.debug("Status: {}", responseCode.code);
        log.trace("Response HEADERS: \n {}", headers);
        if(body == null)
            return headerBytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(headerBytes);
            baos.write(this.body);
        } catch (IOException ignored) {
            log.error("Fallo tratando de escribir el archivo");
        }
        return baos.toByteArray();
    }
}
