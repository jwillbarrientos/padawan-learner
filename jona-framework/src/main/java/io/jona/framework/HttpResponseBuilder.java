package io.jona.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseBuilder.class);

    private String protocol = "HTTP/1.1";
    private Path path;
    private HttpCodes responseCode;
    private String serverName = "jonatitoServer";
    private String contentType;
    private long range;
    private long startOfFile;
    private long enfOfFile;
    public static final long CHUNK_SIZE_BYTES = 2561024;
    private long totalFileSize;
    private byte[] body;
    private HashMap<String, String> cookies = new HashMap<>();
    private boolean deleteCookies;

    public HttpResponseBuilder setResponseCode(HttpCodes responseCode) {
        this.responseCode = responseCode;
        return this;
    }
    public HttpResponseBuilder setContentType(MimeType mimeType, String charset) {
        this.contentType = mimeType.value + "; charset=" + charset;
        return this;
    }
    public HttpResponseBuilder setContentType(MimeType mimeType) {
        this.contentType = mimeType.value;
        return this;
    }
    public HttpResponseBuilder setStartOfFile(long startOfFile) {
        this.startOfFile = startOfFile;
        return this;
    }
    public HttpResponseBuilder setEnfOfFile(long enfOfFile) {
        this.enfOfFile = enfOfFile;
        return this;
    }
    public HttpResponseBuilder setRange(long range) {
        this.range = range;
        return this;
    }
    public HttpResponseBuilder setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
        logger.trace("Body for response:\n {}", body);
        return this;
    }
    public HttpResponseBuilder setTotalFileSize(long totalFileSize) {
        this.totalFileSize = totalFileSize;
        return this;
    }
    public HttpResponseBuilder setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public HttpResponseBuilder setBody() {
        return this;
    }

    public HttpResponseBuilder addCookie(String key, String value) {
        cookies.put(key, value);
        return this;
    }

    public HttpResponseBuilder deleteCookies() {
        deleteCookies = true;
        return this;
    }

    public HttpResponse build() {
        return new HttpResponse(protocol, responseCode, serverName, contentType, range, startOfFile, enfOfFile, totalFileSize, body, cookies, deleteCookies);
    }
}
