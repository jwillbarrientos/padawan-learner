package io.jona;

public enum MimeType {
    TEXT_HTML("text/html", ".html"),
    FAVICON("image/x-icon", ".ico"),
    TEXT_PLAIN("text/plain", ".txt");

    public final String value;
    public final String extension;

    MimeType(String value, String extension) {
        this.value = value;
        this.extension = extension;
    }
}
