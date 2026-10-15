
package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Projeto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {

    private final HttpClient client;
    private final ObjectMapper mapper;

    private final String baseUrl = "http://localhost:7071";

    public ApiClient() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public List<Projeto> listarProjetos() throws Exception {

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/projetos"))
                .GET()
                .build();

        HttpResponse<String> response =
            client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

        if (response.statusCode() != 200) {
            throw new Exception(
                "Erro ao listar projetos: " + response.statusCode()
            );
        }

        return mapper.readValue(
            response.body(),
            new TypeReference<List<Projeto>>() {}
        );
    }

    public Projeto cadastrarProjeto(Projeto projeto) throws Exception {

        String json = mapper.writeValueAsString(projeto);

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/projetos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
            client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

        if (response.statusCode() != 201) {
            throw new Exception(
                "Erro ao cadastrar projeto: " + response.statusCode()
            );
        }

        return mapper.readValue(response.body(), Projeto.class);
    }

    public Projeto atualizarProjeto(Projeto projeto) throws Exception {

        String json = mapper.writeValueAsString(projeto);

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/projetos/" + projeto.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
            client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

        if (response.statusCode() != 200) {
            throw new Exception(
                "Erro ao atualizar projeto: " + response.statusCode()
            );
        }

        return mapper.readValue(response.body(), Projeto.class);
    }

    public void excluirProjeto(int id) throws Exception {

        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/projetos/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response =
            client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

        if (response.statusCode() != 204) {
            throw new Exception(
                "Erro ao excluir projeto: " + response.statusCode()
            );
        }
    }
}
