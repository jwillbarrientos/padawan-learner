package io.jona;

public enum HttpCodes {
    OK_200(200, "Accepted"),
    NOT_FOUND_404(404, "Not Found"),
    PARTIAL_CONTENT_206(206, "Partial Content");

    public final int code;
    public final String desc;

    HttpCodes(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
