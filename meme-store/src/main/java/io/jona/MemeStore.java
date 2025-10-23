package io.jona;

import io.jona.framework.*;
import io.jona.memestore.AppProps;
import io.jona.memestore.controller.AuthController;
import io.jona.memestore.controller.TagController;
import io.jona.memestore.controller.TestController;
import io.jona.memestore.dto.Client;
import io.jona.memestore.filters.AuthFilter;
import io.jona.memestore.filters.NoCacheFilter;
import java.io.IOException;
import java.util.HashMap;

public class MemeStore {
    static HashMap<String, Client> sessionCookies = new HashMap<>();
    public static void main(String[] args) throws IOException {
        JonaDb.init(AppProps.getJdbcUrl(), AppProps.getDbUser(), AppProps.getDbPassword());
        JonaServer jonaServer = new JonaServer(8080);
        TestController testController = new TestController();

        jonaServer.registerInboundFilter(Methods.GET, "^/testPath/.*$", (rq, rp) -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
//        jonaServer.registerOutboundFilter(Methods.GET, "^/testPath/.*$", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
        jonaServer.registerEndPoint(Methods.GET, "/testPath/getdate", testController::getDate);

        jonaServer.registerEndPoint(Methods.GET, "/getdate", testController::getDateAndCookies);
        jonaServer.registerEndPoint(Methods.GET, "/setcookie", testController::setCookie);
        jonaServer.registerEndPoint(Methods.GET, "/deletecookies", testController::deleteCookie);

        // app memestore
        AuthController authController = new AuthController(sessionCookies);
        TagController tagController = new TagController();
        AuthFilter authFilter = new AuthFilter(sessionCookies);
        NoCacheFilter noCacheFilter = new NoCacheFilter();
        jonaServer.registerInboundFilter(Methods.GET, "^/api/.*", authFilter::onlyAuthenticated);
        jonaServer.registerInboundFilter(Methods.GET, "^/app/.*", authFilter::onlyAuthenticated);
        jonaServer.registerEndPoint(Methods.GET, "/public/login", authController::login);
        jonaServer.registerEndPoint(Methods.GET, "/public/signup", authController::signUp);
        jonaServer.registerEndPoint(Methods.GET, "/public/signout", authController::signOut);
        jonaServer.registerEndPoint(Methods.GET, "/public/getprofilename", authController::getProfileName);
        jonaServer.addStaticContent("./web-root");
        jonaServer.registerOutboundFilter(Methods.GET, "^/public/.*$", noCacheFilter::addNoCache);
        jonaServer.registerOutboundFilter(Methods.GET, "^/api/.*$", noCacheFilter::addNoCache);
        //jonaServer.registerEndPoint(Methods.GET, "/api/list-tags", tagController::listTags);

        jonaServer.start();
    }
}
