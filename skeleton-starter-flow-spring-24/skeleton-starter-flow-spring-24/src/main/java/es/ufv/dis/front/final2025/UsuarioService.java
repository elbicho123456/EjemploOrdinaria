package es.ufv.dis.front.final2025;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UsuarioService {
    private static final String BASE_URL = "http://localhost:8080/api/usuarios";
    private final HttpClient client;
    private final Gson gson;

    public UsuarioService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Usuario> obtenerUsuarios() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            return gson.fromJson(response.body(), listType);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return List.of(); // lista vacía en caso de error
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        try {
            String json = gson.toJson(usuario);
            String url = BASE_URL + "/" + usuario.getId();

            System.out.println("PUT → " + url);
            System.out.println("Body: " + json);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Response: " + response.body());

        } catch (Exception e) { // captura TODO
            System.out.println("❌ ERROR AL ENVIAR PUT:");
            e.printStackTrace();
        }
    }

    public void crearUsuario(Usuario usuario) {
        try {
            String json = gson.toJson(usuario);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("POST enviado. Status: " + response.statusCode());
        } catch (Exception e) {
            System.out.println("❌ ERROR EN CREAR USUARIO:");
            e.printStackTrace();
        }
    }




    // Más adelante puedes añadir métodos como:
    // - guardarUsuario(Usuario u)
    // - actualizarUsuario(String id, Usuario u)
    // - obtenerUsuarioPorId(String id)
    // - generarPdf()

}
