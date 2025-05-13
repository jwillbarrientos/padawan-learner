package io.jona;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while(true) {
            //1. aceptar conexion de cliente
            Socket client = serverSocket.accept();

            //2. leer el request http completo del cliente
            InputStream inputStream = client.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String line = bufferedReader.readLine();
                System.out.println(line);
                if (line.isEmpty())
                    break;
            }

            //3. preparar respuesta del cliente
            String header = """
                    HTTP/1.1 200 OK
                    Date: Tue, 13 May 2025 16:48:00 GMT
                    Server: jonatitoServer
                    Content-Type: text/html; charset=UTF-8
                    Content-Length: ${bodyLen}
                    Vary: Accept-Encoding
                    
                    """;

            String body = """
                    <!DOCTYPE html><head>
                        <title>Welcome</title>
                    </head>
                    <body>
                        <h1>Hello, World!</h1>
                        <p>Welcome to the localhost server.</p>
                    </body>
                    </html>
                    """;
            byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

            header = header.replace("${bodyLen}", String.valueOf(bodyBytes.length));
            byte[] headerBytes = header.getBytes(StandardCharsets.US_ASCII);
            System.out.println("Respuesta al navegador: ");
            System.out.println(header);
            System.out.println(body);

            //4. enviar respuesta
            client.getOutputStream().write(headerBytes);
            client.getOutputStream().write(bodyBytes);
            client.getOutputStream().flush();

            //5. cerrar conexion
            client.close();
        }
    }
}