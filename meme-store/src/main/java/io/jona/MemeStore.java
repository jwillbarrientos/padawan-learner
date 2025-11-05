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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemeStore {
    static HashMap<String, Client> sessionCookies = new HashMap<>();
    public static void main(String[] args) throws IOException {

        JonaDb.init(AppProps.getJdbcUrl(), AppProps.getDbUser(), AppProps.getDbPassword(), true);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(VideoHelper.backgroundDownloader());

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
        jonaServer.registerEndPoint(Method.GET, "/api/loadvideos", streamingController::downloadVideosInTheServerAndLoad);
        jonaServer.registerEndPoint(Method.GET, "/api/streamingvideos", streamingController::streamVideos);
        jonaServer.registerEndPoint(Method.GET, "/api/addvideobylink", videoController::addVideoByLink);
        jonaServer.registerEndPoint(Method.POST, "/api/processwhatsappchat", videoController::addVideoByFile);
        jonaServer.registerEndPoint(Method.GET, "/api/getvideosforreel", streamingController::getVideosForReel);

        jonaServer.addStaticContent("./web-root");

        jonaServer.registerOutboundFilter(Method.GET, "^/public/.*$", noCacheFilter::addNoCache);
        jonaServer.registerOutboundFilter(Method.GET, "^/api/.*$", noCacheFilter::addNoCache);

        jonaServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            service.shutdownNow();
            jonaServer.shutdown();
            System.exit(0);
        }));
    }
}
