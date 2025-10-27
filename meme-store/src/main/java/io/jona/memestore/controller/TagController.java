package io.jona.memestore.controller;

import com.google.gson.Gson;
import io.jona.framework.*;
import io.jona.memestore.dto.Client;
import io.jona.memestore.dto.Tag;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
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
        Tag tag = JonaDb.selectSingle("select id, name, client_id from tag where id = ?", Tag.getFullMapping(), request.getQueryParams().get("id"));
        System.out.println(JonaDb.delete(tag));
        this.listTags(request, response);
    }

    public void listTags(HttpRequest request, HttpResponse response) {
        String sessionCookie = request.getCookies().get("sessionCookie");
        Client client = sessionCookies.get(sessionCookie);
        List<Tag> tags = JonaDb.selectList("select id, name, client_id from tag where client_id = ?", Tag.getFullMapping(), client.getId());
        System.out.println(tags);
        Gson gson = new Gson();
        String json = gson.toJson(tags);
        System.out.println("HEREEEEE" + json);
        response.setContentType(MimeType.APPLICATION_JSON, "UTF-8");
        response.setResponseCode(HttpCodes.OK_200);
        response.setBody(json.getBytes());
    }
}
