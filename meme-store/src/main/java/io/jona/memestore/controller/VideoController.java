package io.jona.memestore.controller;

import io.jona.framework.HttpCodes;
import io.jona.framework.HttpRequest;
import io.jona.framework.HttpResponse;
import io.jona.memestore.DownloadVideo;
import io.jona.memestore.dto.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class VideoController {
    private final Map<String, Client> sessionCookies;

    public VideoController(HashMap<String, Client> sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public void addVideoByLink(HttpRequest request, HttpResponse response) {
        DownloadVideo.downloadVideo(request.getQueryParams().get("link"));
        response.setResponseCode(HttpCodes.OK_200);
    }
}
