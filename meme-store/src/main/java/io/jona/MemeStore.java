package io.jona;

import io.jona.framework.*;
import io.jona.memestore.AppProps;
import io.jona.memestore.controller.MemeStoreController;
import io.jona.memestore.controller.TestController;
import java.io.IOException;

public class MemeStore {
    public static void main(String[] args) throws IOException {
        JonaDb.init(AppProps.getJdbcUrl(), AppProps.getDbUser(), AppProps.getDbPassword());
        JonaServer jonaServer = new JonaServer(8080);
        TestController testController = new TestController();
        MemeStoreController memeStoreController = new MemeStoreController();

        jonaServer.registerInboundFilter(Methods.GET, "^/testPath/.*$", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
//        jonaServer.registerOutboundFilter(Methods.GET, "^/testPath/.*$", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
        jonaServer.registerEndPoint(Methods.GET, "/testPath/getdate", testController::getDate);

        jonaServer.registerEndPoint(Methods.GET, "/getdate", testController::getDateAndCookies);
        jonaServer.registerEndPoint(Methods.GET, "/setcookie", testController::setCookie);
        jonaServer.registerEndPoint(Methods.GET, "/deletecookies", testController::deleteCookie);

        // app memestore
//        NoCacheFilter noCacheFilter = new NoCacheFilter();
//        AuthFilter authFilter = new AuthFilter();
//        jonaServer.registerFilter(Methods.GET, "^/public/.*$", noCacheFilter::addNoCahe);
//        jonaServer.registerFilter(Methods.GET, "^/api/.*$", noCacheFilter::addNoCahe);
        jonaServer.registerEndPoint(Methods.GET, "/public/login", memeStoreController::login);
        jonaServer.registerEndPoint(Methods.GET, "/public/signup", memeStoreController::signUp);
//        jonaServer.registerFilter(Methods.GET, "^/api/.*", authFilter::onlyAuthenticated);
//        jonaServer.registerEndPoint(Methods.GET, "/api/list-tags", memeStoreController::listTags);

        jonaServer.addStaticContent("./web-root");
        jonaServer.start();
    }
}
