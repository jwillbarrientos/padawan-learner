package io.jona.memestore.controller;

import com.google.gson.Gson;
import io.jona.framework.*;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Video;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
        List<Video> listVideos = JonaDb.selectList("select id, name, link, path, duration_seconds, file_size, video_state, date, client_id from video where client_id = ? order by 7 desc limit " + 10,
                Video.getFullMapping(), client.getId());
        Gson gson = new Gson();
        String json = gson.toJson(listVideos);
        response.setContentType(MimeType.APPLICATION_JSON, "UTF-8");
        response.setResponseCode(HttpCodes.OK_200);
        response.setBody(json.getBytes());
    }

    public void streamVideos(HttpRequest request, HttpResponse response) {
        String id = request.getQueryParams().get("id");
        Video video = JonaDb.selectSingle("select id, name, link, path, duration_seconds, file_size, video_state, date, client_id from video where id = ?",
                Video.getFullMapping(), id);
        Path path = Path.of(video.getPath());
        try {
            response.setResponseCode(HttpCodes.PARTIAL_CONTENT_206);
            response.setStartOfFile(request.getRange());
            response.setTotalFileSize(video.getFileSize());
            long end = Math.min(response.getStartOfFile() + HttpResponse.CHUNK_SIZE_BYTES - 1, response.getTotalFileSize() - 1);
            response.setEnfOfFile(end);
            String extension = video.getPath().substring(video.getPath().lastIndexOf("."));
            MimeType mimeType = MimeType.getMimeForExtension(extension);
            response.setContentType(mimeType);
            response.setBody(path, response.getStartOfFile(), response.getEnfOfFile());
        } catch (IOException e) {
            response.setResponseCode(HttpCodes.CONFLICT_409);
            log.error("Error streaming the video ", e);
        }
    }
}
