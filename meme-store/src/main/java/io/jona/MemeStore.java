package io.jona;

import io.jona.framework.*;
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
        JonaServer jonaServer = new JonaServer(8080);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(VideoHelper.setVideoWhenDownloaded(), 0,5, TimeUnit.SECONDS);
        TestController testController = new TestController();

        jonaServer.registerInboundFilter(Methods.GET, "^/testPath/.*$", (rq, rp) -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
//        jonaServer.registerOutboundFilter(Methods.GET, "^/testPath/.*$", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
        jonaServer.registerEndPoint(Methods.GET, "/testPath/getdate", testController::getDate);

        jonaServer.registerEndPoint(Methods.GET, "/getdate", testController::getDateAndCookies);
        jonaServer.registerEndPoint(Methods.GET, "/setcookie", testController::setCookie);
        jonaServer.registerEndPoint(Methods.GET, "/deletecookies", testController::deleteCookie);

        // app memestore
        AuthController authController = new AuthController(sessionCookies);
        TagController tagController = new TagController(sessionCookies);
        VideoController videoController = new VideoController(sessionCookies);
        StreamingController streamingController = new StreamingController(sessionCookies);
        AuthFilter authFilter = new AuthFilter(sessionCookies);
        NoCacheFilter noCacheFilter = new NoCacheFilter();
        jonaServer.registerInboundFilter(Methods.GET, "^/api/.*", authFilter::onlyAuthenticated);
        jonaServer.registerInboundFilter(Methods.GET, "^/app/.*", authFilter::onlyAuthenticated);
        jonaServer.registerEndPoint(Methods.GET, "/public/login", authController::login);
        jonaServer.registerEndPoint(Methods.GET, "/public/signup", authController::signUp);
        jonaServer.registerEndPoint(Methods.GET, "/api/signout", authController::signOut);
        jonaServer.registerEndPoint(Methods.GET, "/api/getprofilename", authController::getProfileName);
        jonaServer.registerEndPoint(Methods.GET, "/api/loadtags", tagController::listTags);
        jonaServer.registerEndPoint(Methods.GET, "/api/addtag", tagController::addTag);
        jonaServer.registerEndPoint(Methods.GET, "/api/edittag", tagController::editTag);
        jonaServer.registerEndPoint(Methods.GET, "/api/deletetag", tagController::deleteTag);
        jonaServer.registerEndPoint(Methods.GET, "/api/addvideobylink", videoController::addVideoByLink);
        jonaServer.registerEndPoint(Methods.GET, "/api/loadvideos", streamingController::loadVideos);
        jonaServer.registerEndPoint(Methods.GET, "/api/streamingvideos", streamingController::streamVideos);
        jonaServer.addStaticContent("./web-root");
        jonaServer.registerOutboundFilter(Methods.GET, "^/public/.*$", noCacheFilter::addNoCache);
        jonaServer.registerOutboundFilter(Methods.GET, "^/api/.*$", noCacheFilter::addNoCache);

        jonaServer.start();
    }
}
