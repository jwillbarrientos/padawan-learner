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
        jonaServer.registerFilter(Methods.GET, "^/api/.*$", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
//        jonaServer.registerEndPoint(Methods.GET, "/public/signup", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/home", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/add-label", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/edit-label", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/remove-label", ...);
        jonaServer.registerEndPoint(Methods.GET, "/setcookie", testController::setCookie);
        jonaServer.registerEndPoint(Methods.GET, "/deletecookies", testController::deleteCookie);
        jonaServer.registerEndPoint(Methods.GET, "/api/getdate", testController::getDate);
        jonaServer.registerEndPoint(Methods.GET, "/getdate", testController::getDateAndCookies);
        jonaServer.registerEndPoint(Methods.GET, "/public/login", memeStoreController::login);
        jonaServer.registerEndPoint(Methods.GET, "/public/signup", memeStoreController::signUp);
        jonaServer.addStaticContent("./web-root");
        jonaServer.start();
    }
}
