package io.jona.memestore.controller;

import io.jona.framework.*;
import io.jona.memestore.dto.Client;

public class MemeStoreController {
    public HttpResponse login(HttpRequest request) {
        HttpResponseBuilder builder = new HttpResponseBuilder();
        boolean userExist = JonaDb.selectSingle("select  id, email, password from client where email = ? and password = ?",
                Client.getFullMapping(),
                request.getQueryParams().get("email"),
                request.getQueryParams().get("password")) != null;
        builder.setContentType(MimeType.TEXT_PLAIN);
        if (userExist) {
            builder.setResponseCode(HttpCodes.OK_200);
        } else {
            builder.setResponseCode(HttpCodes.UNAUTHORIZED_401);
        }
        return builder.build();
    }

    public HttpResponse signUp(HttpRequest request) {
        HttpResponseBuilder builder = new HttpResponseBuilder();
        boolean userExist = JonaDb.selectSingle("select  id, email, password from client where email = ? and password = ?",
                Client.getFullMapping(),
                request.getQueryParams().get("email"),
                request.getQueryParams().get("password")) != null;
        builder.setContentType(MimeType.TEXT_PLAIN);
        if (!userExist) {
            Client client = new Client(request.getQueryParams().get("email"), request.getQueryParams().get("password"));
            boolean insertSuccessful = JonaDb.insert(client);
            if (insertSuccessful) {
                builder.setResponseCode(HttpCodes.OK_200);
                return builder.build();
            }
        }
        builder.setResponseCode(HttpCodes.CONFLICT_409);
        return builder.build();
    }
}
