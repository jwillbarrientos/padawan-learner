package io.jona.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ProcessingOfRequests {
    private static final Logger logger = LoggerFactory.getLogger(ProcessingOfRequests.class);

    public static void processRequest(HttpRequest request, HttpResponse response, String location) throws IOException {
        String relativePath = location + "/" + request.getPath();
        boolean requestedResourceExistsAsFile = ProcessingOfRequests.class.getResourceAsStream(relativePath) != null;
        InputStream resourceAsStream = ProcessingOfRequests.class.getResourceAsStream(relativePath);
        String content = new String(resourceAsStream.readAllBytes());
        boolean welcomePageRequested = request.getPath().isEmpty() || request.getPath().equals("/");
        if (welcomePageRequested) {
            response.setResponseCode(HttpCodes.OK_200);
            response.setContentType(MimeType.TEXT_HTML, "UTF-8");
            try {
                response.setBody(content.getBytes());
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
//        Construye el response
        if (mimeType.equals(MimeType.VIDEO_MP4)) {
            response.setResponseCode(HttpCodes.PARTIAL_CONTENT_206);
            response.setPath(request.getPath());
            response.setRange(request.getRange());
            response.setStartOfFile(request.getRange());
            response.setTotalFileSize(Files.size(Paths.get(relativePath)));
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
                Path filePath = Paths.get(relativePath);
                long end = Math.min(response.getStartOfFile() + HttpResponse.CHUNK_SIZE_BYTES - 1, response.getTotalFileSize() - 1);
                response.setEnfOfFile(end);
                response.setBody(filePath, response.getStartOfFile(), response.getEnfOfFile());
            } else {
                response.setBody(content.getBytes());
            }
        } catch (IOException e) {
            logger.error("Fallo seteando el body", e);
        }
    }
}
