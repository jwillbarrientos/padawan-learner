package io.jona.memestore.filters;

import io.jona.framework.http.HttpRequest;
import io.jona.framework.http.HttpResponse;

public class NoCacheFilter {
    public void addNoCache(HttpRequest request, HttpResponse response) {
        response.noCache();
    }
}
