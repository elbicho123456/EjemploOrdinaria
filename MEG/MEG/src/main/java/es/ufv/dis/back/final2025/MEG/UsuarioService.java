package es.ufv.dis.back.final2025.MEG;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioService {

    private static final String FILE_PATH = "usuarios.json"; // Ruta del archivo JSON
    private final Gson gson = new Gson();

    public List<Usuario> obtenerTodos() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Usuario obtenerPorId(String id) {
        return obtenerTodos().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void guardarUsuario(Usuario usuario) {
        List<Usuario> usuarios = obtenerTodos();
        usuario.setId(UUID.randomUUID().toString()); // Genera un nuevo ID único
        usuarios.add(usuario);
        guardarTodos(usuarios);
    }

    public void actualizarUsuario(String id, Usuario usuarioActualizado) {
        List<Usuario> usuarios = obtenerTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(id)) {
                usuarioActualizado.setId(id); // conservar el ID original
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
        guardarTodos(usuarios);
    }

    private void guardarTodos(List<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(usuarios, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}