package io.jona.memestore.controller;

import io.jona.framework.http.HttpCode;
import io.jona.framework.http.HttpRequest;
import io.jona.framework.http.HttpResponse;
import io.jona.framework.JonaDb;
import io.jona.memestore.Platform;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Video;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (Video.notYetInserted(video)) {
            JonaDb.insert(video);
        }
        response.setResponseCode(HttpCode.OK_200);
    }

    public void addVideoByFile(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        String body = parseBody(request.getBody());
        String[] links = body.split("\n");
        for (String link : links) {
            Video video = new Video(link, Video.State.SUBMITTED, client.getId());
            if (Video.notYetInserted(video)) {
                JonaDb.insert(video);
            }
        }
        response.setResponseCode(HttpCode.OK_200);
    }

    public String parseBody(String body) {
        Pattern urlPattern = Pattern.compile("(https?://[\\w./?=&%-]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = urlPattern.matcher(body);
        List<String> links = new ArrayList<>();
        while (matcher.find()) {
            String url = matcher.group(1);
            Platform platform = Platform.whatPlatformIs(url);
            if (platform.equals(Platform.YOU_TUBE)) {
                int qIndex = url.indexOf('?');
                if (qIndex != -1) {
                    String base = url.substring(0, qIndex);
                    String query = url.substring(qIndex + 1);
                    int ampIndex = query.indexOf('&');
                    if (ampIndex != -1) {
                        query = query.substring(0, ampIndex);
                    }
                    links.add(base + "?" + query);
                }
            } else if (!platform.equals(Platform.INVALID_PLATFORM)){
                int qIndex = url.indexOf('?');
                if (qIndex != -1) {
                    links.add(url.substring(0, qIndex));
                }
            }
        }
        return String.join("\n", links);
    }
}
