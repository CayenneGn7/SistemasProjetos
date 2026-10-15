
package client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private final HttpClient client;

    private final String baseUrl = "http://localhost:7071";

    public ApiClient() {
        client = HttpClient.newHttpClient();
    }

    public String listarProjetos() throws Exception {

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(
                    URI.create(
                        baseUrl + "/api/projetos"
                    )
                )
                .GET()
                .build();

        HttpResponse<String> response =
            client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

        return response.body();
    }
}
