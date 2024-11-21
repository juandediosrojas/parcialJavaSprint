package proyecto.com.example.parcial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.com.example.parcial.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

}
