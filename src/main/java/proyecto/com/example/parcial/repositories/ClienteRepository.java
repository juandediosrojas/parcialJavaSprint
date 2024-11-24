package proyecto.com.example.parcial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.com.example.parcial.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
