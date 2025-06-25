package es.ufv.dis.back.final2025.MEG;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite que el frontend se conecte
public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    @GetMapping
    public ResponseEntity<List<Usuario>> getTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getPorId(@PathVariable String id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado"); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable String id, @RequestBody Usuario usuario) {
        usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok("Usuario actualizado"); // 200 OK
    }

}
