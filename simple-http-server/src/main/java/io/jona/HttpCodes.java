package io.jona;

public enum HttpCodes {

    OK_200(200, "OK"),
    NOT_FOUND_404(404, "NOT FOUND");

    public final int code;
    public final String desc;

    HttpCodes(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
