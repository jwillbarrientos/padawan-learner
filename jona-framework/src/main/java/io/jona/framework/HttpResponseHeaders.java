package io.jona.framework;

import lombok.Getter;

public enum HttpResponseHeaders {
    DATE("Date: "), SERVER("Server: "), CONTENT_TYPE("Content-Type: "),
    CONTENT_RANGE("Content-Range: "), CONTENT_LENGTH("Content-Length: "),
    ACCEPT_RANGES("Accept-Ranges: "), SET_COOKIE("Set-Cookie: "),
    CLEAR_SITE_DATA("Clear-Site-Data: "), CACHE_CONTROL("Cache-Control: ");

    @Getter
    private final String headerKey;

    private HttpResponseHeaders(String headerKey) {
        this.headerKey = headerKey;
    }
}
