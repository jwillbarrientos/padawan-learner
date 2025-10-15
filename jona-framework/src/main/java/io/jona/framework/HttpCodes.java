package io.jona.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum HttpCodes {

    OK_200(200, "Accepted"),
    NOT_FOUND_404(404, "Not Found"),
    NO_CONTENT_204(204, "No Content"),
    PARTIAL_CONTENT_206(206, "Partial Content");

    private static final Logger logger = LoggerFactory.getLogger(HttpCodes.class);

    public final int code;
    public final String desc;

    HttpCodes(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
