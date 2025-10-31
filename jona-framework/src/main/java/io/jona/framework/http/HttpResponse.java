package io.jona.framework.http;

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
    public static final long CHUNK_SIZE_BYTES = 1024 * 64L;
    private static final String SERVER_NAME = "JonaServer";
    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

    private final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
    private final String date = now.format(ISO_DATE_FORMATTER);
    private final Map<HttpResponseHeader, String> responseHeaders = new HashMap<>();

    @Setter
    private HttpCode responseCode;
    private String contentType;
    private long headerRangeStart;
    private long headerRangeEnd;
    private long headerRangeTotal;
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

    public void setBodyWithRange(Path body, long headerRangeStart, long headerRangeEnd, long headerRangeTotal) throws IOException {
        this.headerRangeStart = headerRangeStart;
        this.headerRangeEnd = headerRangeEnd;
        this.headerRangeTotal = headerRangeTotal;
        try (RandomAccessFile file = new RandomAccessFile(body.toFile(), "r")) {
            long fileLength = file.length();
            if (headerRangeEnd > fileLength) {
                headerRangeEnd = fileLength - 1;
            }
            long chunkSize = headerRangeEnd - headerRangeStart + 1;

            if (chunkSize < 0 || chunkSize > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Invalid chunk size: " + chunkSize);
            }

            byte[] buffer = new byte[(int) chunkSize];
            file.seek(headerRangeStart);
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
        responseHeaders.put(HttpResponseHeader.DATE, date + "\r\n");
        responseHeaders.put(HttpResponseHeader.SERVER, SERVER_NAME + "\r\n");
        if (body != null) {
            responseHeaders.put(HttpResponseHeader.CONTENT_RANGE, body.length + "\r\n");
        } else {
            responseHeaders.put(HttpResponseHeader.CONTENT_RANGE, null);
        }

        if (contentType != null) {
            responseHeaders.put(HttpResponseHeader.CONTENT_TYPE, contentType + "\r\n");
            if (contentType.equals(MimeType.VIDEO_MP4.value) || contentType.equals(MimeType.VIDEO_WEBM.value) || contentType.equals(MimeType.VIDEO_OGG.value)) {
                responseHeaders.put(HttpResponseHeader.CONTENT_RANGE, "bytes " + headerRangeStart + "-" + headerRangeEnd + "/" + headerRangeTotal + "\r\n");
                responseHeaders.put(HttpResponseHeader.ACCEPT_RANGES, "bytes" + "\r\n");
            }
        } else {
            responseHeaders.put(HttpResponseHeader.CONTENT_TYPE, null);
        }

        if (noCache) {
            responseHeaders.put(HttpResponseHeader.CACHE_CONTROL, "no-store" + "\r\n");
        } else {
            responseHeaders.put(HttpResponseHeader.CACHE_CONTROL, null);
        }
    }

    public StringBuilder setAndGetHeaders() {
        String protocol = "HTTP/1.1";
        StringBuilder headers = new StringBuilder(protocol + " " + responseCode.code + " " + responseCode.desc + "\r\n");
        initHeaders();
        if (cookies != null && !deleteCookies) {
            for (Map.Entry<String, String> cookie : cookies.entrySet()) {
                String cookieString = cookie.getKey() + "=" + cookie.getValue();
                headers.append(HttpResponseHeader.SET_COOKIE.headerKey).append(cookieString).append("; Path=/\r\n");
            }
        } else if (deleteCookies) {
            headers.append(HttpResponseHeader.CLEAR_SITE_DATA.headerKey).append("\"cookies\"\r\n");
        }
        headers.append(HttpResponseHeader.DATE.headerKey).append(responseHeaders.get(HttpResponseHeader.DATE));
        headers.append(HttpResponseHeader.SERVER.headerKey).append(responseHeaders.get(HttpResponseHeader.SERVER));
        if (responseHeaders.get(HttpResponseHeader.CONTENT_RANGE) != null)
            headers.append(HttpResponseHeader.CONTENT_RANGE.headerKey).append(responseHeaders.get(HttpResponseHeader.CONTENT_RANGE));
        if (responseHeaders.get(HttpResponseHeader.CONTENT_TYPE) != null) {
            headers.append(HttpResponseHeader.CONTENT_TYPE.headerKey).append(responseHeaders.get(HttpResponseHeader.CONTENT_TYPE));
            if (contentType.equals(MimeType.VIDEO_MP4.value)) {
                headers.append(HttpResponseHeader.CONTENT_RANGE.headerKey).append(responseHeaders.get(HttpResponseHeader.CONTENT_RANGE));
                headers.append(HttpResponseHeader.ACCEPT_RANGES.headerKey).append(responseHeaders.get(HttpResponseHeader.ACCEPT_RANGES));
            }
        }
        if (responseHeaders.get(HttpResponseHeader.CACHE_CONTROL) != null)
            headers.append(HttpResponseHeader.CACHE_CONTROL.headerKey).append(responseHeaders.get(HttpResponseHeader.CACHE_CONTROL));
        headers.append("\r\n");
        return headers;
    }

    public byte[] buildResponse() {
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
