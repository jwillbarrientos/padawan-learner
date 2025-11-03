package io.jona.memestore.controller;

import io.jona.framework.http.HttpCode;
import io.jona.framework.http.HttpRequest;
import io.jona.framework.http.HttpResponse;
import io.jona.framework.JonaDb;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Video;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class VideoController {
    private final Map<String, Client> sessionCookies;

    public VideoController(HashMap<String, Client> sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public void addVideoByLink(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        Video video = new Video(request.getQueryParams().get("link"), Video.State.SUBMITTED, client.getId());
        JonaDb.insert(video);
        response.setResponseCode(HttpCode.OK_200);
    }

    public void addVideoByFile(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
    }
}
