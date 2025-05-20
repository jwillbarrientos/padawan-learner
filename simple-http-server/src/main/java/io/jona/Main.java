package io.jona;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static Path CONTENT_ROOT = Paths.get("C:\\Users\\barri\\IdeaProjects\\padawan-learner\\simple-http-server\\web-root");

    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while(true) {
            //1. aceptar conexion de cliente
            Socket client = serverSocket.accept();
            HttpRequest request = new HttpRequest();
            request.readFromSocket(client);
            System.out.println("Objeto request construido: \n" + request);

            HttpResponse response = new HttpResponse();

            if (request.getPath().equals("/")) {
                response.setResponseCode(HttpCodes.OK_200);
                response.setContentType(MimeType.TEXT_HTML, "UTF-8");
                response.setBody( Files.readString(CONTENT_ROOT.resolve(Paths.get("index.html")), StandardCharsets.UTF_8) );
            } else if (CONTENT_ROOT.resolve(request.getPath()).toFile().exists()) {
                response.setResponseCode(HttpCodes.OK_200);
                if (request.getPath().endsWith("html")) {
                    response.setContentType(MimeType.TEXT_HTML, "UTF-8");
                } else if (request.getPath().endsWith("ico")) {
                    response.setContentType(MimeType.FAVICON);
                }
                response.setBody(Files.readAllBytes(CONTENT_ROOT.resolve(Paths.get(request.getPath()))));
            } else {
                response.setResponseCode(HttpCodes.NOT_FOUND_404);
                response.setContentType(MimeType.TEXT_PLAIN);
                response.setBody("Este recurso no existe");
            }

            byte[] responseBytes = response.buildResponse();
            System.out.println("Respuesta al navegador:\n "+ response);
            //4. enviar respuesta
            client.getOutputStream().write(responseBytes);
            client.getOutputStream().flush();
            //5. cerrar conexion
            client.close();
        }
    }

}