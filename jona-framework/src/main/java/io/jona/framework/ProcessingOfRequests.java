package io.jona.framework;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
public class ProcessingOfRequests {
    public static void processRequest(HttpRequest request, HttpResponse response, String location) throws IOException {
        boolean welcomePageRequested = request.getPath().isEmpty() || request.getPath().equals("/");
        String relativePath = location + "/" + request.getPath(); // /index.html === ./web-root//index.html
        Path path = Paths.get(relativePath).toAbsolutePath();
        relativePath = relativePath.replaceAll("/{2,}", "/");
        if(welcomePageRequested) {
            relativePath = location + "/index.html";
        }

        boolean requestedResourceExistsAsFile = path.toFile().exists();
        if (!requestedResourceExistsAsFile) {
            response.setResponseCode(HttpCodes.NOT_FOUND_404);
            response.setContentType(MimeType.TEXT_PLAIN);
            response.setBody("Este recurso no existe");
            log.info("Served File: NOT FOUND");
            return;
        }

        try(InputStream resourceAsStream = new FileInputStream(relativePath)) {
            if (welcomePageRequested) {
                response.setResponseCode(HttpCodes.OK_200);
                response.setContentType(MimeType.TEXT_HTML, "UTF-8");
                try {
                    response.setBody(resourceAsStream.readAllBytes());
                    log.info("Served File: public/index.html (MIME: text/html, Size: {} bytes", response.getBody().length);
                } catch (Exception e) {
                    log.error("Fallo tratando de leer el archivo para el response del welcome page", e);
                }
                return;
            }

            String extension = request.getPath().substring(request.getPath().lastIndexOf("."));
            MimeType mimeType = MimeType.getMimeForExtension(extension);
            if (mimeType.requiresEncoding) {
                response.setContentType(mimeType, "UTF-8");
            } else {
                response.setContentType(mimeType);
            }

//      Setea el body
            try {
                    if (mimeType == MimeType.VIDEO_MP4) {
                        response.setResponseCode(HttpCodes.PARTIAL_CONTENT_206);
                        response.setRange(request.getRange());
                        response.setStartOfFile(request.getRange());
                        response.setTotalFileSize(resourceAsStream.readAllBytes().length);

                        long end = Math.min(response.getStartOfFile() + HttpResponse.CHUNK_SIZE_BYTES - 1, response.getTotalFileSize() - 1);
                        response.setEnfOfFile(end);
                        response.setBody(path, response.getStartOfFile(), response.getEnfOfFile());
                    } else {
                        response.setResponseCode(HttpCodes.OK_200);
                        response.setBody(resourceAsStream.readAllBytes());
                    }
            } catch (IOException e) {
                log.error("Fallo seteando el body", e);
            }
        }
    }
}
