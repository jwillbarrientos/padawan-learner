package io.jona;

import io.jona.framework.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JonaServer jonaServer = new JonaServer(8080);
        jonaServer.addStaticContent("./web-root");
        jonaServer.registerEndPoint(Methods.GET, "/getdate", r -> new HttpResponseBuilder().setResponseCode(HttpCodes.OK_200).setPath("/getdate").setContentType(MimeType.TEXT_PLAIN).build());
        jonaServer.start();
    }
}
