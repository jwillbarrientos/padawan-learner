package io.jona.simplehttpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
    private ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
    private String protocol = "HTTP/1.1";
    private Path path;
    private HttpCodes responseCode;
    private String date = now.format(formatter);
    private String serverName = "jonatitoServer";
    private String contentType;
    private long range;
    private long startOfFile;
    private long enfOfFile;
    public static final long CHUNK_SIZE_BYTES = 2561024;
    long totalFileSize;
    private byte[] body;

    public void setPath(String path) { this.path = Paths.get(path); }
    public void setResponseCode(HttpCodes responseCode) { this.responseCode = responseCode; }
    public void setContentType(MimeType mimeType, String charset) { this.contentType = mimeType.value + "; charset=" + charset; }
    public void setContentType(MimeType mimeType) { this.contentType = mimeType.value; }
    public String getContentType() { return this.contentType; }
    public void setStartOfFile(long startOfFile) { this.startOfFile = startOfFile; }
    public long getStartOfFile() { return this.startOfFile; }
    public void setEnfOfFile(long enfOfFile) { this.enfOfFile = enfOfFile; }
    public long getEnfOfFile() { return this.enfOfFile; }
    public void setRange(long range) { this.range = range; }
    public void setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
        logger.trace("Body for response:\n {}", body);
    }
    public void setTotalFileSize(long totalFileSize) { this.totalFileSize = totalFileSize; }
    public long getTotalFileSize() { return this.totalFileSize; }
    public void setBody(byte[] body) { this.body = body; }
    public byte[] getBody() { return this.body; }

//    todo: added by Alan
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
        String headers;
        if(this.contentType.equals("video/mp4")) {
            headers = """
                    %s %s %s
                    Date: %s
                    Server: %s
                    Content-Type: %s
                    Content-Range: bytes %s-%d/%d
                    Content-Length: %s
                    Accept-Ranges: bytes
                    
                    """
                    .formatted(
                            protocol,
                            responseCode.code,
                            responseCode.desc,
                            date,
                            serverName,
                            contentType,
                            startOfFile,
                            enfOfFile,
                            totalFileSize,
                            this.body.length
                    );
        } else {
            headers = """
                    %s %s %s
                    Date: %s
                    Server: %s
                    Content-Type: %s
                    Content-Length: %s
                    
                    """
                    .formatted(
                            protocol,
                            responseCode.code,
                            responseCode.desc,
                            date, serverName,
                            contentType,
                            body.length
                    );
        }
        headers = headers.replace("\n", "\r\n");

        byte[] headerBytes = headers.getBytes(StandardCharsets.US_ASCII);
        logger.debug("Status: {}", responseCode.code);
        logger.trace("Response HEADERS: \n {}", headers);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
                baos.write(headerBytes);
                baos.write(this.body);
        } catch (IOException ignored) {
            logger.error("Fallo tratando de escribir el archivo");
        }
        return baos.toByteArray();
    }
}
