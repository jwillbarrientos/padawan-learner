package io.jona;

import io.jona.framework.JonaServer;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JonaServer jonaServer = new JonaServer(8080);
        jonaServer.addStaticContent("/static");
        jonaServer.start();
    }
}
