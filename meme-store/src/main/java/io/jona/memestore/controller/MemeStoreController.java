package io.jona.memestore.controller;

import io.jona.framework.*;
import io.jona.memestore.dto.Client;

public class MemeStoreController {
    public HttpResponse login(HttpRequest request) {
        HttpResponseBuilder builder = new HttpResponseBuilder();
        System.out.println(request.getQueryParams());
        System.out.println(request.getQueryParams().get("email"));
        System.out.println(request.getQueryParams().get("password"));
        boolean userExist = JonaDb.selectSingle("select  id, email, password from client where email = ? and password = ?",
                Client.getFullMapping(),
                request.getQueryParams().get("email"),
                request.getQueryParams().get("password")) != null;
        builder.setContentType(MimeType.TEXT_PLAIN);
        if(userExist) {
            builder.setResponseCode(HttpCodes.OK_200);
            builder.login();
        } else {
            builder.setResponseCode(HttpCodes.UNAUTHORIZED_401);
            builder.setBody("false");
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
        if(!userExist) {
            builder.setResponseCode(HttpCodes.OK_200);
            builder.signUp();
            Client client = new Client(request.getQueryParams().get("email"), request.getQueryParams().get("password"));
            JonaDb.insert(client);
            builder.setBody("true");
        } else {
            builder.setResponseCode(HttpCodes.CONFLICT_409);
            builder.setBody("false");
        }
        return builder.build();
    }
}
