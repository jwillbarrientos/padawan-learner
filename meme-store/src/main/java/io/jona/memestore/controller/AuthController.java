package io.jona.memestore.controller;

import io.jona.framework.*;
import io.jona.memestore.dto.Client;

public class AuthController {
    public void login(HttpRequest request, HttpResponse response) {
        boolean userExist = JonaDb.selectSingle("select  id, email, password from client where email = ? and password = ?",
                Client.getFullMapping(),
                request.getQueryParams().get("email"),
                request.getQueryParams().get("password")) != null;
        if (userExist) {
            response.setResponseCode(HttpCodes.OK_200);
            response.addCookie("sessionCookie", "ok");
        } else {
            response.setResponseCode(HttpCodes.UNAUTHORIZED_401);
        }
    }

    public void signUp(HttpRequest request, HttpResponse response) {
        boolean userExist = JonaDb.selectSingle("select  id, email, password from client where email = ? and password = ?",
                Client.getFullMapping(),
                request.getQueryParams().get("email"),
                request.getQueryParams().get("password")) != null;
        if (!userExist) {
            Client client = new Client(request.getQueryParams().get("email"), request.getQueryParams().get("password"));
            boolean insertSuccessful = JonaDb.insert(client);
            if (insertSuccessful) {
                response.setResponseCode(HttpCodes.OK_200);
                response.addCookie("sessionCookie", "ok");
            }
        }
        response.setResponseCode(HttpCodes.CONFLICT_409);
    }
}
