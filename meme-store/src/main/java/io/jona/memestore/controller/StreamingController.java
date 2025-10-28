package io.jona.memestore.controller;

import io.jona.framework.*;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Video;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamingController {
    private final Map<String, Client> sessionCookies;

    public StreamingController(HashMap<String, Client> sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public void loadVideos(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        List<Video> listVideos = JonaDb.selectList("select id, name, link, path, duration_seconds, video_state, date, client_id from video where client_id = ?",
                Video.getFullMapping(), client.getId());
        for (int i = 0; i < 4; i++) {
            listVideos.get(i).getPath();
        }
        response.setContentType(MimeType.VIDEO_MP4);
        response.setResponseCode(HttpCodes.PARTIAL_CONTENT_206);
    }
}
