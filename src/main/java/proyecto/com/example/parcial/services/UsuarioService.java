package proyecto.com.example.parcial.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import proyecto.com.example.parcial.models.Usuario;
import proyecto.com.example.parcial.repositories.UsuarioRepository;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
     
    public Usuario save(Usuario usuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public boolean checkPassword(Usuario usuario, String contrasenaActual) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(contrasenaActual, usuario.getPassword());
    }

    public void updatePassword(Usuario usuario, String contrasenaNueva) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(passwordEncoder.encode(contrasenaNueva));
        usuarioRepository.save(usuario);
    }
}
