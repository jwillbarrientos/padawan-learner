package io.jona.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;

public class ProcessingOfRequests {
    private static final Logger logger = LoggerFactory.getLogger(ProcessingOfRequests.class);

    public static void processRequest(HttpRequest request, HttpResponse response, String location, Map<String, Function<HttpRequest, HttpResponse>> endPoints) throws IOException {
        String welcomePage = "index.html";
        boolean welcomePageRequested = request.getPath().isEmpty() || request.getPath().equals("/") || endPoints.containsKey(request.getPath());
        String relativePath = location + "/" + request.getPath(); // /index.html === ./web-root//index.html
        relativePath = relativePath.replaceAll("/{2,}", "/");
        if(welcomePageRequested) {
            relativePath = location + "/index.html";
        }

        try(InputStream resourceAsStream = new FileInputStream(relativePath)) {
            boolean requestedResourceExistsAsFile = resourceAsStream != null;

            if (welcomePageRequested) {
                response.setResponseCode(HttpCodes.OK_200);
                response.setContentType(MimeType.TEXT_HTML, "UTF-8");
                try {
                    response.setBody(resourceAsStream.readAllBytes());
                    logger.info("Served File: public/index.html (MIME: text/html, Size: {} bytes", response.getBody().length);
                } catch (Exception e) {
                    logger.error("Fallo tratando de leer el archivo para el response del welcome page", e);
                }
                return;
            }

            if (!requestedResourceExistsAsFile) {
                response.setResponseCode(HttpCodes.NOT_FOUND_404);
                response.setContentType(MimeType.TEXT_PLAIN);
                response.setBody("Este recurso no existe");
                logger.info("Served File: NOT FOUND");
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
                        response.setPath(request.getPath());
                        response.setRange(request.getRange());
                        response.setStartOfFile(request.getRange());
                        response.setTotalFileSize(resourceAsStream.readAllBytes().length);

                        Path filePath = Paths.get(relativePath);
                        long end = Math.min(response.getStartOfFile() + HttpResponse.CHUNK_SIZE_BYTES - 1, response.getTotalFileSize() - 1);
                        response.setEnfOfFile(end);
                        response.setBody(filePath, response.getStartOfFile(), response.getEnfOfFile());
                    } else {
                        response.setResponseCode(HttpCodes.OK_200);
                        response.setBody(resourceAsStream.readAllBytes());
                    }
            } catch (IOException e) {
                logger.error("Fallo seteando el body", e);
            }
        }
    }
}
