package io.jona.memestore.filters;

import io.jona.framework.HttpRequest;
import io.jona.framework.HttpResponse;

public class NoCacheFilter {
    public void addNoCache(HttpRequest request, HttpResponse response) {
        response.noCache();
    }
}
