package io.jona;

import java.io.FileInputStream;
import java.util.Properties;

public class DownloadVideo {
    private static final Properties props = new Properties();
    public static void main(String[] args) {
        //https://www.facebook.com/reel/718363154596932
        runDownloader("https://www.youtube.com/shorts/Msn1xgzBwTg");
    }

    public static void runDownloader(String url) {
        Platforms platform = Platforms.whatPlatformIs(url);
        String[] command = new String[4];


        System.out.println("user.dir: "+ System.getProperty("user.dir"));

        try (FileInputStream fis = new FileInputStream("./app.properties")) {

            String ytDl = props.getProperty("ytDl.win");
            String youtubeDl = props.getProperty("youtubeDl.win");
            String destinyFolder = props.getProperty("videOutputPath");
            props.load(fis);
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
                    System.out.println("Invalid url");
                    return;
            }
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            System.out.println("Download finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] buildCommand(String[] command, String downloader, String destinyFolder, String url) {
        command[0] = downloader;
        command[1] = "-o";
        command[2] = destinyFolder + "/%(title)s.%(ext)s";
        command[3] = url;
        return command;
    }
}
