package io.jona.framework;

public enum HttpCodes {

    OK_200(200, "Accepted"),
    NO_CONTENT_204(204, "No Content"),
    PARTIAL_CONTENT_206(206, "Partial Content"),
    UNAUTHORIZED_401(401, "Unauthorized"),
    NOT_FOUND_404(404, "Not Found"),
    CONFLICT_409(409, "Conflict");

    public final int code;
    public final String desc;

    HttpCodes(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
