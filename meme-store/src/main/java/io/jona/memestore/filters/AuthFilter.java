package io.jona.memestore.filters;

import io.jona.framework.HttpCodes;
import io.jona.framework.HttpRequest;
import io.jona.framework.HttpResponse;

public class AuthFilter {
    public void onlyAuthenticated(HttpRequest request, HttpResponse response) {

        /*
        boolean sessionCookiePresentg = "ok".equals(request.getCookieValue("sessionCookie"));
            getCookieValue(String cookieName)
                for cookie in request.getHeaders("Cookie")\
                    if cookie.name.equals(cookieName)
                        return cookie;
                return null;

         */
        boolean sessionCookiePresent = "ok".equals(request.getCookies().get("sessionCookie"));
        if (sessionCookiePresent) {
            return;
        }
        response.setResponseCode(HttpCodes.NOT_FOUND_404);
        response.setBody("Request blocked by AuthFilter");
        response.setFinal();
    }
}
