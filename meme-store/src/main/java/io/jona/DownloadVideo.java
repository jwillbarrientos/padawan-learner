package io.jona;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

public class DownloadVideo {
    private static final Logger logger = LoggerFactory.getLogger(DownloadVideo.class);
    private static final Properties props = new Properties();
    public static void main(String[] args) {
        //https://www.tiktok.com/@gertu0k/video/7258798530066713862
        //https://www.instagram.com/reel/DOWR1MPDaE4/?igsh=MTR6N3duemJmd3R0eA%3D%3D
        //https://www.youtube.com/shorts/Msn1xgzBwTg
        //https://www.facebook.com/reel/718363154596932
        runDownloader("https://www.facebook.com/reel/718363154596932");
    }

    public static void runDownloader(String url) {
        Platforms platform = Platforms.whatPlatformIs(url);
        String[] command = new String[4];
        logger.info("user.dir: "+ System.getProperty("user.dir"));
        try (FileInputStream fis = new FileInputStream("./app.properties")) {
            props.load(fis);
            String ytDl = props.getProperty("ytDl.win");
            String youtubeDl = props.getProperty("youtubeDl.win");
            String destinyFolder = props.getProperty("videOutputPath");
            switch (platform) {
                case FACEBOOK:
                    buildCommand(command, ytDl, destinyFolder, url);
                    break;
                case YOU_TUBE:
                    buildCommand(command, youtubeDl, destinyFolder, url);
                    break;
                case INSTAGRAM:
                    buildCommand(command, ytDl, destinyFolder, url);
                    break;
                case TIKTOK:
                    buildCommand(command, ytDl, destinyFolder, url);
                    break;
                default:
                    logger.debug("Invalid url");
                    return;
            }
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            logger.debug("Download finished");
        } catch (Exception e) {
            logger.error("Download failed", e);
        }
    }

    public static String[] buildCommand(String[] command, String downloader, String destinyFolder, String url) {
        logger.trace("Entering buildCommand() with params command={}, downloader={}, destinyFolder={}, url={}", command, downloader, destinyFolder, url);
        command[0] = downloader;
        command[1] = "-o";
        command[2] = destinyFolder + "/%(title)s.%(ext)s";
        command[3] = url;
        logger.trace("Returning full command of buildCommand() method: {}", Arrays.toString(command));
        return command;
    }
}
