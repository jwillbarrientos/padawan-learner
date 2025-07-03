package io.jona;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Protocols {
    HTTP_1_1("HTTP/1.1"),
    UNSUPPORTED("UNSUPPORTED");

    private static final Logger logger = LoggerFactory.getLogger(Protocols.class);

    public final String desc;

    Protocols(String desc) {
        this.desc = desc;
    }
}
