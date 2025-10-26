package io.jona.memestore.controller;

import io.jona.framework.*;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TagController {
    private final Map<String, Client> sessionCookies;

    public TagController(HashMap<String, Client> sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public void addTag(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        response.setContentType(MimeType.TEXT_PLAIN);
        Tag tag = new Tag(request.getQueryParams().get("tagName"), client.getId());
        log.info("Tag creation was successful: " + JonaDb.insert(tag));
        response.setResponseCode(HttpCodes.OK_200);
    }

    public void editTag(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        //JonaDb.selectSingle("select id, name, client_id from tag where id = ?", Tag.getFullMapping(), );
    }

    public void deleteTag(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        //JonaDb.delete()
        this.listTags(request, response);
    }

    public void listTags(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        List<Tag> tags = JonaDb.selectList("select id, name, client_id from tag where client_id = ?", Tag.getFullMapping(), client.getId());
        StringBuilder sb = new StringBuilder();
        System.out.println(tags);
        for (Tag tag : tags) {
            sb.append(tag.getName()).append("\n");
        }
        System.out.println(sb);
        response.setContentType(MimeType.TEXT_PLAIN);
        response.setResponseCode(HttpCodes.OK_200);
        response.setBody(sb.toString().getBytes());
    }
}
