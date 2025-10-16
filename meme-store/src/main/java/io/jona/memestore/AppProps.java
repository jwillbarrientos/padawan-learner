package io.jona.memestore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProps {
    private static final Properties props = new Properties();
    static {
        try (FileInputStream fis = new FileInputStream("./app.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getYoutubeDlWin() {
        return props.getProperty("youtubeDl.win");
    }

    public String getYtDlWin() {
        return props.getProperty("ytDl.win");
    }

    public String getVideOutputPath() {
        return props.getProperty("videOutputPath");
    }

    public String getJdbcUrl() {
        return props.getProperty("jdbcUrl");
    }

    public String getDbUser() {
        return props.getProperty("dbUser");
    }
    public String getDbPassword() {
        return props.getProperty("dbPassword");
    }
}
