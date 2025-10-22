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
    private final Map<HttpResponseHeaders, String> responseHeaders = new HashMap<>();

    private String protocol = "HTTP/1.1";
    @Setter
    private HttpCodes responseCode;
    private String serverName = "JonaServer";
    private String contentType;
    @Setter @Getter
    private long startOfFile;
    @Setter @Getter
    private long enfOfFile;
    @Setter @Getter
    private long totalFileSize;
    @Getter
    private byte[] body;
    @Getter
    private Map<String, String> cookies = new HashMap<>();
    private boolean deleteCookies;
    @Getter
    private boolean isFinal;
    private boolean noCache;

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

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public void deleteCookies() {
        this.deleteCookies = true;
    }

    public void setFinal() {
        this.isFinal = true;
    }

    public void noCache() {
        this.noCache = true;
    }

    public void initHeaders() {
        responseHeaders.put(HttpResponseHeaders.DATE, date + "\r\n");
        responseHeaders.put(HttpResponseHeaders.SERVER, serverName + "\r\n");
        if (body != null) {
            responseHeaders.put(HttpResponseHeaders.CONTENT_RANGE, body.length + "\r\n");
        } else {
            responseHeaders.put(HttpResponseHeaders.CONTENT_RANGE, null);
        }

        if (contentType != null) {
            responseHeaders.put(HttpResponseHeaders.CONTENT_TYPE, contentType + "\r\n");
            if (contentType.equals(MimeType.VIDEO_MP4.value)) {
                responseHeaders.put(HttpResponseHeaders.CONTENT_RANGE, "bytes " + startOfFile + "-" + enfOfFile + "/" + totalFileSize + "\r\n");
                responseHeaders.put(HttpResponseHeaders.ACCEPT_RANGES, "bytes" + "\r\n");
            }
        } else {
            responseHeaders.put(HttpResponseHeaders.CONTENT_TYPE, null);
        }

        if (noCache) {
            responseHeaders.put(HttpResponseHeaders.CACHE_CONTROL, "no-store" + "\r\n");
        } else {
            responseHeaders.put(HttpResponseHeaders.CACHE_CONTROL, null);
        }
    }

    public StringBuilder setAndGetHeaders() {
        StringBuilder headers = new StringBuilder(protocol + " " + responseCode.code + " " + responseCode.desc + "\r\n");
        initHeaders();
        if (cookies != null && !deleteCookies) {
            for (Map.Entry<String, String> cookie : cookies.entrySet()) {
                String cookieString = cookie.getKey() + "=" + cookie.getValue();
                headers.append(HttpResponseHeaders.SET_COOKIE.headerKey).append(cookieString).append("; Path=/\r\n");
            }
        } else if (deleteCookies) {
            headers.append(HttpResponseHeaders.CLEAR_SITE_DATA.headerKey).append("\"cookies\"\r\n");
        }
        headers.append(HttpResponseHeaders.DATE.headerKey).append(responseHeaders.get(HttpResponseHeaders.DATE));
        headers.append(HttpResponseHeaders.SERVER.headerKey).append(responseHeaders.get(HttpResponseHeaders.SERVER));
        if (responseHeaders.get(HttpResponseHeaders.CONTENT_RANGE) != null)
            headers.append(HttpResponseHeaders.CONTENT_RANGE.headerKey).append(responseHeaders.get(HttpResponseHeaders.CONTENT_RANGE));
        if (responseHeaders.get(HttpResponseHeaders.CONTENT_TYPE) != null) {
            headers.append(HttpResponseHeaders.CONTENT_TYPE.headerKey).append(responseHeaders.get(HttpResponseHeaders.CONTENT_TYPE));
            if (contentType.equals(MimeType.VIDEO_MP4.value)) {
                headers.append(HttpResponseHeaders.CONTENT_RANGE.headerKey).append(responseHeaders.get(HttpResponseHeaders.CONTENT_RANGE));
                headers.append(HttpResponseHeaders.ACCEPT_RANGES.headerKey).append(responseHeaders.get(HttpResponseHeaders.ACCEPT_RANGES));
            }
        }
        if (responseHeaders.get(HttpResponseHeaders.CACHE_CONTROL) != null)
            headers.append(HttpResponseHeaders.CACHE_CONTROL.headerKey).append(responseHeaders.get(HttpResponseHeaders.CACHE_CONTROL));
        headers.append("\r\n");
        return headers;
    }

    byte[] buildResponse() {
        StringBuilder headers = setAndGetHeaders();
        byte[] headerBytes = headers.toString().getBytes(StandardCharsets.US_ASCII);
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
