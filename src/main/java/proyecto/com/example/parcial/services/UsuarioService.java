package proyecto.com.example.parcial.services;

import org.springframework.stereotype.Service;
import proyecto.com.example.parcial.models.Usuario;
import proyecto.com.example.parcial.repositories.UsuarioRepository;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
