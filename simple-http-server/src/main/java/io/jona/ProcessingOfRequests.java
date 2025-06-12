package io.jona;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.jona.Main.CONTENT_ROOT;

public class ProcessingOfRequests {
    public static void processRequest(HttpRequest request, HttpResponse response) {
        boolean requestedResourceExistsAsFile = CONTENT_ROOT.resolve(request.getPath()).toFile().exists();
        boolean welcomePageRequested = request.getPath().isEmpty() || request.getPath().equals("/");
        if (welcomePageRequested) {
            response.setResponseCode(HttpCodes.OK_200);
            response.setContentType(MimeType.TEXT_HTML, "UTF-8");
            try {
                response.setBody(Files.readString(CONTENT_ROOT.resolve(Paths.get("index.html")), StandardCharsets.UTF_8));
            } catch (IOException e) {

            }
        }
        //if (response.getContentType().equals(MimeType.VIDEO_MP4.value)) {
        //    response.setResponseCode(HttpCodes.PARTIAL_CONTENT_206);
        //    response.setContentType(MimeType.VIDEO_MP4);
        //    response.setRange(request.getHeaders().get("Range: "));
        if (requestedResourceExistsAsFile) {
            String extension = request.getPath().substring(request.getPath().lastIndexOf("."));
            MimeType mimeType = MimeType.getMimeForExtension(extension);
            if (mimeType.equals(MimeType.VIDEO_MP4)) {
                response.setResponseCode(HttpCodes.PARTIAL_CONTENT_206);
                response.setRange(request.getHeaders().get("Range"));
                response.setPath(request.getPath());
                response.setRange(request.getRange());
            } else {
                response.setResponseCode(HttpCodes.OK_200);
            }
            if (mimeType.requiresEncoding) {
                response.setContentType(mimeType, "UTF-8");
            } else {
                response.setContentType(mimeType);
            }
        } else {
            response.setResponseCode(HttpCodes.NOT_FOUND_404);
            response.setContentType(MimeType.TEXT_PLAIN);
            response.setBody("Este recurso no existe");
        }
        //Path filePath = CONTENT_ROOT.resolve(Paths.get(request.getPath()));
        //response.setBody(filePath);
        try {
            response.setBody(Files.readAllBytes(CONTENT_ROOT.resolve(Paths.get(request.getPath()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
