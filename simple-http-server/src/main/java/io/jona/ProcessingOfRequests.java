package io.jona;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.jona.Main.CONTENT_ROOT;

public class ProcessingOfRequests {
    public static void processRequest(HttpRequest request, HttpResponse response) throws IOException {
        boolean requestedResourceExistsAsFile = CONTENT_ROOT.resolve(request.getPath()).toFile().exists();
        boolean welcomePageRequested = request.getPath().isEmpty() || request.getPath().equals("/");
        if (welcomePageRequested) {
            response.setResponseCode(HttpCodes.OK_200);
            response.setContentType(MimeType.TEXT_HTML, "UTF-8");
            try {
                response.setBody(Files.readString(CONTENT_ROOT.resolve(Paths.get("index.html")), StandardCharsets.UTF_8));
            } catch (IOException e) {

            }
            return;
        }

        if (!requestedResourceExistsAsFile) {
            response.setResponseCode(HttpCodes.NOT_FOUND_404);
            response.setContentType(MimeType.TEXT_PLAIN);
            response.setBody("Este recurso no existe");
            return;
        }

        String extension = request.getPath().substring(request.getPath().lastIndexOf("."));
        MimeType mimeType = MimeType.getMimeForExtension(extension);
//        Construye el response
        if (mimeType.equals(MimeType.VIDEO_MP4)) {
            response.setResponseCode(HttpCodes.PARTIAL_CONTENT_206);
            response.setPath(request.getPath());
            response.setRange(request.getRange());
            response.setStartOfFile(request.getRange());
            response.setTotalFileSize(Files.size(CONTENT_ROOT.resolve(Paths.get(request.getPath()))));
        } else {
            response.setResponseCode(HttpCodes.OK_200);
        }
        if (mimeType.requiresEncoding) {
            response.setContentType(mimeType, "UTF-8");
        } else {
            response.setContentType(mimeType);
        }


//        Setea el body
        try {
            if (response.getContentType().equals("video/mp4")) {
                Path filePath = CONTENT_ROOT.resolve(Paths.get(request.getPath()));
                long end = Math.min(response.getStartOfFile() + HttpResponse.CHUNK_SIZE_BYTES - 1, response.getTotalFileSize() - 1);
                response.setEnfOfFile(end);
                response.setBody(filePath, response.getStartOfFile(), response.getEnfOfFile());
            } else {
                response.setBody(Files.readAllBytes(CONTENT_ROOT.resolve(Paths.get(request.getPath()))));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
