package io.jona.memestore;

import io.jona.framework.JonaDb;
import io.jona.memestore.dto.Video;

import java.nio.file.Paths;

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
                    video.setDurationSeconds(0);
                    video.setVideoState(Video.State.DOWNLOADED);
                }
                JonaDb.update(video);
            }
        };
    }
}
