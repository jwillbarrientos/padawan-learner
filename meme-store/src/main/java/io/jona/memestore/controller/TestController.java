package io.jona.memestore.controller;

import io.jona.framework.*;
import java.time.LocalDateTime;

public class TestController {
    public HttpResponse getDate(HttpRequest request) {
        return new HttpResponseBuilder()
                .setResponseCode(HttpCodes.OK_200)
                .setContentType(MimeType.TEXT_PLAIN)
                .setBody(LocalDateTime.now().toString().getBytes())
                .build();
    }

    public HttpResponse getDateAndCookies(HttpRequest request) {
        return new HttpResponseBuilder()
                .setResponseCode(HttpCodes.OK_200)
                .setContentType(MimeType.TEXT_PLAIN)
                .setBody(LocalDateTime.now()+ " - " + request.getHeaders().get("Cookie"))
                .build();
    }

    public HttpResponse setCookie(HttpRequest request) {
        return new HttpResponseBuilder()
                .setResponseCode(HttpCodes.NO_CONTENT_204)
                .addCookie("hola", "myCookie")
                .addCookie("hola2", "myCookie2")
                .build();
    }

    public HttpResponse deleteCookie(HttpRequest request) {
        return new HttpResponseBuilder()
                .setResponseCode(HttpCodes.NO_CONTENT_204)
                .deleteCookies()
                .build();
    }
}
