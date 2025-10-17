package io.jona.memestore;

import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class AppProps {
    private static final Properties props = new Properties();
    static {
        try (FileInputStream fis = new FileInputStream("./app.properties")) {
            props.load(fis);
        } catch (IOException e) {
            log.error("while loading app.properties", e);
            System.exit(-1 );
        }
    }

    public static String getYoutubeDlWin() {
        return props.getProperty("youtubeDl.win");
    }

    public static String getYtDlWin() {
        return props.getProperty("ytDl.win");
    }

    public static String getVideOutputPath() {
        return props.getProperty("videOutputPath");
    }

    public static String getJdbcUrl() {
        return props.getProperty("jdbcUrl");
    }

    public static String getDbUser() {
        return props.getProperty("dbUser");
    }

    public static String getDbPassword() {
        return props.getProperty("dbPassword");
    }
}
