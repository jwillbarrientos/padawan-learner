package io.jona.memestore.controller;

import io.jona.framework.*;
import io.jona.memestore.dto.Client;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthController {
    private final Map<String, Client> sessionCookies;

    public AuthController(HashMap<String, Client> sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public void login(HttpRequest request, HttpResponse response) {
        Client client = JonaDb.selectSingle("select  id, email, password from client where email = ? and password = ?",
                Client.getFullMapping(),
                request.getQueryParams().get("email"),
                request.getQueryParams().get("password"));
        boolean clientExist = client != null;
        if (clientExist) {
            response.setResponseCode(HttpCodes.OK_200);
            String sessionId = UUID.randomUUID().toString();
            response.addCookie("sessionCookie", sessionId);
            sessionCookies.put(sessionId, client);
        } else {
            response.setResponseCode(HttpCodes.UNAUTHORIZED_401);
        }
    }

    public void signUp(HttpRequest request, HttpResponse response) {
        Client client = JonaDb.selectSingle("select  id, email, password from client where email = ? and password = ?",
                Client.getFullMapping(),
                request.getQueryParams().get("email"),
                request.getQueryParams().get("password"));
        boolean clientExist = client != null;
        if (!clientExist) {
            client = new Client(request.getQueryParams().get("email"), request.getQueryParams().get("password"));
            boolean insertSuccessful = JonaDb.insert(client);
            if (insertSuccessful) {
                response.setResponseCode(HttpCodes.OK_200);
                String sessionId = UUID.randomUUID().toString();
                response.addCookie("sessionCookie", sessionId);
                sessionCookies.put(sessionId, client);
            }
        }
        response.setResponseCode(HttpCodes.CONFLICT_409);
    }

    public void signOut(HttpRequest request, HttpResponse response) {
        response.deleteCookies();
        response.setResponseCode(HttpCodes.OK_200);
    }

    public void getProfileName(HttpRequest request, HttpResponse response) {
        Client client = sessionCookies.get(request.getCookies().get("sessionCookie"));
        response.setBody(client.getEmail());
        response.setContentType(MimeType.TEXT_PLAIN);
        response.setResponseCode(HttpCodes.OK_200);
    }
}
