package io.jona;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.jona.Main.CONTENT_ROOT;

public class ProcessingOfRequests {
    public static void processRequest(HttpRequest request, HttpResponse response) {
        boolean requestedResourceExistsAsFile = CONTENT_ROOT.resolve(request.getPath()).toFile().exists();
        if (request.getPath().isEmpty()) {
            response.setResponseCode(HttpCodes.OK_200);
            response.setContentType(MimeType.TEXT_HTML, "UTF-8");
            try {
                response.setBody(Files.readString(CONTENT_ROOT.resolve(Paths.get("index.html")), StandardCharsets.UTF_8));
            } catch (IOException e) {

            }
        } else if (requestedResourceExistsAsFile) {
            if (CONTENT_ROOT.resolve(request.getPath()).toFile().exists()) {
                response.setResponseCode(HttpCodes.OK_200);
            }
            String extension = request.getPath().substring(request.getPath().lastIndexOf("."));
            MimeType mimeType = MimeType.getMimeForExtension(extension);
            if (mimeType.requiresEncoding) {
                response.setContentType(mimeType, "UTF-8");
            } else {
                response.setContentType(mimeType);
            }

            try {
                response.setBody(Files.readAllBytes(CONTENT_ROOT.resolve(Paths.get(request.getPath()))));
            } catch (IOException e) {

            }
        } else {
            response.setResponseCode(HttpCodes.NOT_FOUND_404);
            response.setContentType(MimeType.TEXT_PLAIN);
            response.setBody("Este recurso no existe");
        }
        try {
            response.setBody(Files.readAllBytes(CONTENT_ROOT.resolve(Paths.get(request.getPath()))));
        } catch (IOException e) {

        }
    }
}
