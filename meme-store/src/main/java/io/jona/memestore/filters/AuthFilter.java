package io.jona.memestore.filters;

import io.jona.framework.http.HttpCode;
import io.jona.framework.http.HttpRequest;
import io.jona.framework.http.HttpResponse;
import io.jona.memestore.dto.Client;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter {
    private final Map<String, Client> sessionCookies;

    public AuthFilter(HashMap<String, Client> sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public void onlyAuthenticated(HttpRequest request, HttpResponse response) {
        boolean sessionCookiePresent = sessionCookies.containsKey(request.getCookies().get("sessionCookie"));
        if (sessionCookiePresent) {
            return;
        }
        response.setResponseCode(HttpCode.NOT_FOUND_404);
        response.setBody("Request blocked by AuthFilter");
        response.truncateProcessing();
    }
}
