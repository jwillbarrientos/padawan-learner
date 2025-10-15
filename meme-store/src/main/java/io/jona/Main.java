package io.jona;

import io.jona.framework.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {


        JonaServer jonaServer = new JonaServer(8080);
        jonaServer.registerFilter(Methods.GET, "^/api/.*$", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NOT_FOUND_404).setContentType(MimeType.TEXT_PLAIN).build()); //  /api/add-label
//        jonaServer.registerEndPoint(Methods.GET, "/public/login", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/public/signup", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/home", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/add-label", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/edit-label", ...);
//        jonaServer.registerEndPoint(Methods.GET, "/api/remove-label", ...);
        jonaServer.registerEndPoint(Methods.GET, "/setcookie", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NO_CONTENT_204).addCookie("hola", "myCookie").addCookie("hola2", "myCookie2").build());
        jonaServer.registerEndPoint(Methods.GET, "/deletecookies", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.NO_CONTENT_204).deleteCookies().build());
        jonaServer.registerEndPoint(Methods.GET, "/api/getdate", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.OK_200).setContentType(MimeType.TEXT_PLAIN).setBody(LocalDateTime.now().toString().getBytes()).build());
        jonaServer.registerEndPoint(Methods.GET, "/getdate",
                r -> {
            return new HttpResponseBuilder()
                    .setResponseCode(HttpCodes.OK_200)
                    .setContentType(MimeType.TEXT_PLAIN)
                    .setBody(LocalDateTime.now()+ " - " + r.getHeaders().get("Cookie"))
                    .build();
        });
        jonaServer.addStaticContent("./web-root");
        jonaServer.start();
    }
}
