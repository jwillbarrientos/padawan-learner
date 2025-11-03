package io.jona;

import io.jona.framework.*;
import io.jona.framework.http.Method;
import io.jona.memestore.AppProps;
import io.jona.memestore.VideoHelper;
import io.jona.memestore.controller.*;
import io.jona.memestore.dto.Client;
import io.jona.memestore.filters.AuthFilter;
import io.jona.memestore.filters.NoCacheFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MemeStore {
    static HashMap<String, Client> sessionCookies = new HashMap<>();
    public static void main(String[] args) throws IOException {

        JonaDb.init(AppProps.getJdbcUrl(), AppProps.getDbUser(), AppProps.getDbPassword());

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(VideoHelper.backgroundDownloader(), 0,5, TimeUnit.SECONDS);

        // app memestore
        AuthFilter authFilter = new AuthFilter(sessionCookies);
        NoCacheFilter noCacheFilter = new NoCacheFilter();

        AuthController authController = new AuthController(sessionCookies);
        TagController tagController = new TagController(sessionCookies);
        VideoController videoController = new VideoController(sessionCookies);
        StreamingController streamingController = new StreamingController(sessionCookies);

        JonaServer jonaServer = new JonaServer(8080);

        jonaServer.registerInboundFilter(Method.GET, "^/api/.*", authFilter::onlyAuthenticated);
        jonaServer.registerInboundFilter(Method.GET, "^/app/.*", authFilter::onlyAuthenticated);

        jonaServer.registerEndPoint(Method.GET, "/public/login", authController::login);
        jonaServer.registerEndPoint(Method.GET, "/public/signup", authController::signUp);

        jonaServer.registerEndPoint(Method.GET, "/api/signout", authController::signOut);
        jonaServer.registerEndPoint(Method.GET, "/api/getprofilename", authController::getProfileName);
        jonaServer.registerEndPoint(Method.GET, "/api/loadtags", tagController::listTags);
        jonaServer.registerEndPoint(Method.GET, "/api/addtag", tagController::addTag);
        jonaServer.registerEndPoint(Method.GET, "/api/edittag", tagController::editTag);
        jonaServer.registerEndPoint(Method.GET, "/api/deletetag", tagController::deleteTag);
        jonaServer.registerEndPoint(Method.GET, "/api/addvideobylink", videoController::addVideoByLink);
        jonaServer.registerEndPoint(Method.GET, "/api/loadvideos", streamingController::loadVideos);
        jonaServer.registerEndPoint(Method.GET, "/api/streamingvideos", streamingController::streamVideos);
        jonaServer.registerEndPoint(Method.POST, "/api/processwhatsappchat", videoController::addVideoByFile);

        jonaServer.addStaticContent("./web-root");

        jonaServer.registerOutboundFilter(Method.GET, "^/public/.*$", noCacheFilter::addNoCache);
        jonaServer.registerOutboundFilter(Method.GET, "^/api/.*$", noCacheFilter::addNoCache);

        jonaServer.start();

        // todo setup graceful shutdown
    }
}
