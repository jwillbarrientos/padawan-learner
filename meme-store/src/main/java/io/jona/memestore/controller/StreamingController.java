package io.jona.memestore.controller;

import com.google.gson.Gson;
import io.jona.framework.*;
import io.jona.framework.http.HttpCode;
import io.jona.framework.http.HttpRequest;
import io.jona.framework.http.HttpResponse;
import io.jona.framework.http.MimeType;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Video;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class StreamingController {
    private final Map<String, Client> sessionCookies;

    public StreamingController(HashMap<String, Client> sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public void loadVideos(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);

        List<Video> listVideos = JonaDb.selectList("select id, name, link, path, duration_seconds, file_size, video_state, date, client_id from video where client_id = ? and video_state = 'DOWNLOADED' order by date desc limit " + 10,
                Video.getFullMapping(), client.getId());
        Gson gson = new Gson();
        String json = gson.toJson(listVideos);
        response.setContentType(MimeType.APPLICATION_JSON, "UTF-8");
        response.setResponseCode(HttpCode.OK_200);
        response.setBody(json.getBytes());
    }

    public void streamVideos(HttpRequest request, HttpResponse response) {
        String id = request.getQueryParams().get("id");
        Video video = JonaDb.selectSingle("select id, name, link, path, duration_seconds, file_size, video_state, date, client_id from video where id = ?",
                Video.getFullMapping(), id);
        Path path = Path.of(video.getPath());
        try {
            response.setResponseCode(HttpCode.PARTIAL_CONTENT_206);
            String extension = video.getPath().substring(video.getPath().lastIndexOf("."));
            MimeType mimeType = MimeType.getMimeForExtension(extension);
            response.setContentType(mimeType);
            long end = Math.min(request.getRangeStart() + HttpResponse.CHUNK_SIZE_BYTES - 1, path.toFile().length() - 1);
            response.setBodyWithRange(path, request.getRangeStart(), end, path.toFile().length());
        } catch (IOException e) {
            response.setResponseCode(HttpCode.CONFLICT_409);
            log.error("Error streaming the video ", e);
        }
    }
}
