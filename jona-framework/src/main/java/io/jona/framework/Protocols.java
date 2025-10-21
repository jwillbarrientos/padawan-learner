package io.jona.framework;

public enum Protocols {
    HTTP_1_1("HTTP/1.1"),
    UNSUPPORTED("UNSUPPORTED");

    public final String desc;

    Protocols(String desc) {
        this.desc = desc;
    }
}
