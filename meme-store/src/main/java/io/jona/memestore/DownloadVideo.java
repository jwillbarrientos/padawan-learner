package io.jona.memestore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

public class DownloadVideo {
    private static final Logger logger = LoggerFactory.getLogger(DownloadVideo.class);
    private static Platforms platform;
    private static Executables exe;
    private static final Properties props = new Properties();
    private static Process process;
    public static void main(String[] args) {
        //https://www.tiktok.com/@gertu0k/video/7258798530066713862
        //https://www.instagram.com/reel/DM2mUqnKUYS/?igsh=MWNuOG5leXRjajE0dg==
        //https://www.youtube.com/watch?v=lWHYJOayAUg
        //https://youtu.be/lWHYJOayAUg?si=UgHR7MhnOECnnluj
        //https://www.facebook.com/reel/718363154596932
        runDownloader("https://www.tiktok.com/@gertu0k/video/7258798530066713862");
    }

    public static void runDownloader(String url) {
        platform = Platforms.whatPlatformIs(url);
        if(platform == Platforms.YOU_TUBE)
            exe = Executables.YOUTUBE_DL;
        else
            exe = Executables.YT_DLP;
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
            process = Runtime.getRuntime().exec(command);
            new Thread(DownloadVideo::printProcessOutput, "OutputThread").start();
            new Thread(DownloadVideo::printProcessErr, "ErrThread").start();
            process.waitFor();
            logger.debug("Download finished");
        } catch (Exception e) {
            logger.error("Download failed", e);
        }
    }

    public static void printProcessOutput() {
        logger.trace("Entering printProcessOutput() with param: {}", process);
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while((line = reader.readLine()) != null) {
                logger.debug(exe.name() + ": {}", line);
            }
        } catch (IOException e) {
            logger.error(exe.name() + ": IOException reading the InputStream of process", e);
        }
        logger.debug("Printing InputStream of process without exception");
    }

    public static void printProcessErr() {
        logger.trace("Entering printProcessErr() with param: {}", process);
        try(BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()))) {
            String line;
            while((line = errorReader.readLine()) != null) {
                logger.error(exe.name() + ": {}", line);
            }
        } catch (IOException e) {
            logger.error(exe.name() + ": IOException reading the ErrorStream of process", e);
        }
        logger.debug("Printing ErrorStream of process without Exception");
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
