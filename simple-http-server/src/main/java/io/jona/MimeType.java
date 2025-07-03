package io.jona;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MimeType {
    TEXT_HTML("text/html", ".html", true),
    TEXT_CSS("text/css", ".css", true),
    TEXT_JAVASCRIPT("text/javascript", ".js", true),
    TEXT_PLAIN("text/plain", ".txt", true),
    FAVICON("image/x-icon", ".ico", false),
    IMAGE_PNG("image/png", ".png", false),
    IMAGE_JPG("image/jpeg", ".jpg", false),
    VIDEO_MP4("video/mp4", ".mp4", false),
    UNKNOW("", "", false);

    private static final Logger logger = LoggerFactory.getLogger(MimeType.class);

    public final String value;
    public final String extension;
    public final boolean requiresEncoding;

    MimeType(String value, String extension, boolean requiresEncoding) {
        this.value = value;
        this.extension = extension;
        this.requiresEncoding = requiresEncoding;
    }

    public static MimeType getMimeForExtension(String extension) {
        for (MimeType mimeType : MimeType.values()) {
            if (mimeType.extension.equals(extension)) {
                return mimeType;
            }
        }
        return UNKNOW;
    }
}
