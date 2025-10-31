package io.jona.memestore;

import io.jona.framework.JonaDb;
import io.jona.memestore.dto.Video;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Slf4j
public class VideoHelper {
    public static Runnable setVideoWhenDownloaded() {
        return () -> {
            Video video = JonaDb.selectSingle("select id, name, link, path, duration_seconds, video_state, date, client_id from video where video_state = ?",
                    Video.getFullMapping(), Video.State.SUBMITTED.name());
            if (video != null) {
                String videoPath = DownloadVideo.downloadVideo(video.getLink());
                if (videoPath == null) {
                    video.setVideoState(Video.State.ERROR_DOWNLOADING);
                } else {
                    video.setName(Paths.get(videoPath).getFileName().toString());
                    video.setPath(videoPath);
                    //video.setDurationSeconds(getSeconds(video.getLink()));
                    video.setVideoState(Video.State.DOWNLOADED);
                }
                JonaDb.update(video);
            }
        };
    }

    //public static int getSeconds(String link) {
    //    try {
    //        Process process = Runtime.getRuntime().exec("yt-dlp -j \"" + link + "\" --skip-download --print \"%(duration)s\"");
    //        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    //        String line = reader.readLine();
    //        process.waitFor();
    //        if (line != null && !line.isEmpty()) {
    //            return Integer.parseInt(line.trim());
    //        }
    //    } catch (IOException | InterruptedException e) {
    //        log.error("Failed to parse duration: ", e);
    //    }
    //    return 0;
    //}
}
