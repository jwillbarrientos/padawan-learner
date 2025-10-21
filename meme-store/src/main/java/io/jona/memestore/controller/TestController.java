package io.jona.memestore.controller;

import io.jona.framework.*;
import java.time.LocalDateTime;

public class TestController {
    public void getDate(HttpRequest request, HttpResponse response) {
        response.setResponseCode(HttpCodes.OK_200);
        response.setContentType(MimeType.TEXT_PLAIN);
        response.setBody(LocalDateTime.now().toString().getBytes());
    }

    public void getDateAndCookies(HttpRequest request, HttpResponse response) {
        response.setResponseCode(HttpCodes.OK_200);
        response.setContentType(MimeType.TEXT_PLAIN);
        response.setBody(LocalDateTime.now()+ " - " + request.getHeaders().get("Cookie"));
    }

    public void setCookie(HttpRequest request, HttpResponse response) {
        response.setResponseCode(HttpCodes.NO_CONTENT_204);
        response.addCookie("hola", "myCookie");
        response.addCookie("hola2", "myCookie2");
    }

    public void deleteCookie(HttpRequest request, HttpResponse response) {
        response.setResponseCode(HttpCodes.NO_CONTENT_204);
        response.deleteCookies();
    }
}
