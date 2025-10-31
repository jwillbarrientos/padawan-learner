package io.jona.memestore;

import io.jona.framework.JonaDb;
import io.jona.memestore.dto.Video;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Slf4j
public class VideoHelper {
    public static Runnable setVideoWhenDownloaded() {
        return () -> {
            Video video = JonaDb.selectSingle("select id, name, link, path, duration_seconds, file_size, video_state, date, client_id from video where video_state = ?",
                    Video.getFullMapping(), Video.State.SUBMITTED.name());
            if (video != null) {
                String videoPath = DownloadVideo.downloadVideo(video.getLink());
                if (videoPath == null) {
                    video.setVideoState(Video.State.ERROR_DOWNLOADING);
                } else {
                    video.setName(Paths.get(videoPath).getFileName().toString());
                    video.setPath(videoPath);
                    video.setDurationSeconds(getSeconds(video.getLink()));
                    video.setFileSize((int) (new File(videoPath).length()));
                    video.setVideoState(Video.State.DOWNLOADED);
                }
                JonaDb.update(video);
            }
        };
    }

    public static int getSeconds(String link) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"./downloaders/yt-dlp.exe", "-j",  link, "--skip-download", "--print", "%(duration)s"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null && line.matches("^[0-9]+([0-9]\\.[0-9]+)?$")) {
                process.destroyForcibly();
                line = line.trim();
                double d = Double.parseDouble(line);
                return (int) Math.round(d);
            }
        } catch (IOException e) {
            log.error("Failed to parse duration: ", e);
        }
        return 0;
    }
}
