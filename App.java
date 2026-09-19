import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class App {

    public static void main(String[] args) throws Exception {

        int porta = 3000;

        HttpServer servidor =
                HttpServer.create(new InetSocketAddress(porta), 0);

        servidor.createContext("/", App::responder);

        servidor.setExecutor(null);

        servidor.start();

        System.out.println(
                "Servidor Java executando na porta " + porta
        );
    }

    private static void responder(HttpExchange exchange)
            throws IOException {

        String hostname = InetAddress
                .getLocalHost()
                .getHostName();

        String html = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Servidor Web Java</title>
                </head>
                <body>
                    <h1>World!</h1>
                    <h2>Aplicacao Web em Java</h2>
                    <p>Servidor: %s</p>
                    <p>Data/Hora: %s</p>
                </body>
                </html>
                """.formatted(
                    hostname,
                    LocalDateTime.now()
                );

        byte[] resposta = html.getBytes();

        exchange.getResponseHeaders().add(
                "Content-Type",
                "text/html; charset=UTF-8"
        );

        exchange.sendResponseHeaders(
                200,
                resposta.length
        );

        try (OutputStream os =
                     exchange.getResponseBody()) {

            os.write(resposta);
        }
    }
}
